package com.itdy.hqsm.io.mynio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.alibaba.fastjson.JSON;
import com.itdy.hqsm.modes.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.DigestUtils;

import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 *client端发送消息： 内容长度 + 内容，200线程同时发送
 * server端接收消息：解析内容长度和内容，返回2MB测试数据给客户端
 *  Server端：       一个accept selector，多个read selector，一个write selector
 */
public class MyServerSocketChannel {


    public static final Log LOG = LogFactory.getLog(MyServerSocketChannel.class);
    //基于链接节点的可选限定的blocking queue 。 这个队列排列元素FIFO（先进先出）。
    private BlockingQueue<Call> queue = new LinkedBlockingQueue<Call>();
    //基于链接节点的无界线程安全queue 。 这个队列排列元素FIFO（先进先出）。是许多线程将共享对公共集合的访问的适当选择。 像大多数其他并发集合实现一样，此类不允许使用null元素。
    private Queue<Call> responseCalls = new ConcurrentLinkedQueue<Call>();

    volatile boolean running = true;

    private Responder responder = null;

    private static int NIO_BUFFER_LIMIT = 64 * 1024;

    private int handler = 10;


    /**
     *
     *监听器
     */
    class Listener extends Thread {

        Selector selector;
        Reader[] readers;
        int robin;
        int readNum;

        Listener(int port) throws IOException {
            //创建对象
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            //设置非阻塞
            serverChannel.configureBlocking(false);
            //绑定端口
            serverChannel.socket().bind(new InetSocketAddress(port), 150);
            //创建多路复用选择器
            selector = Selector.open();
            //设置并注册选择器到serverChannel上
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            readNum = 10;
            readers = new Reader[readNum];
            for(int i = 0; i < readNum; i++) {
                readers[i] = new Reader(i);
                readers[i].start();
            }
        }


        public void run() {
            while(running) {
                try {
                    selector.select();
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if(key.isValid()) {
                            // 新连接请求，注册到选择器中
                            if(key.isAcceptable()) {
                                doAccept(key);
                            }
                        }
                    }
                } catch (IOException e) {
                    LOG.error("--------", e);
                }
            }
        }

        /**
         *
         *
         * @param selectionKey
         * @throws IOException
         */
        public void doAccept(SelectionKey selectionKey) throws IOException {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel;
            //accept()接受到此通道套接字的连接
            while((socketChannel = serverSocketChannel.accept()) != null) {
                try {
                    //调整此通道的阻塞模式。
                    socketChannel.configureBlocking(false);
                    //禁用/启用Nagle算法
                    socketChannel.socket().setTcpNoDelay(true);
                    //启用/禁用
                    socketChannel.socket().setKeepAlive(true);
                } catch (IOException e) {
                    socketChannel.close();
                    throw e;
                }
                Reader reader = getReader();
                try {
                    reader.startAdd();
                    SelectionKey readKey = reader.registerChannel(socketChannel);
                    Connection c = new Connection(socketChannel);
                    readKey.attach(c);
                } finally {
                    reader.finishAdd();
                }
            }
        }

