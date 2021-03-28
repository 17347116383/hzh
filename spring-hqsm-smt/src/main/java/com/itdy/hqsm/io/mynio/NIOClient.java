package com.itdy.hqsm.io.mynio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import java.nio.channels.ServerSocketChannel;

import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;

import java.util.UUID;

/**
 * client实例代码
 *
 * @author
 * @date
 */
public class NIOClient {

 /*
    // 通道管理器(Selector)
    private static Selector selector;

    public static void maind() throws IOException {
        // 创建通道管理器(Selector)
        selector = Selector.open();
        // 创建通道SocketChannel
        SocketChannel channel = SocketChannel.open();
        // 将通道设置为非阻塞
        channel.configureBlocking(false);
        // 客户端连接服务器，其实方法执行并没有实现连接，需要在handleConnect方法中调channel.finishConnect()才能完成连接
        channel.connect(new InetSocketAddress("localhost", 8989));
        //
        // * 将通道(Channel)注册到通道管理器(Selector)，并为该通道注册selectionKey.OP_CONNECT
        // * 注册该事件后，当事件到达的时候，selector.select()会返回，
        // * 如果事件没有到达selector.select()会一直阻塞。
        //
        channel.register(selector, SelectionKey.OP_CONNECT);

        // 循环处理
        while (true) {
           //
           // 选择一组可以进行I/O操作的事件，放在selector中，客户端的该方法不会阻塞，
           // selector的wakeup方法被调用，方法返回，而对于客户端来说，通道一直是被选中的
           // 这里和服务端的方法不一样，查看api注释可以知道，当至少一个通道被选中时。
           //
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
                // 连接事件发生
                if (key.isConnectable()) {
                    handleConnect(key);
                } else if (key.isReadable()) {// 监听到读事件，对读事件进行处理
                    handleRead(key);
                }

            }
        }
    }

    //
    // 处理客户端连接服务端成功事件
    //
    private static void handleConnect(SelectionKey key) throws IOException {
        // 获取与服务端建立连接的通道
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            // channel.finishConnect()才能完成连接
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        // 数据写入通道
        channel.write(ByteBuffer.wrap(new String("Hello Server!-------------").getBytes()));
        // 通道注册到选择器，并且这个通道只对读事件感兴趣
        channel.register(selector, SelectionKey.OP_READ);
    }

    //
    // 监听到读事件，读取客户端发送过来的消息
    //
    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 从通道读取数据到缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024*2);
        channel.read(buffer);

        // 输出服务端响应发送过来的消息
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("client receive msg from server：" + msg);
    }



    public static void main(String[] args) throws IOException {
        maind();
    }*/

//-////////////////////////////////////////////////////////////////

  /*  public static void main(String[] args) {

        try {
            Selector selector = SelectorProvider.provider().openSelector();
//            String url = "ifeve.com";
//            int port = 80;
            String url = "127.0.0.1";
            int port = 8080;

            SocketChannel socketChannel = SelectorProvider.provider().openSocketChannel();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(url, port));
            socketChannel.register(selector,SelectionKey.OP_CONNECT);

            NIOClient.loopSelect(selector);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loopSelect(Selector selector){
        boolean isFinish = false;
        while (!isFinish) {
            try {
                int ready = selector.select();
                if (ready <= 0) continue;

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();

                    if(selectionKey.isConnectable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        if(socketChannel.finishConnect())
                        {
                            RequestTo requestTo = new RequestTo();
                            requestTo.setRequestId(UUID.randomUUID().toString());
                            requestTo.setSocketChannel(socketChannel);

                            SelectionKey selectionKey1 = socketChannel.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                            selectionKey1.attach(requestTo);
                        }else{
                            throw new RuntimeException("connection failed");
                        }
                    }
                    if(selectionKey.isReadable()){
                        RequestTo requestTo = (RequestTo) selectionKey.attachment();
                        SocketChannel socketChannel = requestTo.getSocketChannel();

                        //未发送过请求
                        if(!requestTo.isWrited()){
                            it.remove();
                            continue;
                        }

                        int len = 0;
                        ByteBuffer buf = ByteBuffer.allocate(10240);
                        while ((len = socketChannel.read(buf)) != -1) {
                            buf.flip();
                            String tmp = new String(buf.array(),0,len);
                            System.out.println(tmp);
                            buf.clear();

                            //判断读取完毕, content-length, chunked, 等等，确定长度，比较复杂
                            break;
                        }
                        socketChannel.close();
                        return;
                    }
                    if(selectionKey.isWritable()){
                        RequestTo requestTo = (RequestTo) selectionKey.attachment();
                        SocketChannel socketChannel = requestTo.getSocketChannel();

                        String url = "ifeve.com";

                        String sendStr = "GET / HTTP/1.1\r\n" +
                                "Host: " + url + "\r\n" +
                                "\r\n";
                        ByteBuffer buf = ByteBuffer.wrap(sendStr.getBytes(StandardCharsets.UTF_8));


                        socketChannel.write(buf);

                        requestTo.setWrited(true);
                    }

                    it.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
                isFinish = true;
            }
        }
    }
}

class RequestTo{
    private SocketChannel socketChannel = null;
    private String requestId;
    private boolean writed = false;

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

    public boolean isWrited() {
        return writed;
    }

    public void setWrited(boolean writed) {
        this.writed = writed;
    }
*/

}

