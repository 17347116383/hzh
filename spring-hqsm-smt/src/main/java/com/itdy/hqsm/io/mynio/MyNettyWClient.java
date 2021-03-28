package com.itdy.hqsm.io.mynio;

import org.apache.poi.hssf.util.HSSFColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.itdy.hqsm.io.mynio
 * @ClassName: MyNettyWClient
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/8 16:07
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MyNettyWClient {

    public static void main(String[] args) {

    }

    public static void smain() throws IOException {
        SocketChannel  socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//设置非阻塞
        Selector selector = Selector.open();//打开一个选择器。
        socketChannel.register(selector, SelectionKey.OP_CONNECT);  //选择器注册此通道，返回一个选择键。
        socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));//绑定端口

        while (true){
            selector.select();  //选择一组键，其对应的通道已准备好进行I/O操作。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey sel :selectionKeys){
                if (sel.isConnectable()){
                    SocketChannel client =(SocketChannel) sel.channel();
                   if (client.isConnectionPending()){
                       client.finishConnect();
                       ByteBuffer allocate = ByteBuffer.allocate(2048);
                       allocate.put("xxxxxxxxxxxx".getBytes());
                       allocate.flip();
                       //
                       ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                       executorService.submit(()->{
                           allocate.clear();
                           InputStreamReader inputStream = new InputStreamReader(System.in);
                           BufferedReader b = new BufferedReader(inputStream);
                           String s = null;
                           try {
                           s = b.readLine();
                           //
                           allocate.put(s.getBytes());
                           allocate.flip();
                           client.write(allocate);
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       });
                   }
                     //服务器向客户端发送数据才可以收到
                     client.register(selector,SelectionKey.OP_READ);
                   //判断是否可以去读取数据
                }else if (sel.isReadable()){
                    SocketChannel channel = (SocketChannel)sel.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(2048);
                    int read = channel.read(allocate);
                    if (read>0){
                        String s = new String(allocate.array(), 0, read);
                        System.out.println("------------->"+s);
                    }
                }
            }
            selectionKeys.clear();
        }

    }




}
