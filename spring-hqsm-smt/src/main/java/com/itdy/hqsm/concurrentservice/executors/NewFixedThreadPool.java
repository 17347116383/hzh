package com.itdy.hqsm.concurrentservice.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 newFixedThreadPool创建一个可重用固定线程数的线程，
 以共享的无界队列方式来运行这些线程。在任意点，
 在大多数 nThreads 线程会处于处理任务的活动状态。
 如果在所有线程处于活动状态时提交附加任务，
 则在有可用线程之前，附加任务将在队列中等待
 。如果在关闭前的执行期间由于失败而导致任何线程终止，
 那么一个新线程将代替它执行后续的任务（如果需要）。
 在某个线程被显式地 关闭之前，池中的线程将一直存在。
 */
public class NewFixedThreadPool {


    /**
     * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     * 定长线程池的大小最好根据系统资源进行设置。
     */
    public static void newFixedThreadPoolDemos() {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 100; i++) {
            final int index = i;
            pool.execute(() -> {
                System.out.println("----------execute" + index);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Exception during sleep  ----睡眠中->" + e.getMessage());
                }
            });
        }
        System.out.println("1判断线程池是否已关闭: " + pool.isShutdown() + "   关闭线程池后判断所有任务是否都已完成: " + pool.isTerminated());
        //启动先前提交的有序关闭 执行任务，但不接受新任务。如果已经关闭，调用不会产生额外效果。
        pool.shutdown();
        //无法响应中断的任务可能永远不会终止。
         pool.shutdownNow();
        System.out.println("2判断线程池是否已关闭: " + pool.isShutdown() + "   关闭线程池后判断所有任务是否都已完成: " + pool.isTerminated());
        try {
            Thread.sleep(10000);
            System.out.println("3判断线程池是否已关闭: " + pool.isShutdown() + "   关闭线程池后判断所有任务是否都已完成: " + pool.isTerminated());
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

    }



    /*public static void main(String[] args) {


        try {
            new NewFixedThreadPool().myMain();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/


  public  void myMain() throws ExecutionException, InterruptedException {

       int taskSize =10;
       //创建一个线程池
       ExecutorService pool = Executors.newFixedThreadPool(taskSize);
       // 创建多个有返回值的任务
      List<Future> list = new ArrayList<Future>();
      for (int i = 0; i < taskSize; i++) {
      Callable c = new MyCallable(i + "a");
      // 执行任务并获取 Future 对象
      Future f = pool.submit(c);
      list.add(f);
  }
   // 关闭线程池
   pool.shutdown();
   // 获取所有并发任务的运行结果
   for (Future f : list) {
   // 从 Future 对象上获取任务的返回值，并输出到控制台
   System.out.println("res：-----" + f.get().toString());
   }
        newFixedThreadPoolDemos();
    }


}