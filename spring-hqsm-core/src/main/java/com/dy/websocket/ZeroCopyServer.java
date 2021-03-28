package com.dy.websocket;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;

/**
 * @ProjectName: hqsm-parent  零拷贝
 * @Package: com.dy.websocket
 * @ClassName: ZearCopy
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 21:12
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ZeroCopyServer {

    public static void main(String[] args)throws Exception {
       // main();
        main(23);
    }

    /**
     * 测试1
     * @param
     * @throws Exception
     */
    public static void main()throws Exception{

        ServerSocket s = new ServerSocket(9999);
        while (true){
            Socket socket =s.accept();
            DataInputStream dataInputStream =new DataInputStream(socket.getInputStream());
            byte [] bt = new byte[4096];
            while (true){
                int read = dataInputStream.read(bt,0,bt.length);
                if (-1==read){
                    break;
                }
            }
            char c = dataInputStream.readChar();
            System.out.println(c);
            byte b = dataInputStream.readByte();
            System.out.println(b);
        }

    }



    /**
     * 测试2   零拷贝
     * @param t
     * @throws IOException
     */
    public static void main(int t) throws IOException {

        InetSocketAddress  inetSocketAddress = new InetSocketAddress(9999);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        //绑定
        socket.setReuseAddress(true);
        //添加
        socket.bind(inetSocketAddress);
        //缓存
        ByteBuffer byteBuffer = ByteBuffer.allocate(10240);
        while (true){

            SocketChannel accept = serverSocketChannel.accept();
            //阻塞的
            accept.configureBlocking(true);
            int i =0;
            while (-1!=i){
                int read = accept.read(byteBuffer);
            }
            //倒带  回位
            byteBuffer.remaining();

           // byteBuffer.limit();
           // byteBuffer.position();
           // byteBuffer.flip();

        }

    }
}
