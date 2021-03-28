package com.itdy.hqsm.io.myaio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 AIO最大的一个特性就是异步能力，这种能力对socket与文件I/O都起作用。
 AIO其实是一种在读写操作结束之前允许进行其他操作的I/O处理。
 AIO是对JDK1.4中提出的同步非阻塞I/O(NIO)的进一步增强
 类如下:

 AsynchronousChannel：支持异步通道，包括服务端AsynchronousServerSocketChannel和普通AsynchronousSocketChannel等实现。
 CompletionHandler：用户处理器。定义了一个用户处理就绪事件的接口，由用户自己实现，异步io的数据就绪后回调该处理器消费或处理数据。
 AsynchronousChannelGroup：一个用于资源共享的异步通道集合。处理IO事件和分配给CompletionHandler

 主要在java.nio.channels包下增加了下面四个异步通道：

 AsynchronousSocketChannel
 AsynchronousServerSocketChannel
 AsynchronousFileChannel
 AsynchronousDatagramChannel

 */
public class AIOServer {

    /**
     *
     */
    public final static int PORT = 9888;
    //用于面向流的侦听套接字的异步通道。
    private AsynchronousServerSocketChannel server;

    public AIOServer() throws IOException {
        server = AsynchronousServerSocketChannel.open().bind(
                new InetSocketAddress(PORT)
        );
    }

    /**
     * 测试
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public void startWithFuture() throws InterruptedException, ExecutionException,
            TimeoutException {
        System.out.println("服务器监听------" + PORT);
        Future<AsynchronousSocketChannel> future = server.accept();
        AsynchronousSocketChannel socket = future.get();
        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        readBuf.clear();
        socket.read(readBuf).get(100, TimeUnit.SECONDS);
        readBuf.flip();
        System.out.println("接收信息------" + new String(readBuf.array()));
        System.out.println("线程名称-----"+Thread.currentThread().getName());
    }

    /**
     * 测试
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public void startWithCompletionHandler() throws InterruptedException, ExecutionException,
            TimeoutException {
        System.out.println("--------服务器监听-------" + PORT);
        //注册事件和事件完成后的处理器
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println("------线程名称-------"+Thread.currentThread().getName());
                System.out.println("--------start----------");
                try {
                    buffer.clear();
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    System.out.println("-------接收消息-------" + new String(buffer.array()));
                }  catch(InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        result.close();
                        server.accept(null, this);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("------结束--------");
            }
            /*
             *
             */
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("----失败------ " + exc);
            }
        });

        // 主线程继续自己的行为
        while(true) {
            System.out.println("-------主线程------main thread--------");
            Thread.sleep(1000);
        }
    }



   /* public static void main(String[] args) throws Exception {
        new AIOServer().startWithCompletionHandler();
    }*/
}
