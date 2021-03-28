package com.itdy.hqsm.concurrentservice.executors;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *

 */
public class ABA {

    /**
     AtomicStampedReference用来解决AtomicInteger中的ABA问题，该demo企图将integer的值从0一直增长到1000，
     但当integer的值增长到128后，将停止增长。出现该现象有两点原因：
     1、使用int类型而非Integer保存当前值，
     2、Interger对-128~127的缓存

     */

    //原子操作类
    private static AtomicInteger atomicInt = new AtomicInteger(100);
    //
    private static AtomicStampedReference<Integer> atomicStampedRef =
            new AtomicStampedReference<Integer>(128, 0);

    public  void getCeiShi() throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 128);
                atomicInt.compareAndSet(128, 100);
            }
        });

        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean c3 = atomicInt.compareAndSet(100, 128);
                System.out.println("--1------"+c3);        //true
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedRef.compareAndSet(100, 128,
                        atomicStampedRef.getStamp(), atomicStampedRef.getStamp()+1);
                atomicStampedRef.compareAndSet(128, 100,
                        atomicStampedRef.getStamp(), atomicStampedRef.getStamp()+1);
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedRef.getStamp();
                System.out.println("标记之前----- " + stamp);    // stamp = 0
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("标记之后----- " + atomicStampedRef.getStamp());//stamp = 1
                boolean c3 = atomicStampedRef.compareAndSet(100, 128, stamp, stamp+1);
                System.out.println("---2---------"+c3);        //false
            }
        });
        refT1.start();
        refT2.start();
    }

   /* public static void main(String[] args) throws InterruptedException {

        new ABA().getCeiShi();
    }*/
}
