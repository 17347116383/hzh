package com.itdy.hqsm.io.mynio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;
import java.util.Scanner;

/**
 *
 *
 *
 */
public class MyDatagramSocketChannelClient /*implements Runnable*/{

   /* private DatagramSocket datagramSocket;

    public MyDatagramSocketChannelClient(int port) throws SocketException{
        datagramSocket = new DatagramSocket(port);
    }*/

    /**
     * 没有绑定固定远程地址和端口，只能使用recive和send
     *    datagramChannel.send(write,socketAddress);  发送方法
     *    datagramChannel.receive(buffer);  读取方法，返回SocketAddress
     * @param str
     * @param address
     * @throws Exception
     */
   /* public void test(String str,SocketAddress address) throws Exception{
        byte [] bytes = str.getBytes("utf-8");
        int sendLength=0;
        DatagramPacket datagramPacket = new DatagramPacket(bytes,0,2,address);
        while(sendLength<bytes.length){
            datagramSocket.send(datagramPacket);
            sendLength+=datagramPacket.getLength();
            System.out.println("-------------->"+sendLength);
            int remain = bytes.length-sendLength;
            int length = (remain>2)?2:remain;
            datagramPacket.setData(bytes,sendLength,length);
        }
    }*/

   /* public static void main(String []args) throws Exception {
        MyDatagramSocketChannelClient sed = new MyDatagramSocketChannelClient(9998);
        new Thread(sed).start();
       sed.test("kyukrtdyavwvbytudx",new InetSocketAddress("127.0.0.1",9998));
    }*/

    /*@Override
    public void run() {
        while(true){
            byte[] bytes = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(bytes,0,20);
            try {
                datagramSocket.receive(datagramPacket);
                System.out.println("--------"+new String(datagramPacket.getData(),0,datagramPacket.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/



    // 定义发送数据报的目的地
    public static final int DEST_PORT = 30000;
    public static final String DEST_IP = "127.0.0.1";
    // 定义每个数据报的最大大小为4KB
    private static final int DATA_LEN = 4096;
    // 定义接收网络数据的字节数组
    byte[] inBuff = new byte[DATA_LEN];
    // 以指定的字节数组创建准备接收数据的DatagramPacket对象
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    // 定义一个用于发送的DatagramPacket对象
    private DatagramPacket outPacket = null;

    /**
     *
     * @throws IOException
     */
    public void init()throws IOException
    {
        try(
                // 创建一个客户端DatagramSocket，使用随机端口
                DatagramSocket socket = new DatagramSocket())
        {
            // 初始化发送用的DatagramSocket，它包含一个长度为0的字节数组
            outPacket = new DatagramPacket(new byte[0] , 0, InetAddress.getByName(DEST_IP) , DEST_PORT);
            // 创建键盘输入流
            Scanner scan = new Scanner(System.in);
            // 不断地读取键盘输入
            while(scan.hasNextLine())
            {
                // 将键盘输入的一行字符串转换成字节数组
                byte[] buff = scan.nextLine().getBytes();
                // 设置发送用的DatagramPacket中的字节数据
                outPacket.setData(buff);
                // 发送数据报
                socket.send(outPacket);
                // 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中
                socket.receive(inPacket);
                System.out.println("-------------->"+new String(inBuff , 0
                        , inPacket.getLength()));
            }
        }
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    /* public static void main(String[] args)
            throws IOException
    {
        new MyDatagramSocketChannelClient().init();
    }*/








}


