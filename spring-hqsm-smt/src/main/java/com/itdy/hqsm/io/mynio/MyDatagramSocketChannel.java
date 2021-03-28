package com.itdy.hqsm.io.mynio;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;


/**
 *
 *
 *
 */
public class MyDatagramSocketChannel {

   /* private int port[]={8888};
    private Selector selector;*/

    /**
     *
     * @throws IOException
     */
    /*public MyDatagramSocketChannel() throws IOException{
        selector = Selector.open();
        for(int i=0;i<port.length;i++){
            //打开通道
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.socket().bind(new InetSocketAddress("127.0.0.1",port[i]));
            datagramChannel.configureBlocking(false);
            //设置成读取操作
            datagramChannel.register(selector,SelectionKey.OP_READ);
        }
    }*/

    /**
     * 没有绑定固定远程地址和端口，只能使用recive和send
     *    datagramChannel.send(write,socketAddress);  发送方法
     *    datagramChannel.receive(buffer);  读取方法，返回SocketAddress
     * 绑定固定的远程地址和远程端口
     *    TestDatagramSocketChannel的构造函数中加入
     *    SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",9998);
     *    datagramChannel.connect(socketAddress);
     * 后可以直接使用int readLength = datagramChannel.read(buffer);
     * 代替datagramChannel.receive(buffer); 
     * 使用datagramChannel.write(write);代替datagramChannel.send(write,socketAddress);发送数据
     * @throws IOException
     */
  /*  public void testChannel() throws IOException{
        byte bytes[] = new byte[1024*1024];
        int length=0;
        while(true){
            //选择一组键，其对应的通道已为I/O做好准备 操作。
            int num = selector.select();
            System.out.println("------------>"+num);
            if(num>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                if(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    DatagramChannel datagramChannel=null;
                    //测试此密钥的通道是否已准备好读取。如果此键的通道不支持读取操作，则执行此操作方法总是返回false
                    if(selectionKey.isReadable()){
                        datagramChannel = (DatagramChannel)selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(30);
                        //通过此通道接收数据报
                        datagramChannel.receive(buffer);
                        //读取准备
                        buffer.flip();
                        //返回此缓冲区的限制。
                        int readLength = buffer.limit();
                        byte byteread[]=new byte[readLength];
                        ByteBuffer byteBuffer = buffer.get(byteread, 0, readLength);
                        System.out.println("-------byteBuffer------->"+byteBuffer);
                        System.arraycopy(byteread, 0, bytes,length,readLength);
                        length+=readLength;
                        //读取的数据达到10哥字节，设置成写出
                        if(length==10){
                        datagramChannel.register(selector, SelectionKey.OP_WRITE);
                            System.out.println("-------读取的数据达到10哥字节------->");
                         }
                        //如果这个键的通道不支持写操作，那么这个方法总是返回false
                    }else if(selectionKey.isWritable()){
                        //返回为其创建此键的通道。该方法将 即使取消了密钥，也要继续返回通道。
                        datagramChannel = (DatagramChannel)selectionKey.channel();
                        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",9998);
                        datagramChannel.connect(socketAddress);
                        String string = "是打发发斯蒂芬阿斯蒂芬斯蒂芬萨达发个跟帖点";
                        byte []t= string.getBytes("UTF-8");
                        //将字节数组封装到缓冲区中。
                        ByteBuffer write = ByteBuffer.wrap(t);
                        System.out.println("----------------"+write.toString());
                        //表示当前位置和之间是否有任何元素 的极限。
                        while(write.hasRemaining()){
                        // 发送数据方法
                        int send = datagramChannel.send(write, socketAddress);
                        System.out.println("----------->"+send);
                        }
                        datagramChannel.register(selector,SelectionKey.OP_READ);
                    }
                    iterator.remove();
                }
            }
        }
    }

    public static void main(String []args) throws IOException{
        new MyDatagramSocketChannel().testChannel();
    }*/
    public static final int PORT = 30000;
    // 定义每个数据报的最大大小为4KB
    private static final int DATA_LEN = 4096;
    // 定义接收网络数据的字节数组
    byte[] inBuff = new byte[DATA_LEN];
    // 以指定字节数组创建准备接收数据的DatagramPacket对象
    private DatagramPacket inPacket = new DatagramPacket(inBuff , inBuff.length);
    // 定义一个用于发送的DatagramPacket对象
    private DatagramPacket outPacket;
    // 定义一个字符串数组，服务器端发送该数组的元素
    String[] books = new String[]
            {
                    "dfsfsdf",
                    "轻量级Java EE企业应用实战",
                    "asdAndroid",
                    "asdaAjaxsdfvcd"
            };


    public void init()throws IOException
    {
        try(
                // 创建DatagramSocket对象
                DatagramSocket socket = new DatagramSocket(PORT))
        {
            // 采用循环接收数据
            for (int i = 0; i < 1000 ; i++ )
            {
                // 读取Socket中的数据，读到的数据放入inPacket封装的数组里
                socket.receive(inPacket);
                // 判断inPacket.getData()和inBuff是否是同一个数组
                System.out.println(inBuff == inPacket.getData());
                // 将接收到的内容转换成字符串后输出
                System.out.println(new String(inBuff, 0 , inPacket.getLength()));
                // 从字符串数组中取出一个元素作为发送数据
                byte[] sendData = books[i % 4].getBytes();
                // 以指定的字节数组作为发送数据，以刚接收到的DatagramPacket的
                // 源SocketAddress作为目标SocketAddress创建DatagramPacket
                outPacket = new DatagramPacket(sendData, sendData.length , inPacket.getSocketAddress());
                // 发送数据
                socket.send(outPacket);
            }
        }
    }

    /**
     *
     *
     * @param args
     * @throws IOException
     */
    /*public static void main(String[] args) throws IOException
    {
        new MyDatagramSocketChannel().init();
    }*/









}
