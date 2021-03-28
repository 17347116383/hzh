package com.itdy.hqsm.io.mybio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在 Socket socket = serverSocket.accept(); 处打了断点，有20个客户端同时发出请求，
 * 可服务端还是一个一个的处理，其它线程都处于阻塞状态
 * 如果操作系统没有发现有套接字从指定的端口xx来，那么操作系统就会等待。
 * 这样serverSocket.accept()方法就会一直等待。
 * 这就是为什么accept()方法为什么会阻塞：它内部的实现是使用的操作系统级别的同步IO
 */
public class BioSocketServerThreadPool {
    //默认的端口号
    private static int DEFAULT_PORT = 8083;
    //线程池 懒汉式的单例
    private static ExecutorService executorService = Executors.newFixedThreadPool(60);


    /**
     *
     */
    public  void maind() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("监听来自于------"+DEFAULT_PORT+"的端口信息");
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while(true) {
                Socket socket = serverSocket.accept();
                //当然业务处理过程可以交给一个线程（这里可以使用线程池）,并且线程的创建是很耗资源的。
                //最终改变不了.accept()只能一个一个接受socket的情况,并且被阻塞的情况
                SocketServerThreadPool socketServerThreadPool = new SocketServerThreadPool(socket);
                executorService.execute(socketServerThreadPool);
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
                BioSocketServerThreadPool.class.wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}

/**
 *
 */
class SocketServerThreadPool implements Runnable {
    private Socket socket;
    public SocketServerThreadPool (Socket socket) {
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
            System.out.println("服务器收到来自于端口：-------" + sourcePort + "的信息：" + message);

            //下面开始发送信息
            out.write("回发响应信息！----".getBytes());
        } catch(Exception e) {
            System.out.println("------"+e.getMessage());
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
    /**
     如果操作系统没有发现有套接字从指定的端口xx来，那么操作系统就会等待。
     这样serverSocket.accept()方法就会一直等待。这就是为什么accept()方法为什么会阻塞：
     它内部的实现是使用的操作系统级别的同步IO。

     阻塞IO 和 非阻塞IO
     这两个概念是程序级别的。主要描述的是程序请求操作系统IO操作后，如果IO资源没有准备好，
     那么程序该如何处理的问题：前者等待；后者继续执行（并且使用线程一直轮询，直到有IO资源准备好了）
     同步IO 和非同步IO
     这两个概念是操作系统级别的。主要描述的是操作系统在收到程序请求IO操作后，
     如果IO资源没有准备好，该如何处理相应程序的问题：前者不响应，直到IO资源准备好以后；
     后者返回一个标记（好让程序和自己知道以后的数据往哪里通知），当IO资源准备好以后，
     再用事件机制返回给程序。
     */
  /*  public static void main(String[] args) {
        new BioSocketServerThreadPool().maind();
    }*/

}

