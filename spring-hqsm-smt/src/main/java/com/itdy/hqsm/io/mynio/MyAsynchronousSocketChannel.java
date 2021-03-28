package com.itdy.hqsm.io.mynio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 *
 *
 */
public class MyAsynchronousSocketChannel {


    public  void maind() {
        AsynchronousSocketChannel socketChannel = null;
        try {
            socketChannel = AsynchronousSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 80);
            Future<Void> connect = socketChannel.connect(inetSocketAddress);

            while (!connect.isDone()) {
                Thread.sleep(10);
            }
            System.out.println("建立连接" + socketChannel.getRemoteAddress());
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            Future<Integer> read = socketChannel.read(buffer);
            while (!read.isDone()) {
                Thread.sleep(10);
            }
            System.out.println("接收服务器数据:" + new String(buffer.array(), 0, read.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) {
    new MyAsynchronousSocketChannel().maind();
    }*/
}
