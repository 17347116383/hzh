package com.itdy.hqsm.concurrentservice.runnables;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/***
 *线程的挂起和唤醒
 *
 *在没有LockSupport之前，线程的挂起和唤醒咱们都是通过Object的wait和notify/notifyAll方法实现，
 * 而Object的wait和notify/notifyAll方法只能在同步代码块里用。
 * 而LockSupport  park()和unpark(thread)方法来实现阻塞和唤醒线程的操作，直接调用就可以了
 */
public class MyLockSupport {

        /*
        我们在调用Object的notify和wait方法时如果notify先于wait方法那么程序将会无限制的等下去，
        而如果LockSupport的unpark方法先于park方法则不会无限制的等待。程序依然能够正确的执行
        LockSupport比Object的wait/notify有两大优势：
        LockSupport不需要在同步代码块里 。所以线程间也不需要维护一个共享的同步对象了，实现了线程间的解耦。
        unpark函数可以先于park调用，所以不需要担心线程间的执行的先后顺序。
         */
  //     public static void main(String[] args)throws Exception {
        /* Thread t1 = new Thread(()->{
            int sum = 0;
            for(int i=0;i<10;i++){
                sum+=i;
            }
            LockSupport.park();
            System.out.println("-------->"+sum);
        });
        t1.start();
        Thread.sleep(1000);//等待t1中的求和计算完成，当我们注释此行时，则会导致unpark方法先于park方法。但是程序依然能够正确执行。
        LockSupport.unpark(t1);*/

        //  new MyLockSupport().mains();
        //  new MyLockSupport().Objectmain();
    //       new MyLockSupport().mainLockSupport();
  //  }


    /**
     *  线程的挂起和唤醒咱们都是通过Object的wait和notify/notifyAll方法实现。
     * @throws Exception
     */
    public  void Objectmain()throws Exception {
       /* final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                try {
                    obj.wait();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("----object----"+sum);
            }
        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        obj.notify();
        //以上代码执行有报错信息*/



        final Object obj = new Object();
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                try {
                    synchronized (obj){
                        obj.wait();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("---加synchronized后--"+sum);
            }
        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        synchronized (obj){
            obj.notify();
        }

    }


    /**
     *
     * @throws Exception
     */
    public  void mains()throws Exception {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1000);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,1000, TimeUnit.SECONDS,queue);
        //此任务实现了Callable接口
       // RunnableFuture<T> ftask = newTaskFor(task);//查看源码其实是创建了一个FutureTask对象
        Future<String> future = poolExecutor.submit(()->{
            TimeUnit.SECONDS.sleep(5);
            return "hello";
        });
        //同步阻塞等待线程池的执行结果。
        String result = future.get();
        System.out.println("-------->"+result);
    }


    /**
     * 使用LockSupport类 线程的挂起和唤醒
     * @throws Exception
     */
    public  void mainLockSupport()throws Exception {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                LockSupport.park();
                System.out.println(sum);
            }
        });
        A.start();
        //睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
        Thread.sleep(1000);
        LockSupport.unpark(A);
    }
}





//https://www.liangzl.com/get-article-detail-17602.html