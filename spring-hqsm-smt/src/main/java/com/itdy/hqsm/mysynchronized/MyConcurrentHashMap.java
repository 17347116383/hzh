package com.itdy.hqsm.mysynchronized;

import com.itdy.hqsm.concurrentservice.runnables.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
分段锁其实是一种锁的设计，并不是具体的一种锁，对于ConcurrentHashMap而言，其并发的实现就是通过分段锁的形式来实现高效的并发操作。
我们以ConcurrentHashMap来说一下分段锁的含义以及设计思想，ConcurrentHashMap中的分段锁称为Segment，
它即类似于HashMap（JDK7与JDK8中HashMap的实现）的结构，即内部拥有一个Entry数组，
数组中的每个元素又是一个链表；同时又是一个ReentrantLock（Segment继承了ReentrantLock)。
当需要put元素的时候，并不是对整个hashmap进行加锁，而是先通过hashcode来知道他要放在那一个分段中，
然后对这个分段进行加锁，所以当多线程put的时候，只要不是放在一个分段中，就实现了真正的并行的插入。
但是，在统计size的时候，可就是获取hashmap全局信息的时候，就需要获取所有的分段锁才能统计。
分段锁的设计目的是细化锁的粒度，当操作不需要更新整个数组的时候，就仅仅针对数组中的一项进行加锁操作。
 */
public class MyConcurrentHashMap {


    public Map getMyConcurrentHashMap() {
        final int numberint = 10000;

        Map locks = new HashMap();
        List lockKeys = new ArrayList();
        for (int number = numberint; number > 0; number--) {
            Object lockKey = new Object();
            lockKeys.add(lockKey);
            locks.put(lockKey, new Object());
        }
        return locks;
    }


    public void getCeiShiMyConcurrentHashMap() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Task p1 = new Task(23, "张三", 18);
        Task p2 = new Task(23, "张三", 18);
        Task p3 = new Task(23, "张三", 18);
        final Map map = new HashMap<Integer, Task>();
        map.put(1, p1);
        map.put(2, p2);
        map.put(3, p3);
        final CountDownLatch blatch = new CountDownLatch(1);
        final CountDownLatch elatch = new CountDownLatch(3);
        for (int i = 0; i <= 2; i++) {
            final int index = i + 1;
            Runnable run1 = new Runnable() {
                public void run() {
                    try {
                        blatch.await();
                        Thread.sleep((long) (Math.random() * 10));
                        if (map.keySet().contains(index)) {
                            System.out.println(map.get(index).toString() + "选手到达了");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        elatch.countDown();
                    }
                }
            };
            es.submit(run1);
        }
        System.out.println("比赛开始");
        blatch.countDown();//减少锁存器的数量，如果是，则释放所有等待的线程 计数为零。
        elatch.await();
        System.out.println("比赛结束");
        es.shutdown();
    }






    public void doSomeThing(String uid) {
     //   Object lockKey = lockKeys.get(uid.hash() % lockKeys.size());
   //     Object lock = locks.get(lockKey);

     //   synchronized(lock) {
            // do something
     //   }


    }



   /* public static  void main(String[]  arge) {
        try {
            new MyConcurrentHashMap().getCeiShiMyConcurrentHashMap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