        /**
         *
         *获取reader
         * @return
         */
        public Reader getReader() {
            if(robin == Integer.MAX_VALUE) {
                robin = 0;
            }
            return readers[(robin ++) % readNum];
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *  服务器启动多个read selector
     *
     */
    class Reader extends Thread {
        Selector readSelector;
        boolean adding;

        Reader(int i) throws IOException {
            setName("huangzuhua------" + i);
            this.readSelector = Selector.open();
            LOG.info("---------读-----" + i + "");
        }

        public void run() {
            while(running) {
                try {
                    //循环阻塞选择器
                    readSelector.select();
                    while(adding) {
                        synchronized(this) {
                            this.wait(100);
                        }
                    }
                    // 获得待处理请求
                    Iterator<SelectionKey> it = readSelector.selectedKeys().iterator();
                   // 循环遍历获取
                    while(it.hasNext()) {
                        SelectionKey key = it.next(); //获取一个请求
                        it.remove();
                        //key.isAcceptable(); 新连接请求，注册到选择器中
                        //判断请求状态 ,
                        if(key.isValid()) {
                            //连接可读请求，处理读业务逻辑
                            if(key.isReadable()) {
                                //处理读业务
                                doRead(key);
                            }
                        }
                    }
                } catch (IOException e) {
                    LOG.error("-------", e);
                } catch (InterruptedException e) {
                    LOG.error("-------", e);
                }
            }
        }
        /**
         *处理读业务
         *
         * @param selectionKey
         */
        public void doRead(SelectionKey selectionKey) {
            Connection c = (Connection) selectionKey.attachment();
            if(c == null) {
                return;
            }
            int n;
            try {
                //业务处理
                n = c.readAndProcess();
                System.out.println("-----业务处理1---"+n);
            } catch (IOException e) {
                LOG.error("--业务处理2------", e);
                n = -1;
            } catch (Exception e) {
                LOG.error("--业务处理3------", e);
                n = -1;
            }
            if(n == -1) {
                c.close();
            }
        }
        public SelectionKey registerChannel(SocketChannel channel) throws IOException {
            return channel.register(readSelector, SelectionKey.OP_READ);
        }
        public void startAdd() {
            adding = true;
            readSelector.wakeup();
        }
        public synchronized void finishAdd() {
            adding = false;
            this.notify();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////

    class Connection {
        private SocketChannel channel;
        private ByteBuffer dataBufferLength;
        private ByteBuffer dataBuffer;
        private boolean skipHeader;

        public Connection(SocketChannel channel) {
            this.channel = channel;
            this.dataBufferLength = ByteBuffer.allocate(4);
        }

        /**
         * 业务处理
         * @return
         * @throws IOException
         */
        public int readAndProcess() throws IOException {
            int count;
            if(!skipHeader) {
                count = channelRead(channel, dataBufferLength);
                if (count < 0 || dataBufferLength.remaining() > 0) {
                    return count;
                }
            }
            skipHeader = true;
            if(dataBuffer == null) {
                dataBufferLength.flip();
                int dataLength = dataBufferLength.getInt();
                System.out.println("---dataLength------>"+dataLength);
                dataBuffer = ByteBuffer.allocate(dataLength);
            }
            count = channelRead(channel, dataBuffer);
            if(count >= 0 && dataBuffer.remaining() == 0) {
                process();
            }
            return count;
        }


        /**
        * 处理dataBuffer
        *
        */
        public void process() {
            dataBuffer.flip();
            byte[] data = dataBuffer.array();
            Call call = new Call(this, data, responder);
            try {
                queue.put(call);
            } catch (InterruptedException e) {
                LOG.error("------", e);
            }

        }
        public void close() {
            if(channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }

    }

//////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 服务端 回应客户端信息
     */
    class Responder extends Thread {
        Selector writeSelector;

        public Responder() throws IOException {
            //打开通道
            writeSelector = Selector.open();
        }

        public void run() {
            while(running) {
                try {
                    //判断客户端是否关闭了
                    registWriters();
                    //选择通道已为I/ o操作做好准备。
                    int n = writeSelector.select(100);
                    if(n == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> it = writeSelector.selectedKeys().iterator();
                    while(it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if(key.isValid() && key.isWritable()) {
                            //异步写
                            doAsyncWrite(key);
                        }
                    }
                } catch (IOException e) {
                    LOG.error("------>", e);
                }
            }
        }


        /**
         *
         *
         * @throws IOException
         */
        public void registWriters() throws IOException {
            Iterator<Call> it = responseCalls.iterator();
            while(it.hasNext()) {
                Call call = it.next();
                it.remove();
                SelectionKey key = call.conn.channel.keyFor(writeSelector);
                try {
                    if (key == null) {
                        try {
                            call.conn.channel.register(writeSelector, SelectionKey.OP_WRITE, call);
                        } catch (ClosedChannelException e) {
                            //客户端关闭
                            if (LOG.isTraceEnabled())
                                LOG.trace("客户端关闭----------", e);
                        }
                    } else {
                        //将此键的兴趣值设置为给定值。
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                } catch (CancelledKeyException e) {
                    if (LOG.isTraceEnabled())
                        LOG.trace("客户端关闭", e);
                }
            }
        }


        /**
         *   把数据写入客户端
         * @param call
         * @throws IOException
         */
        public void registerForWrite(Call call) throws IOException {
            responseCalls.add(call);
            writeSelector.wakeup();
        }

        /**
         * 异步写
         * @param key
         * @throws IOException
         */
        private void doAsyncWrite(SelectionKey key) throws IOException {
            Call call = (Call) key.attachment();
            if(call.conn.channel != key.channel()) {
                throw new IOException("通道异常----");
            }
            int numBytes = channelWrite(call.conn.channel, call.response);
            if(numBytes < 0 || call.response.remaining() == 0) {
                try {
                    key.interestOps(0);
                } catch (CancelledKeyException e) {
                    LOG.warn("更改通道异常----- " + e);
                }
            }
        }

        private void doResponse(Call call) throws IOException {
            //if data not fully send, then register the channel for async writer
            //如果数据没有完全发送，则为async写入器注册通道
            if(!processResponse(call)) {
                registerForWrite(call);
            }
        }

        /**
         *
         * @param call
         * @return
         * @throws IOException
         */
        private boolean processResponse(Call call) throws IOException {
            boolean error = true;
            try {
                int numBytes = channelWrite(call.conn.channel, call.response);
                if (numBytes < 0) {
                    throw new IOException("套接字异常");
                }
                error = false;
            } finally {
                if(error) {
                    call.conn.close();
                }
            }
            if(!call.response.hasRemaining()) {
                call.done = true;
                return true;
            }
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////



    /**
     *
     *读取客户端的数据
     */
    class Handler extends Thread {
        public Handler(int i) {
            setName("huangzuhua------" + i);
            LOG.info("开始处理程序----------" + i + "...");
        }
        public void run() {
            while(running) {
                try {
                    Call call = queue.take();
                    //获取客户端数据
                    process(call);
                } catch (InterruptedException e) {
                    LOG.error("-------", e);
                } catch (IOException e) {
                    LOG.error("----------", e);
                }
            }
        }

        /**
         * 每个通道为测试编写2MB的数据
         * @param call
         * @throws IOException
         */
        public void process(Call call) throws IOException {
            byte[] request = call.request;
            String message = new String(request);
            LOG.info("收到客服端信息-------: " + message);
            //每个通道为测试编写2MB的数据
            int dataLength = 2*2 * 1024 * 1024;
            //分配一个新的字节缓冲区大小。
            ByteBuffer buffer = ByteBuffer.allocate(4 + dataLength);

            buffer.putInt(dataLength);

            int n = buffer.limit() - 4;
            //把数据写到客户端
            for(int i = 0; i < n; i++) {
                //将给定的字节按当前位置写入此缓冲区，然后递增该位置。
               buffer.put((byte)66);
            }


            buffer.flip();
            call.response = buffer;
            responder.doResponse(call);
        }
    }

    ////////////////////////////////////////////////////////////////////////////



    class Call {
        Connection conn;
        //客户端数据缓存区
        byte[] request;
        Responder responder;
        ByteBuffer response;
        boolean done;

        public Call(Connection conn, byte[] request, Responder responder) {
            this.conn = conn;
            this.request = request;
            this.responder = responder;
        }
    }


    public int channelRead(ReadableByteChannel channel, ByteBuffer buffer) throws IOException {
        //System.out.println(000+buffer.remaining() <= NIO_BUFFER_LIMIT ? channel.read(buffer) : channleIO(channel, null, buffer));
        return buffer.remaining() <= NIO_BUFFER_LIMIT ? channel.read(buffer) : channleIO(channel, null, buffer);
    }

    public int channelWrite(WritableByteChannel channel, ByteBuffer buffer) throws IOException {
       // System.out.println(0000+buffer.remaining() <= NIO_BUFFER_LIMIT ? channel.write(buffer) : channleIO(null, channel, buffer));
        return buffer.remaining() <= NIO_BUFFER_LIMIT ? channel.write(buffer) : channleIO(null, channel, buffer);
    }


    public int channleIO(ReadableByteChannel readCh, WritableByteChannel writeCh, ByteBuffer buffer) throws IOException {
        int initRemaining = buffer.remaining();
        int originalLimit = buffer.limit();
        int ret = 0;
        try {
            while (buffer.remaining() > 0) {
                int ioSize = Math.min(buffer.remaining(), NIO_BUFFER_LIMIT);
                System.out.println("------ioSize----------"+ioSize);
                buffer.limit(buffer.position() + ioSize);
                ret = readCh == null ? writeCh.write(buffer) : readCh.read(buffer);
                if (ret < ioSize) {
                    break;
                }
            }
        } finally {
            buffer.limit(originalLimit);
        }
        int byteRead = initRemaining - buffer.remaining();
        System.out.println("------byteRead----------"+byteRead);
        return byteRead > 0 ? byteRead : ret;
    }


    /**
     *
     *
     */
    public void startHandler() {
        for(int i = 0; i < handler; i++) {
            new Handler(i).start();
        }
    }


    public void start() throws IOException {
        new Listener(10000).start();
        responder = new Responder();
        responder.start();
        startHandler();
        LOG.info("server startup! ------------");
    }

    /*public static void main(String[] args) throws IOException {
        MyServerSocketChannel server = new MyServerSocketChannel();
        server.start();
    }*/





/////////////////////////////////////
       /* public static void main(String[] args) {
            selector();
        }
        private static final int BUF_SIZE=1024;
        private static final int PORT=8080;
        private static final int TIMEOUT=3000;

        public static void handleAccept(SelectionKey key) throws IOException {
            ServerSocketChannel ssChannel=(ServerSocketChannel)key.channel();
            //通常不会仅仅只监听一个连接,在while循环中调用 accept()方法
            //通过 ServerSocketChannel.accept() 方法监听新进来的连接。当 accept()
            //方法返回的时候,它返回一个包含新进来的连接的 SocketChannel。因此,
            //accept()方法会一直阻塞到有新连接到达。
            SocketChannel sc=ssChannel.accept();
            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocate(BUF_SIZE));
        }
*/
    /**
     * 获取客户端数据
     * @param key
     * @throws IOException
     */
    /*public static void handleRead(SelectionKey key) throws IOException {
            SocketChannel sc=(SocketChannel)key.channel();
            ByteBuffer buf=(ByteBuffer)key.attachment();
            long bytesRead=sc.read(buf);
            while(bytesRead>0) {
                //flip方法将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，
                //并将limit设置成之前position的值。
                buf.flip();
                while(buf.hasRemaining()) {
                    //使用get()方法从Buffer中读取数据
                    System.out.print("--"+(char)buf.get());
                }
                System.out.println();
                buf.clear();
                bytesRead=sc.read(buf);
            }
            if(bytesRead==-1) {
                sc.close();
            }
        }
        public static void handleWrite(SelectionKey key) throws IOException {
            ByteBuffer buf=(ByteBuffer)key.attachment();
            buf.flip();
            SocketChannel sc=(SocketChannel)key.channel();
            while(buf.hasRemaining()) {
                sc.write(buf);
            }
            //compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
            buf.compact();
        }
        public static void selector() {
            Selector selector=null;
            ServerSocketChannel ssc=null;
            try {
                selector=Selector.open();
                //通过调用 ServerSocketChannel.open() 方法来打开ServerSocketChannel.
                ssc=ServerSocketChannel.open();
                ssc.socket().bind(new InetSocketAddress(PORT));
                ssc.configureBlocking(false);
                ssc.register(selector, SelectionKey.OP_ACCEPT);
                while(true) {
                    if(selector.select(TIMEOUT)==0) {
                        System.out.println("==");
                        continue;
                    }
                    Iterator iter=selector.selectedKeys().iterator();
                    while(iter.hasNext()) {
                        SelectionKey key= (SelectionKey) iter.next();
                        if(key.isAcceptable()) {
                            handleAccept(key);
                        }
                        if(key.isReadable()) {
                            handleRead(key);
                        }
                        if(key.isWritable()&&key.isValid()) {
                            handleWrite(key);
                        }
                        if(key.isConnectable()) {
                            System.out.println("isConnectable = true");
                        }
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(selector!=null) {
                        selector.close();
                    }
                    if(ssc!=null) {
                        //通过调用ServerSocketChannel.close() 方法来关闭ServerSocketChannel
                        ssc.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }



