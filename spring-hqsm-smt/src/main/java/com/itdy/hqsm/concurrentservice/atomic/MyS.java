package com.itdy.hqsm.concurrentservice.atomic;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

import sun.misc.Unsafe;


/**
 *
 *
 * 比较LongAdder ,Atomic，synchronized 以及使用Unsafe类中实现的cas 和模拟Atomic，在多线程下的效率
 */
public class MyS  extends Thread{

    //整体表现最好,短时间的低并发下比AtomicInteger性能差一点，高并发下性能最高；
    private static LongAdder longAdder = new LongAdder();

    //短时间低并发下，效率比synchronized高，甚至比LongAdder还高出一点，但是高并发下，性能还不如synchronized；不同情况下性能表现很不稳定；可见atomic只适合锁争用不激烈的场景
    private static AtomicInteger atomInteger = new AtomicInteger(0);

    //单线程情况性能最好，随着线程数增加，性能越来越差，但是比cas高
    private static  int $synchronized = 0;

    //高并发下，cas性能最差
    public static volatile int cas = 0;
    //
    private static long casOffset;

    //
    public static  Unsafe UNSAFE;

    static {
        try {
            @SuppressWarnings("ALL")
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            casOffset = UNSAFE.staticFieldOffset(MyS.class.getDeclaredField("cas"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //乐观锁   调用unsafe类实现cas
    public void cas(){
        boolean bl = false;
        int tmp;
        while(!bl){
            tmp = cas;
            bl = UNSAFE.compareAndSwapInt(MyS.class, casOffset, tmp,tmp+1);
        }
    }

    //模拟AtomicInteger的实现
    public void atomicInteger(){
        UNSAFE.getAndAddInt(this, casOffset, 1);
    }


    //对a执行+1操作，执行10000次
    public void run(){
        int i =1;
        while(i<=100){
            //测试AtomicInteger
          // atomInteger.incrementAndGet();

            //atomicInteger实现;
           //atomicInteger();

            //测试LongAdder
           // longAdder.increment();


            //测试volatile和cas  乐观锁
          // cas();

            //测试锁
//            synchronized(lock){
//                ++$synchronized;
//            }

            i++;

        }
    }
   /* public static void main(String[] args){
        long start = System.currentTimeMillis();
        //100个线程
        for(int i =1 ; i<=60;i++ ){
            new MyS().start();
        }

        while(Thread.activeCount()>1){
            Thread.yield();
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("---1-----"+$synchronized);
        System.out.println("---2-----"+atomInteger);
        System.out.println("---3-----"+longAdder);
        System.out.println("---4-----"+cas);
    }*/


}
