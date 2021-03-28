package com.itdy.hqsm.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *测试1
 */
public class ChatroomClientd {


    //* 套接字，java.net.Socket
    //* 封装了TCP协议，使用它就可以基于TCP协议进行网络通讯。
    // * Socket是运行在客户端的
    private Socket socket;

    //* 构造方法，用来初始化客户端
    public ChatroomClientd() throws Exception {
        //     * 实例化Socket的时候需要传入两个参数：
        //      * 1：服务器地址，通过IP地址可以找到服务器的计算机
        //        * 2：服务器端口，通过端口可以找到服务器计算机上服务端应用程序
        //      * 实例化Socket的过程就是连接的过程，若远程计算机没有响应会抛出异常。
        System.out.println("正在连接服务端····---3----");
        socket = new Socket("localhost",8088);
        System.out.println("已与服务端建立连接---3----");
    }

    // * 入口方法
  /*  public static void main(String[] args) {
        try {
            ChatroomClientd client = new ChatroomClientd();
            client.start();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端启动失败!---3----");
        }
    }*/

    // * 启动客户端的方法
    public void start() {
        try {
            //* Socket提供的方法：
            //* OutputStream getOutputStream
            //* 获取一个字节输出流，通过该流写出的数据会被发送至远端计算机。
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
            PrintWriter pw = new PrintWriter(osw,true);

            //* 启动读取服务端发送过来消息的线程
            ServerHandler handler = new ServerHandler();
            Thread t = new Thread(handler);
            t.start();

            //* 将字符串发送至服务端，，，--漏写发送nick昵称了，
            Scanner scan = new Scanner(System.in);
            while(true) {
                System.out.print("请输入昵称：---3----");
                String nick = scan.nextLine();
                if(nick.length()==0) {
                    System.out.println("输入有误，请重新输入!---3----");
                }else {
                    break;
                }
            }
            while(true) {
                System.out.print("说点什么吧：---3----");
                String str = scan.nextLine();
                /*if("".equals(str)) {
                    break;
                }*/
                pw.println(str);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
                System.out.println("连接结束---3----");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     *该线程用来读取服务端发送过来的消息，并输出到客户端控制台显示。
     */
    class ServerHandler implements Runnable{
        public void run() {
            try {
                InputStream input = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(input,"UTF-8");
                BufferedReader br = new BufferedReader(isr);
                while(true) {
                    String str = br.readLine();
                    System.out.println("服务端回复：---"+str);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
