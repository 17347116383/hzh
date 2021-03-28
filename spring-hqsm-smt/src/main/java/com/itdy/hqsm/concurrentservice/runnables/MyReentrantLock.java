package com.itdy.hqsm.concurrentservice.runnables;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**  锁
 *案列测试
 可重入锁，顾名思义，这个锁可以被线程多次重复进入进行获取操作。

 ReentantLock继承接口Lock并实现了接口中定义的方法，除了能完成synchronized所能完成的所有工作外，
 还提供了诸如可响应中断锁、可轮询锁请求、定时锁等避免多线程死锁的方法。
 Lock实现的机理依赖于特殊的CPU指定，可以认为不受JVM的约束，并可以通过其他语言平台来完成底层的实现。
 在并发量较小的多线程应用程序中，ReentrantLock与synchronized性能相差无几，但在高并发量的条件下，
 synchronized性能会迅速下降几十倍，而ReentrantLock的性能却能依然维持一个水准。
 与synchronized会被JVM自动解锁机制不同，ReentrantLock加锁后需要手动进行解锁。
 为了避免程序出现异常而无法正常解锁的情况，使用ReentrantLock必须在finally控制块中进行解锁操作。

 ReentrantLock是独占锁。所谓独占锁，是指只能被独自占领，即同一个时间点只能被一个线程锁获取到的锁。
 ReentrantLock锁包括”公平的ReentrantLock”和”非公平的ReentrantLock”。”公平的ReentrantLock”
 是指”不同线程获取锁的机制是公平的”，而”非公平的ReentrantLock”则是指”
 不同线程获取锁的机制是非公平的”，ReentrantLock是”可重入的锁”

 */
public class MyReentrantLock  implements Runnable {

  /*  public static final Lock lock = new ReentrantLock();
      public static int i = 0;
      @Override
      public void run() {
        for (int j = 0; j < 1000000; j++) {
            lock.lock();
            //lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
                //lock.unlock();
            }
        }
    }*/


     //重入锁ReentrantLock
     public static ReentrantLock lock1 = new ReentrantLock();
     public static ReentrantLock lock2 = new ReentrantLock();

     int lock;
     public MyReentrantLock(int lock) {
        this.lock = lock;
     }

     /*@Override
     public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
                System.out.println("这是线程1");
            } else {
                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
                System.out.println("这是线程 2");
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();//释放锁
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "：线程退出");
        }
    }*/

     /* 使用了tryLock后，线程不会傻傻的等待，而是不同的尝试获取锁，
        因此，只要执行足够长的时间，线程总是会获得所有需要的资源
        lock():获得锁，如果锁已经被占用，则等待。
        lockInterruptibly(): 获得锁，但优先响应中断。
        tryLock():尝试获得锁，如果成功，返回true，失败返回false。该方法不等待，立即返回
        tryLock(long time,TimeUnit unit)，在给定时间内尝试获得锁
        unlock(): 释放锁。注：ReentrantLock的锁释放一定要在finally中处理，否则可能会产生严重的后果
    */
     @Override
    public void run() {
        try {
            if (lock == 1) {
                while (true) {
                    if (lock1.tryLock()) {
                        try {
                            Thread.sleep(100);
                        } finally {
                            lock1.unlock();
                        }
                    }
                    if (lock2.tryLock()) {
                        try {
                            System.out.println("thread " + Thread.currentThread().getId() + " 执行完毕");
                            return;
                        } finally {
                            lock2.unlock();
                        }
                    }
                }
            } else {
                while (true) {
                    if (lock2.tryLock()) {
                        try {
                            Thread.sleep(100);
                        } finally {
                            lock2.unlock();
                        }
                    }
                    if (lock1.tryLock()) {
                        try {
                            System.out.println("thread " + Thread.currentThread().getId() + " 执行完毕");
                            return;
                        } finally {
                            lock1.unlock();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

       //     public static  void main (String [] args) throws InterruptedException {
             /*   MyReentrantLock demo = new MyReentrantLock();
                Thread t1 = new Thread(demo);
                Thread t2 = new Thread(demo);
                t1.start();
                t2.start();
                t1.join();
                t2.join();
                System.out.println(i);*/

              /*  MyReentrantLock r1 = new MyReentrantLock(1);
                MyReentrantLock r2 = new MyReentrantLock(2);
                Thread t1 = new Thread(r1);
                Thread t2 = new Thread(r2);
                t1.start();
                t2.start();
                Thread.sleep(1000);
                //t2线程被中断，放弃锁申请，释放已获得的lock2，这个操作使得t1线程顺利获得lock2继续执行下去；
                //若没有此段代码，t2线程没有中断，那么会出现t1获取lock1，请求lock2，而t2获取lock2，请求lock1的相互等待死锁情况
                t2.interrupt();*/


              /*  MyReentrantLock r1 = new MyReentrantLock(1);
                MyReentrantLock r2 = new MyReentrantLock(2);
                Thread t1 = new Thread(r1);
                Thread t2 = new Thread(r2);
                t1.start();
                t2.start();

            }*/


}
