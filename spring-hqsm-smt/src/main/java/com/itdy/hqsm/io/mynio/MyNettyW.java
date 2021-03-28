package com.itdy.hqsm.io.mynio;

import javafx.beans.binding.ObjectExpression;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.*;

/**
 * @ProjectName: huang
 * @Package: com.h_z_h.cc
 * @ClassName: MyNettyW
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/8 10:30
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MyNettyW {


    private  static Map <String,SocketChannel> map = new HashMap<String,SocketChannel>();

    public static void main(String[] arge) throws IOException {
    //    mymain();
        mn();

    }

    /**
     *
     * @throws IOException
     */
    public static void mymain() throws IOException {
        int [] posr = new int[6];
        posr [0] =8800;
        posr [1] =8801;
        posr [2] =8802;
        posr [3] =8803;
        posr [4] =8804;
        posr [5] =8805;
        Selector open = Selector.open();
        //Class<? extends SelectorProvider> aClass = SelectorProvider.provider().getClass();
        //Class<? extends SelectorProvider> aClass1 = DefaultSelectorProvider.create().getClass();
        for (int i=0 ; i<posr.length;i++){

            ServerSocketChannel open1 = ServerSocketChannel.open();
            open1.configureBlocking(false);//设置是否为阻塞
            ServerSocket socket = open1.socket();  //获取socket
            InetSocketAddress  isad = new InetSocketAddress(posr[i]);  //设置地址
            socket.bind(isad); //绑定
            //注册  把ServerSocketChannel注册到 open通道中
            SelectionKey register = open1.register(open, SelectionKey.OP_ACCEPT);//SelectionKey.OP_ACCEPT 连接事件
        }

         while(true) {
             int select = open.select();
             if (select==0){
                 return ;
             }
             //返回此选择器的选择键集
             Set<SelectionKey> selectionKeys = open.selectedKeys();
             Iterator<SelectionKey> iterator = selectionKeys.iterator();
             while (iterator.hasNext()){
                 SelectionKey next = iterator.next();
                 //判断是否连接
                 if (next.isAcceptable()){
                     //用于面向流的监听套接字的通道
                     ServerSocketChannel    serverSocketChannel= (ServerSocketChannel)next.channel();
                     SocketChannel accept = serverSocketChannel.accept();
                     //配置非阻塞
                     SelectableChannel selectableChannel = accept.configureBlocking(false);
                     selectableChannel.register(open, SelectionKey.OP_READ);
                     iterator.remove();
                     //判断是否可以读
                 }else if(next.isReadable()){
                     SocketChannel    socketChanne= (SocketChannel)next.channel();
                     int  brd =0;
                     while (true){
                         ByteBuffer allocate = ByteBuffer.allocate(512);
                         allocate.clear();
                         int read = socketChanne.read(allocate);
                         if (read<=0){
                             break;
                         }
                         allocate.flip();
                         socketChanne.write(allocate);
                         brd+=read;
                     }
                    System.out.println("--------"+brd+"---------------"+socketChanne);
                     //删除
                     iterator.remove();
                 }
             }
         }

    }


    /**
     *
     * @throws IOException
     */
    public static void mn() throws IOException {
        ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//非阻塞
        ServerSocket socket = serverSocketChannel.socket();
        ServerSocketChannel bind = serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 9999));
        Selector open = Selector.open();
        //连接
        serverSocketChannel.register(open,SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                open.select();
                Set<SelectionKey> selectionKeys = open.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    //SocketChannel关注读写数据
                    final SocketChannel client;
                    try {
                        //判断是否关联
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            client = channel.accept();
                            client.configureBlocking(false);
                            client.register(open, SelectionKey.OP_ACCEPT);

                            map.put(UUID.randomUUID().toString(), client);
                            //读取客服端发来的数据
                        } else if (selectionKey.isReadable()) {
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer allocate = ByteBuffer.allocate(2048);
                            int read = client.read(allocate);
                            if (read > 0) {
                                allocate.flip();
                                Charset charset = Charset.forName("utf-8");
                                String s = String.valueOf(charset.decode(allocate).array());
                                System.out.println("---------"+s);

                                String  sender =null;
                                for (Map.Entry<String ,SocketChannel> entry: map.entrySet()) {
                                    if(client==entry.getValue()){
                                        sender=entry.getKey();
                                    }
                                }
                                for (Map.Entry<String ,SocketChannel> entry: map.entrySet()
                                     ) {
                                    SocketChannel value = entry.getValue();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
                                    byteBuffer.put((sender+"--------"+s).getBytes());
                                    byteBuffer.flip();
                                    value.read(byteBuffer);
                                }

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                    }
                });

                selectionKeys.clear();
            } finally {
             socket.close();
            }
        }
    }


    /**
     *  文件读取
     * @throws IOException
     */
    public static void fliemn() throws IOException {
     String  inputflie = "test.txt";
     String  outputflie ="test1.txt";

        RandomAccessFile  randomAccessFile=new RandomAccessFile(inputflie,"r");
        RandomAccessFile  randomAccessoutput=new RandomAccessFile(outputflie,"rw");
        long length = new File(inputflie).length();
        FileChannel channelinput = randomAccessFile.getChannel();
        FileChannel channeloutput = randomAccessoutput.getChannel();
        //
        MappedByteBuffer map = channelinput.map(FileChannel.MapMode.READ_ONLY, 0, length);
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder charsetDecoder = charset.newDecoder();//解码
        CharsetEncoder charsetEncoder = charset.newEncoder();//编码
        //
        CharBuffer decode = charsetDecoder.decode(map);
        ByteBuffer encode = charsetEncoder.encode(decode);
        int write = channeloutput.write(encode);

        //关闭
        randomAccessFile.close();
        randomAccessoutput.close();
    }
}
