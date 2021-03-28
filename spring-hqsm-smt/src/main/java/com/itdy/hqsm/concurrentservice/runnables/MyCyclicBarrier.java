package com.itdy.hqsm.concurrentservice.runnables;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**    concurrent 并发包
 *import java.util.concurrent包
 *i++等运算不具有原子性，是不安全的线程操作之一。JVM为此类操作特意提供了一些同步类，
 * 使得使用更方便，且使程序运行效率变得更高通过相关资料显示，
 * 通常AtomicInteger的性能是ReentantLock的好几倍。
 CyclicBarrier是一个同步辅助类，允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 因为该 barrier 在释放等待线程后可以重用，所以称它为循环的barrier。
 */
public class MyCyclicBarrier {

    // 字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
    // 叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。
    // 我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了。





    /**
     *  挂起当前线程，直至所有线程都到达barrier状态再同时执行后续任务；
     */
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(500);      //以睡眠来模拟写入数据操作
                //业务处理
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                // cyclicBarrier.await();
                //测试   tsxtd();方法
                //cyclicBarrier.await(2000,TimeUnit.MILLISECONDS);
            } catch (Exception e ) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }

    /**测试1
     *每个写入线程执行完写数据操作之后，就在等待其他线程写入操作完毕。
     * 　　当所有线程线程写入操作完毕之后，所有线程就继续进行后续的操作了
     */
    public  void  getDeng() {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++)
            new Writer(barrier).start();
    }

     /*测试2
     如果想在所有线程写入操作完之后，进行额外的其他操作可以为CyclicBarrier提供Runnable参数
     */
     public  void text() {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N,new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName());
            }
        });
        for(int i=0;i<N;i++)
            new Writer(barrier).start();
    }

    /**测试3
     * 看一下为await指定时间的效果
     */
    public  void tsxtd() {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++) {
            if(i<N-1)
                new Writer(barrier).start();
            else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Writer(barrier).start();
            }
        }
    }

    /**测试4
     *    CyclicBarrier是可以重用的
     * @param
     */
    public  void  getAdd() {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++) {
            new Writer(barrier).start();
        }
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CyclicBarrier重用----------");
        for(int i=0;i<N;i++) {
            new Writer(barrier).start();
        }
    }


    /**
     *   测试代码
     * @param args
     */
   /* public static void main(String[] args) {
            //new MyCyclicBarrier().getDeng();
            //new MyCyclicBarrier().text();
            //new MyCyclicBarrier().tsxtd();
            new MyCyclicBarrier().getAdd();
       }*/
   //案例https://www.cnblogs.com/dolphin0520/p/3920397.html
    }

