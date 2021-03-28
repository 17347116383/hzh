package com.itdy.hqsm.concurrentservice.executors;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
  创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
 corePoolSize - 池中所保存的线程数，即使线程是空闲的也包括在内。
 */
public class NewScheduledThreadPool {

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行。
     */
    public static void newScheduledThreadPoolDemos(){
        System.out.println("start ... " + new Date());
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
        //表示延迟3秒执行
     /* pool.schedule(()->{
          System.out.println("---------------》delay 3 seconds...");
      }, 3, TimeUnit.SECONDS);*/

        //延迟10秒后每3秒执行一次
        //创建并执行首先启用的定期操作 在给定初始延迟之后，然后用 给定一次执行的终止与…之间的延迟 开始下一个。如果执行任务
        //遇到异常，将禁止后续执行。否则，该任务将仅通过取消或 遗嘱执行人的终止
        pool.scheduleWithFixedDelay(()->{
            System.out.println("--------delay 2 seconds..." + new Date());
        }, 10, 3, TimeUnit.SECONDS);
    }



   /* public static void main(String[] args) {
        newScheduledThreadPoolDemos();
    }*/
}

