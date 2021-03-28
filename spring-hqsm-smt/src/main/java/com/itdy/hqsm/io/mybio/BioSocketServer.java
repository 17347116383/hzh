package com.itdy.hqsm.io.mybio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



/**
 * 此种BIO通信模型的服务端，通常由一个独立的Acceptor线程负责监听客户端的连接，
 * 它接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理没处理完成后，
 * 通过输出流返回应答给客户端，线程销毁。即典型的一请求一应答通宵模型。
 */
public class BioSocketServer {
    //默认的端口号
    private static int DEFAULT_PORT = 8083;


    /**
     *
     */
    public  void mains() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("监听来自于"+DEFAULT_PORT+"的端口信息");
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while(true) {
                Socket socket = serverSocket.accept();
                SocketServerThread socketServerThread = new SocketServerThread(socket);
                new Thread(socketServerThread).start();
            }
        } catch(Exception e) {

        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        //这个wait不涉及到具体的实验逻辑，只是为了保证守护线程在启动所有线程后，进入等待状态
        synchronized (NioSocketServer.class) {
            try {
                BioSocketServer.class.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}





class SocketServerThread implements Runnable {
    private Socket socket;
    public SocketServerThread (Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            //下面我们收取信息
            in = socket.getInputStream();
            out = socket.getOutputStream();
            Integer sourcePort = socket.getPort();
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            //使用线程，同样无法解决read方法的阻塞问题，
            //也就是说read方法处同样会被阻塞，直到操作系统有数据准备好
            int realLen = in.read(contextBytes, 0, maxLen);
            //读取信息
            String message = new String(contextBytes , 0 , realLen);

            //下面打印信息
            System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);

            //下面开始发送信息
            out.write("回发响应信息！".getBytes());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //试图关闭
            try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
                if(this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    //https://blog.csdn.net/yjp198713/article/details/79281237
   /* public static void main(String[] args) {
        new BioSocketServer().mains();
    }*/

    }
