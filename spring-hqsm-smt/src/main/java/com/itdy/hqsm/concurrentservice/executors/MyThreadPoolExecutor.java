package com.itdy.hqsm.concurrentservice.executors;

import com.itdy.hqsm.common.lang.DateUtils;
import com.itdy.hqsm.modes.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * 　java.uitl.concurrent.ThreadPoolExecutor类是线程池中最核心的一个类，
 * 因此如果要透彻地了解Java中的线程池，必须先了解这个类 。ThreadPoolExecutor类中提供了四个构造方法
 * ExecutorService又是继承了Executor接口
 * AbstractExecutorService是一个抽象类，它实现了ExecutorService接口
 * ThreadPoolExecutor继承了AbstractExecutorService类
 *
 execute()方法实际上是Executor中声明的方法，在ThreadPoolExecutor进行了具体的实现，
 这个方法是ThreadPoolExecutor的核心方法，通过这个方法可以向线程池提交一个任务，交由线程池去执行。
 submit()方法是在ExecutorService中声明的方法，在AbstractExecutorService就已经有了具体的实现，
 在ThreadPoolExecutor中并没有对其进行重写，这个方法也是用来向线程池提交任务的，
 但是它和execute()方法不同，它能够返回任务执行的结果，去看submit()方法的实现，
 会发现它实际上还是调用的execute()方法，只不过它利用了Future来获取任务执行结果
 */
public class MyThreadPoolExecutor {

    private final String s = DateUtils.formatDateTime(new Date());
      /*
        prestartCoreThread()：初始化一个核心线程；
        prestartAllCoreThreads()：初始化所有核心线程
        setCorePoolSize：设置核心池大小
        setMaximumPoolSize：设置线程池最大能创建的线程数目大小
        shutdown()：不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
        shutdownNow()：立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务

        ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
　　    LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；
　　   synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。
        */

    public  void mains() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            //获取结果
            //  Future<?> submit = executor.submit(myTask);
            //保存到list中
            //  list.add(submit);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }




  /* public static void main(String[] args) {
        new MyThreadPoolExecutor().mains();
        mainFuture();
    }*/



    public static void mainFuture( ) throws ExecutionException, InterruptedException, TimeoutException {
        // 创建多个有返回值的任务
        List<Future> list1 = new ArrayList<Future>();
        // 创建多个有返回值的任务
        List<Future> list2 = new ArrayList<Future>();
        // 创建多个有返回值的任务
        List<Future> list3 = new ArrayList<Future>();
        Callable c1 = new MyCallable("3434"+ "a");
        //Callable c2 = new MyCallable("dfgdgrw" + "a");
       // Callable c3 = new MyCallable("xcvnsjtgnrt" + "a");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.AbortPolicy());
        //
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务");
            }
        });


        Future submit = executor.submit(c1);
        //如有必要，最多在给定的计算时间内等待 完成，然后检索其结果(如果可用)。
        Object o1 = submit.get(3, TimeUnit.SECONDS);
        System.out.println("-------->"+ o1.toString());
        Object o = submit.get();
        System.out.println("----->"+ o.toString());



        executor.submit(new Runnable() {
            public void run() {
                System.out.println("执行任务submit1");
            }
        });

        final Future<User> sub = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务submit2");
                new User("111", "666", 34234, "2019-09-01");
            }
        }, new User("231", "123", 34234, "2019-09-01"));
        System.out.println("---2---->"+ sub.get().toString());




        //调用shutdown方法
        // executor.shutdown();
        //添加任务
        executor.execute(new Runnable() {
            @Override
            public void run() {
            System.out.println("执行任务2");
            }
        });
        //再次添加任务
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务3");
            }
        });
        executor.shutdownNow();
    }
}


class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行-------- " + taskNum);
        try {
            Thread.currentThread().sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + taskNum + "执行完毕");
    }




}
