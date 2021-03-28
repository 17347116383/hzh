package com.dy.websocket;

import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: ByteBuf
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/14 11:08
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ByteBuf<calss> {

    public static void main(String[] args) {
        ByteBuf bf = new ByteBuf();
      //  bf.daD();
      //  bf.AtomicMarkableReferenc();
        bf.AtomicMarkableReferenc(12);
    }
    /**
     * 堆上缓冲区
     * @param
     */
    public  void daD() {
        //utf-8 可变字符长度
        //Unpooled 未池化的用完就销毁
       io.netty.buffer.ByteBuf fgsfaefsd_awdqw = Unpooled.copiedBuffer("fgsfaefsd awdqw", Charset.forName("utf-8"));
       //判断是都为堆
       if (fgsfaefsd_awdqw.hasArray()){
           byte[] array = fgsfaefsd_awdqw.array();
           System.out.println(new String( array,Charset.forName("utf-8")));
           System.out.println(fgsfaefsd_awdqw.readerIndex());
           System.out.println(fgsfaefsd_awdqw.writerIndex());
           System.out.println(fgsfaefsd_awdqw.arrayOffset());
           System.out.println(fgsfaefsd_awdqw.capacity());
           System.out.println(fgsfaefsd_awdqw.resetReaderIndex());


       }
        //混合缓冲
        CompositeByteBuf byteBufs = Unpooled.compositeBuffer();
        //堆缓冲
        io.netty.buffer.ByteBuf buffer = Unpooled.buffer(10);
        //堆外缓冲/直接缓冲
        io.netty.buffer.ByteBuf byteBuf = Unpooled.directBuffer();
        //
        CompositeByteBuf byteBufs1 = byteBufs.addComponents(buffer, byteBuf);
        CompositeByteBuf byteBufs2 = byteBufs.removeComponent(0);
        //
        Iterator<io.netty.buffer.ByteBuf> iterator = byteBufs.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        byteBufs.forEach(System.out::println);

        // ByteBuffer  JDK中的final byte[] hb;是最终的。
        // ByteBuffer bf= new DirectByteBuffer();
        // ByteBuffer bf= new HeapByteBuffer();
    }


    /**原子操作类
     * 维护对象引用以及可以原子更新的标记位   AtomicMarkableReference 修改包装类型
     * AtomicIntegerFieldUpdater
     */
    public  void AtomicMarkableReferenc() {
        Person p = new Person();
        for (int i =0; i<20; i++){
            Thread  thread = new Thread(() ->{
                try {
                    Thread.sleep(100);//多线程不安全
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(p.s++);
            });
            thread.start();
        }
    }


    /**原子操作类
     * 维护对象引用以及可以原子更新的标记位   AtomicMarkableReference 修改包装类型
     * AtomicIntegerFieldUpdater
     */
    public  void AtomicMarkableReferenc(int sd) {
        Person pp = new Person();
        AtomicIntegerFieldUpdater<Person> ss = AtomicIntegerFieldUpdater.newUpdater(Person.class, "ad");
        for (int i =0; i<20; i++){
            Thread  thread = new Thread(() ->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //
                System.out.println(ss.getAndIncrement(pp));
            });
            thread.start();
        }
    }


}
/**
 *
 */
class  Person{
    volatile int ad=1;
    volatile  int  s = 1;
}