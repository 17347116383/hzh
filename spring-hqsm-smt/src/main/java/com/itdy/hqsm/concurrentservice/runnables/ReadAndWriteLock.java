package com.itdy.hqsm.concurrentservice.runnables;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**  锁
 互斥锁在Java中的具体实现就是ReentrantLock
 读写锁在Java中的具体实现就是ReadWriteLock
 *  读写锁
 */
public class ReadAndWriteLock {

    private static ReentrantLock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();

    public ReadAndWriteLock setValue(int value) {
        this.value = value;
        return this;
    }

    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            //模拟读操作
            lock.lock();
            System.out.println("thread:线程 ---读操作---" + Thread.currentThread().getId() + " value:" + value);
            Thread.sleep(10);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public Object handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            //模拟写操作
            lock.lock();
            value = index;
            Thread.sleep(10);
            System.out.println("thread: --写操作-----" + Thread.currentThread().getId() + " value:" + value);
            return value;
        } finally {
            lock.unlock();
        }
    }

    /*  在这里读线程完全并行，而写会阻塞读。
     * 将读写锁改成可重入锁，即将第68 82行代码注释掉那么所有的读和写线程都必须相互等待，程序执行时间会加长
     */
    public  void getCeiShi() throws InterruptedException {
        final ReadAndWriteLock demo = new ReadAndWriteLock();
        demo.setValue(0);
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //读锁
                    demo.handleRead(readLock);
                    //可重入锁
                    demo.handleRead(lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //写锁
                    demo.handleWrite(readLock, (int) (Math.random() * 1000));
                    //可重入锁
                    demo.handleWrite(lock, (int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService exec = new ThreadPoolExecutor(0, 200,
                0, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            exec.execute(readRunnable);
        }
        for (int i = 0; i < 10; i++) {
            exec.execute(writeRunnable);
        }
        exec.shutdown();
        exec.awaitTermination(60, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间：-------" + (endTime - startTime) + "ms");

    }

    //https://www.linuxidc.com/Linux/2019-08/159811.htm
   /* public static void main(String[] args){
        int threadCount = 10;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + Thread.currentThread().getId() + "开始出发");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getId() + "已到达终点");
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("10个线程已经执行完毕！开始计算排名");
    }*/


    /*public static void main(String[] args) throws InterruptedException {
       new ReadAndWriteLock().getCeiShi();
    }*/
}
