package com.itdy.hqsm.io.mynio;

import java.io.*;
import java.nio.channels.*;
import java.nio.*;
import java.nio.charset.*;
import java.net.*;
import java.util.*;

/**
 *
 *
 */
public class MyServerSocketClient {
    //定义SocketChannel
    private SocketChannel socketChannel=null;
   //设置缓冲区大小
    private ByteBuffer sendBuffer=ByteBuffer.allocate(1024);
    //设置缓冲区大小
    private ByteBuffer receiveBuffer=ByteBuffer.allocate(1024);
    //设置字符编码
    private Charset charset=Charset.forName("UTF-8");
    //定义注册器通道
    private Selector selector;

    /**
     *
     * @throws IOException
     */
    public MyServerSocketClient()throws IOException {
        //打开套接字通道。
        socketChannel=SocketChannel.open();
        //返回本地主机的地址。这是通过检索来实现的主机的名称从系统中提取，然后将该名称解析为一个{@code InetAddress}。
        InetAddress ia= InetAddress.getLocalHost();
        //构造
        InetSocketAddress isa=new InetSocketAddress(ia,8000);
        //连接此通道的套接字。
        socketChannel.connect(isa);
        //调整此通道的阻塞模式。
        socketChannel.configureBlocking(false);
        System.out.println("成功连接服务器.-------------");
        //打开一个选择器。
        selector=Selector.open();
    }
    /**
     * @param args
     */
   /* public static void main(String[] args) throws IOException{
        final MyServerSocketClient client=new MyServerSocketClient();
        Thread receiver=new Thread(){
            public void run(){
                client.receiveFromUser();
            }
        };
        receiver.start();
        client.talk();

    }*/

    public void receiveFromUser(){
        try{
            BufferedReader localReader=new BufferedReader(new InputStreamReader(System.in));
            String msg=null;
            while((msg=localReader.readLine())!=null){
                synchronized(sendBuffer){
                    sendBuffer.put(encode(msg+"\r\n"));
                }
                if(msg.equalsIgnoreCase("bye"))
                    break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws IOException
     */
    public void talk()throws IOException{
        socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        //选择一组键，其对应的通道已准备好进行I/O操作。
        while(selector.select()>0){
            //返回此选择器的选择键集。
            Set readyKeys=selector.selectedKeys();
            Iterator it=readyKeys.iterator();
            while(it.hasNext()){
                SelectionKey key=null;
                try{
                    key=(SelectionKey) it.next();
                    it.remove();

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

    public void send(SelectionKey key)throws IOException{
        SocketChannel socketChannel=(SocketChannel)key.channel();
        synchronized(sendBuffer){
            sendBuffer.flip();
             socketChannel.write(sendBuffer);
            sendBuffer.compact();

        }
    }

    public void receive(SelectionKey key)throws IOException{
        //返回为其创建此键的通道。该方法将 即使取消了密钥，也要继续返回通道。
        SocketChannel socketChannel=(SocketChannel)key.channel();
        socketChannel.read(receiveBuffer);
        receiveBuffer.flip();
        String receiveData=decode(receiveBuffer);
        if(receiveData.indexOf("\n")==-1)
            return;
        String outputData=receiveData.substring(0,receiveData.indexOf("\n")+1);
        if(outputData.equalsIgnoreCase("echo:bye\r\n")){

            key.cancel();
            socketChannel.close();
            System.out.println("stop connect with the server--------------");
            selector.close();
            //终止当前运行的Java虚拟机。设置参数作为状态码。
            System.exit(0);
        }
        ByteBuffer temp=encode(outputData);
        //设置此缓冲区的位置。如果标记已定义且大于 然后将新位置丢弃。
        receiveBuffer.position(temp.limit());
        receiveBuffer.compact();
    }


    public String decode(ByteBuffer buffer){
        CharBuffer charBuffer=charset.decode(buffer);
        return charset.toString();
    }
    public ByteBuffer encode(String str){

        return charset.encode(str);
    }

}
