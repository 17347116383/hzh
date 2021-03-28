package com.itdy.hqsm.io.mynio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Random;

/**
 * @ProjectName: huang
 * @Package: com.h_z_h.cc
 * @ClassName: MyNetty
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/7 10:24
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MyNetty {

    public static void main(String[] args) throws IOException {
       // mmyain();
      //  mmyain(1);
       // mmyain("");
       // Myain();

        my("");

    }

    public static void mmyain() {
        IntBuffer allocate = IntBuffer.allocate(20);   //
        for (int i =0;i<allocate.remaining() ;i++){
           allocate.put( new Random().nextInt());
        }
        System.out.println("-1--"+allocate.limit());
        allocate.flip();
        System.out.println("-2--"+allocate.limit());
        System.out.println("-3----"+allocate.position());
        System.out.println("-4---"+allocate.capacity());
        while(allocate.hasRemaining()){
            System.out.println("-5---"+allocate.get());
            System.out.println("-6---"+allocate.mark());
        }
    }

    public static void mmyain(int as) throws IOException {
        FileOutputStream outputStream =new FileOutputStream("test.txt");
        //
        FileChannel channel = outputStream.getChannel();
        //定义buffer
        ByteBuffer  buffer=ByteBuffer.allocate(512);
        byte[] msg="qweqw qweqw sfsdf".getBytes();
        for (int i=0 ; i<msg.length;i++){
            buffer.put(msg[i]);
        }
        buffer.flip();
        channel.write(buffer);
        outputStream.close();

    }

    /**
     * 文件读取
     * @param as
     * @throws IOException
     */
    public static void mmyain(String as) throws IOException {
        FileInputStream  inputStream =new FileInputStream("test.txt");
        FileOutputStream outputStream =new FileOutputStream("test1.txt");
        //获取文件通道
        FileChannel channel = inputStream.getChannel();
        FileChannel channel1 = outputStream.getChannel();
        //定义buffer
        ByteBuffer  buffer=ByteBuffer.allocate(2014);

        while(true){
           // buffer.clear();//问题注释后就不断的写入到文件中
            int read = channel.read(buffer);  //读取到buffer中
            if (-1==read)
                break;
            buffer.flip(); //翻转这个缓冲区。限制设置为当前位置，然后位置设置为0
            channel1.write(buffer); //写出到文件
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     *
     * @throws IOException
     */
    public static void Myain( ) throws IOException {
      /*  ByteBuffer  buffer=ByteBuffer.allocate(64);
        buffer.putInt(12);
        buffer.putLong(1231231L);
        buffer.putFloat(12312f);
        buffer.putChar('2');
        buffer.putShort((short)12);
        buffer.flip();
        System.out.println("-----"+buffer.getDouble());*/

        //slice  buffer
        ByteBuffer  buffers=ByteBuffer.allocate(10);//原有buffer
       for (int i =0;i<buffers.capacity();i++){
           buffers.put((byte)i);
       }
       buffers.position(2);
       buffers.limit(6);
       ByteBuffer  sliceBuffer=buffers.slice();//操作同一个buffer操作同一个数组
        for (int i =0;i<sliceBuffer.capacity();i++) {
            byte b = sliceBuffer.get(i);
            sliceBuffer.put(i,(byte)(2*b));
        }
        buffers.position(0);
        buffers.limit(buffers.capacity());
        while(buffers.hasRemaining()){
            System.out.println("-----1--"+buffers.get());
        }
        //只读buffer
        ByteBuffer  bufferss=ByteBuffer.allocate(10);
        for (int i =0;i<bufferss.capacity();i++){
            System.out.println("-----1--"+bufferss.getClass());
            bufferss.put((byte)i);
        }
        ByteBuffer buffer = bufferss.asReadOnlyBuffer();    //只读buffer不可以put数据
    }

    /**
     *  零拷贝
     * @param as
     * @throws IOException
     */
    public static void my(String as) throws IOException {
  /*      FileInputStream  inputStream =new FileInputStream("test.txt");
        FileOutputStream outputStream =new FileOutputStream("test1.txt");
        //获取文件通道
        FileChannel channel = inputStream.getChannel();
        FileChannel channel1 = outputStream.getChannel();
        //定义buffer  //allocateDirect零拷贝
        ByteBuffer  buffer=ByteBuffer.allocateDirect(1024);

        while(true){
            // buffer.clear();//问题注释后就不断的写入到文件中
            int read = channel.read(buffer);  //读取到buffer中
            if (-1==read)
                break;
            buffer.flip(); //翻转这个缓冲区。限制设置为当前位置，然后位置设置为0
            channel1.write(buffer); //写出到文件
        }
        inputStream.close();
        outputStream.close();
*/

        //rw 表示可以写读
        RandomAccessFile  rd= new RandomAccessFile("test1.txt","rw");
        FileChannel channel2 = rd.getChannel();
        MappedByteBuffer map = channel2.map(FileChannel.MapMode.READ_WRITE, 0, 9);
        // 修改文件内容
        map.put(0,(byte)'a');
        map.put(3,(byte) 'f');
        rd.close();
      /*  FileLock lock = channel2.lock(3, 6, true);
        System.out.println("是否有效"+lock.isValid());
        System.out.println("类型"+lock.isShared());//true 表示共享锁
        lock.release();
        rd.close();*/
    }



    public static void my() throws IOException {
    ServerSocketChannel serverSocketChannel =ServerSocketChannel.open();
    InetSocketAddress add= new InetSocketAddress("127.0.0.1",9999);
    serverSocketChannel.socket().bind(add);
    int msgl =2+3+4;  //共九个字节
    ByteBuffer[] bf= new ByteBuffer[3];
        bf [0] = ByteBuffer.allocate(2);  //2个字节
        bf [1] = ByteBuffer.allocate(3);  //3个字节
        bf [2] = ByteBuffer.allocate(4);  //4个字节
        //
        SocketChannel accept = serverSocketChannel.accept();
    while(true){
        int byrd=0;
        while (byrd<msgl){
            //
            long read = accept.read(bf);
            byrd +=read;
            System.out.println("-----总长-------"+byrd);
            //List<ByteBuffer> byteBuffers = Arrays.asList(bf);
            //Stream<ByteBuffer> stream = byteBuffers.stream();
            //Stream<Object> objectStream = stream.map();
            Arrays.asList(bf).stream().map(buffer ->"----position---"+buffer.position()+"-----limit----"+buffer.limit()
            ).forEach(System.out::println);
        }
        //反转
        Arrays.asList(bf).forEach(bff ->{
            bff.flip();
        });
        //读取
        long bywr =0;
        while(bywr<msgl){
            long write = accept.write(bf);
            bywr +=write;
        }
        //
        Arrays.asList(bf).forEach(bff ->bff.clear());
        System.out.println("------"+byrd+"-----"+bywr+"-------"+accept);

    }




    }
}
