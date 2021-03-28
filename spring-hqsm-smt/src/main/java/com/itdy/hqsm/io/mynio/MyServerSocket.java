package com.itdy.hqsm.io.mynio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 *
 *
 */
public class MyServerSocket {




    /**
     * @param args
     */
    private Selector selector=null;
    private ServerSocketChannel serverSocketChannel=null;
    private int port=8000;
    //设字符集
    private Charset charset= Charset.forName("UTF-8");

    public MyServerSocket() throws IOException{
        //打开选择器。
        selector=Selector.open();
        //打开服务器套接字通道
        serverSocketChannel=serverSocketChannel.open();
        //检索与此通道关联的服务器套接字。设置打开
        serverSocketChannel.socket().setReuseAddress(true);
        //调整此通道的阻塞模式。
        serverSocketChannel.configureBlocking(false);
        //绑定到特定的地址(IP地址和端口号)。
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("---------------服务器启动");

    }

    /**
     *
     * 服务
     * @throws IOException
     */
    public void service()throws IOException{
        //用给定的选择器注册此通道，返回一个选择 键。
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(selector.select()>0){
            //返回此选择器的选择键集合
            Set readyKeys=selector.selectedKeys();
            Iterator it=readyKeys.iterator();
            while(it.hasNext()){
                SelectionKey key=null;
                try{
                    key=(SelectionKey) it.next();
                    //  System.out.println("-------key------>"+key.toString());
                    //从基础集合中移除最后返回的元素 通过这个迭代器(可选操作)
                    it.remove();

                    if(key.isAcceptable()){
                        //返回为其创建此键的通道。该方法将 即使取消了密钥，也要继续返回通道。
                        ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
                        //接受到此通道套接字的连接
                        SocketChannel socketChannel=(SocketChannel) ssc.accept();

                        System.out.println("收到--------"+socketChannel.socket().getInetAddress()+"----------"+socketChannel.socket().getPort());
                        //调整此通道的阻塞模式。
                        socketChannel.configureBlocking(false);
                        //分配字节缓冲区。
                        ByteBuffer buffer=ByteBuffer.allocate(1024);
                        //用给定的选择器注册此通道，返回一个选择键。
                        socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE,buffer);
                    }
                    if(key.isReadable()){
                        receive(key);
                    }
                    if(key.isWritable()){
                        send(key);
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                    try{
                        if(key!=null){
                            key.cancel();
                            key.channel().close();
                        }
                    }
                    catch(Exception ex){e.printStackTrace();}
                }
            }
        }
    }

    /**
     *
     *
     * @param key
     * @throws IOException
     */
    public void send(SelectionKey key)throws IOException{
        ByteBuffer buffer=(ByteBuffer)key.attachment();
        //返回为其创建此键的通道。该方法将  即使取消了密钥，也要继续返回通道。
        SocketChannel socketChannel=(SocketChannel)key.channel();
        String data=decode(buffer);
        if(data.indexOf("\r\n")==-1)return;
        String outputData=data.substring(0, data.indexOf("\n")+1);
        System.out.print("------------->"+outputData);
        ByteBuffer outputBuffer=encode("echo:---------"+outputData);
        while(outputBuffer.hasRemaining())
            socketChannel.write(outputBuffer);
        ByteBuffer temp=encode(outputData);
        buffer.position(temp.limit());
        buffer.compact();
        if(outputData.equalsIgnoreCase("bye\r\n")){
            key.cancel();
            socketChannel.close();
            System.out.println("----停----stop connect with the host");
        }
    }

    /***
     *
     * @param key
     * @throws IOException
     */
    public void receive(SelectionKey key)throws IOException{
        System.out.println("----12------key------>"+key.toString());
        //检索当前附件。
        ByteBuffer buffer=(ByteBuffer)key.attachment();
        SocketChannel socketChannel=(SocketChannel)key.channel();
        ByteBuffer readBuffer=ByteBuffer.allocate(32);

        int read = socketChannel.read(readBuffer);
        System.out.println("-----------read----------->"+read);
        final Buffer flip = readBuffer.flip();
        System.out.println("-----------flip----------->"+flip);
        final Buffer limit = buffer.limit(buffer.capacity());
        System.out.println("-----------limit----------->"+limit);
        final ByteBuffer put = buffer.put(readBuffer);
        System.out.println("-----------put----------->"+put);

    }

    public String decode(ByteBuffer buffer){
        CharBuffer charBuffer=charset.decode(buffer);
        return charset.toString();
    }
    public ByteBuffer encode(String str){
        return charset.encode(str);
    }


    /***
     *
     *
     *
     * @param args
     * @throws Exception
     */
 /*   public static void main(String[] args)throws Exception {
        MyServerSocket server=new MyServerSocket();
        server.service();
    }*/

}