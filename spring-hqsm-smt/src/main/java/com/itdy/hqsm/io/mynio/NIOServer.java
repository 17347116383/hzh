package com.itdy.hqsm.io.mynio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * server端实例代码
 *
 * @author
 * @date
 */
public class NIOServer {

   /*
    // 通道管理器(Selector)
    private static Selector selector;

    public static void mainS() throws IOException {
        // 创建通道管理器(Selector)
        selector = Selector.open();
        // 创建通道ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 将通道设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 将ServerSocketChannel对应的ServerSocket绑定到指定端口(port)
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8989));
        //
        // 注册该事件后，当事件到达的时候，selector.select()会返回，
        // 如果事件没有到达selector.select()会一直阻塞。
        //
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 循环处理
        while (true) {
            // 当注册事件到达时，方法返回，否则该方法会一直阻塞
            selector.select();
            // 获取监听事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            // 迭代处理
            while (iterator.hasNext()) {
                // 获取事件
                SelectionKey key = iterator.next();

                // 移除事件，避免重复处理
                iterator.remove();

                // 客户端请求连接事件，接受客户端连接就绪
                if (key.isAcceptable()) {
                    handleAccept(key);
                } else if (key.isReadable()) {// 监听到读事件，对读事件进行处理
                    handleRead(key);
                }

            }


        }
    }

    //
    // 处理客户端连接成功事件
    //
    private static void handleAccept(SelectionKey key) throws IOException {
        // 获取客户端连接通道
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = server.accept();
        socketChannel.configureBlocking(false);



        ByteBuffer buff = ByteBuffer.allocate(1024*1024*2);
        int flag=socketChannel.read(buff);
        byte[] data = buff.array();
        //判断客户端是否下线
        if(flag == -1){
            socketChannel.close();
        }
        System.out.println(new Date().toString()+"-----------"+new String(data).trim());

        // 信息通过通道发送给客户端
        socketChannel.write(ByteBuffer.wrap(new String("Hello Client!------------").getBytes()));
        // 给通道设置读事件，客户端监听到读事件后，进行读取操作
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

     //
     //监听到读事件，读取客户端发送过来的消息
     //
    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        // 从通道读取数据到缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(128);
        channel.read(buffer);

        // 输出客户端发送过来的消息
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("server receive msg from client：" + msg);
    }




   */
  // public static void main(String[] args) throws IOException {
      //  mainS();
      //  mainsa();
   // }





  /*  public static void mainsa() {

        Selector selector = null;
        int port = 8080;
        try {
            selector = SelectorProvider.provider().openSelector();

            ServerSocketChannel serverChannel = SelectorProvider.provider().openServerSocketChannel();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress("127.0.0.1", port));
            System.out.printf("-----------Listen on port:"+port);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            NIOServer.loopSelect(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loopSelect(Selector selector){

        while (true){
            //对socket异常处理，关闭
            SelectionKey selectionKey = null;
            try {
                int ready = selector.select();
                if (ready <= 0) continue;

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                while(it.hasNext()){
                    selectionKey = it.next();

                    //不同类型处理,一个key可能是多个类型合集
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.finishConnect();

                        Request request = new Request();
                        request.setSocketChannel(socketChannel);
                        request.setRequestId(UUID.randomUUID().toString());

                        SelectionKey key = socketChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE); //http连接后,读写会同时就绪，保证先读
                        key.attach(request);

                    }
                    //ServerSocketChannel直接来读写了
                    if (!(selectionKey.channel() instanceof SocketChannel)){
                        it.remove();
                        continue;
                    }
                    if(selectionKey.isReadable()){
                        Request request = (Request) selectionKey.attachment();
                        SocketChannel socketChannel = request.getSocketChannel();
                        //循环读取，确定读取结束
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 0;
                        boolean readDone = false;
                        while (!readDone){
                            while((len = socketChannel.read(buf))>0){
                                buf.flip();
                                String tmp = new String(buf.array(),0,len);
                                System.out.println(tmp);
                                buf.clear();

                                //判断结束，get请求，末尾是两个换行
                                if(tmp.contains("\r\n\r\n")){
                                    readDone = true;
                                    break;
                                }
                            }
                        }

                        request.setReaded(true);
                        selectionKey.attach(request);
                    }
                    if(selectionKey.isWritable()){
                        Request request = (Request) selectionKey.attachment();
                        SocketChannel socketChannel = request.getSocketChannel();

                        //如果这个socketChannel还没有被先读
                        if(!request.isReaded()){
                            it.remove();
                            continue;
                        }

                        String sendStr = "Http/1.1 200 Ok\r\n" +
                                "Content-Type:text/html;charset=UTF-8\r\n" +
                                "\r\n" +
                                "<html><head><title>demo page</title></head><body>hi,world</body></html>";
                        ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes(StandardCharsets.UTF_8));
                        socketChannel.write(buf);

                        System.out.println("socket id:----"+ request.getRequestId()+" done");
                        socketChannel.close();
                    }

                    it.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    selectionKey.channel().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

//请求包装类
class Request{
    private SocketChannel socketChannel = null;
    private String requestId;
    private boolean readed = false;

    public SocketChannel getSocketChannel() {
        if(socketChannel == null)
            throw new RuntimeException("no socketChannel");
        return socketChannel;
    }
    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public boolean isReaded() {
        return readed;
    }
    public void setReaded(boolean readed) {
        this.readed = readed;
    }


*/
}
