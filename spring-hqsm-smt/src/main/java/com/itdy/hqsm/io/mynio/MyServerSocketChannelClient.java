package com.itdy.hqsm.io.mynio;
import com.alibaba.fastjson.JSON;
import com.itdy.hqsm.common.lang.DateUtils;
import com.itdy.hqsm.modes.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.SocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.Date;

import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;


/**
 *
 *
 *
 *
 */
public class MyServerSocketChannelClient {

    private final String s = DateUtils.formatDateTime(new Date());

    public static final Log LOG = LogFactory.getLog(MyServerSocketChannelClient.class);

    Socket socket;
    OutputStream out;
    InputStream in;

    public MyServerSocketChannelClient() throws IOException {
        socket = SocketFactory.getDefault().createSocket();
        socket.setTcpNoDelay(true);
        socket.setKeepAlive(true);
        InetSocketAddress server = new InetSocketAddress("localhost", 10000);
        socket.connect(server, 100);
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }


    /**
     * 向服务器发送数据
     * @param message
     * @throws IOException
     */
    public void send(String message) throws IOException {
        byte[] data = message.getBytes();
        DataOutputStream dos = new DataOutputStream(out);
        //写入底层输出流字节，高字节优先。如果没有抛出异常，则抛出计数器
        dos.writeInt(data.length);
        dos.write(data);
        out.flush();
    }


    public static void mainsd() throws IOException {

        int n = 20;
        for(int i = 0; i < n; i++) {
            new Thread() {
                MyServerSocketChannelClient client = new MyServerSocketChannelClient();

                public void run() {
                    try {
                        //client.send(getName() + "_________xiaomiemie");
                        //client.send(JSON.toJSON(new ArrayList<User>()));
                        client.send(JSON.toJSONString(new User("123123","12312",123123,"2019-09-01")));
                        DataInputStream inputStream = new DataInputStream(client.in);
                        int dataLength = inputStream.readInt();
                        byte b = inputStream.readByte();
                        String s = inputStream.readUTF();
                        char c = inputStream.readChar();
                        int i1 = inputStream.readInt();
                        System.out.println("---线程----i1---->"+i1);
                        System.out.println("---线程----c---->"+c);
                        System.out.println("---线程----b---->"+b);
                        System.out.println("---线程----s---->"+s);
                        System.out.println("---线程-------->"+dataLength);
                        byte[] data = new byte[dataLength];
                        inputStream.readFully(data);
                        client.socket.close();
                        LOG.info("从服务器接收--------" + data.length);
                    } catch (Exception e ) {
                        LOG.error("", e);
                    }
                }
            }.start();
        }
    }
   /* public static void main(String[] args) throws IOException {
        mainsd();
    }*/





  /*  public static void main(String[] args) {
        client();
    }
    public static void client() {
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        SocketChannel socketChannel=null;
        try {
            socketChannel=SocketChannel.open();//打开SocketChannel
            socketChannel.configureBlocking(false);//设置为非阻塞方式
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
            //为了确定连接是否建立，可以调用finishConnect()的方法
            if(socketChannel.finishConnect()) {
                int i=0;
                while(true) {
                    TimeUnit.SECONDS.sleep(1);
                    String info="I am "+i+++"-th information from client";
                    buffer.clear();
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while(buffer.hasRemaining()) {
                        System.out.println(buffer);
                        socketChannel.write(buffer);//写数据到SocketChannel用的是SocketChannel.write()方法
                    }
                }
            }
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(socketChannel!=null) {
                try {
                    socketChannel.close();//关闭SocketChannel
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

}
