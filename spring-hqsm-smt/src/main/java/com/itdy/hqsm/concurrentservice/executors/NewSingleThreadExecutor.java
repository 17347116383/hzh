package com.itdy.hqsm.concurrentservice.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
 （注意，如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，
 一个新线程将代替它执行后续的任务）。可保证顺序地执行各个任务，
 并且在任意给定的时间不会有多个线程是活动的。
 与其他等效的 newFixedThreadPool(1) 不同，可保证无需重新配置此方法所返回的执行程序即可使用其他的线程。
 */
public class NewSingleThreadExecutor {



    /**
     * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
     * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     * (可通过实现ThreadFactory来实现，newSingleThreadExecutor(ThreadFactory threadFactory))。
     */
    public static void newSingleThreadExecutorDemos(){
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 10; i++){
            final int index = i;
            pool.execute(()->{
                System.out.println("-----------------执行 -> " + index);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    System.out.println("睡眠中-------: " + e.getMessage());
                }
            });
        }
        pool.shutdown();
    }


    /*public static void main(String[] args) {
        newSingleThreadExecutorDemos();
    }*/
}
