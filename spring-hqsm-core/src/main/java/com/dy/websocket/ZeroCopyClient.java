package com.dy.websocket;



import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: ZeroCopyClient
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 21:24
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ZeroCopyClient {

    public static void main(String[] args) throws IOException {
       // main();
        main(2);
    }

    /**
     * 测试1
     * @throws IOException
     */
    public static void main() throws IOException {
        Socket socket= new Socket("127.0.0.1",9999);
        //文件地址
        String  filename ="E:/111/20190329.rar";
        InputStream  inputStream = new FileInputStream(filename) ;
        DataOutputStream dataOutputStream =new DataOutputStream(socket.getOutputStream());
        byte [] bt = new byte[4096];
        long reads;
        long  tot=0;

        long l = System.currentTimeMillis();
        int read = inputStream.read();
        while ((reads=inputStream.read())>=0){
          tot+=reads;
          dataOutputStream.write(bt);

            System.out.println(reads);
        }
        System.out.println("----时间----"+(System.currentTimeMillis()-l));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }


    /**
     * 测试2   零拷贝
     * @param t
     * @throws IOException
     */
    public static void main(int t) throws IOException {
        SocketChannel   socketChannel = SocketChannel.open();
        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        socketChannel.configureBlocking(true);
        //文件地址
        String  filename ="E:/111/20190329.rar";
        FileChannel channel = new FileInputStream(filename).getChannel();
        long l = System.currentTimeMillis();
        //文件cp
        long cs = channel.transferTo(0, channel.size(), socketChannel);
        System.out.println("---------------"+l);
        socketChannel.close();
    }

}
