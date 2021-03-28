package com.itdy.hqsm.concurrentservice.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
 对于执行很多短期异步任务的程序而言，这些线程池通常可提高程序性能。
 调用 execute 将重用以前构造的线程（如果线程可用）。
 如果现有线程没有可用的，则创建一个新线程并添加到池中。
 终止并从缓存中移除那些已有 60 秒钟未被使用的线程。
 因此，长时间保持空闲的线程池不会使用任何资源。
 注意，可以使用 ThreadPoolExecutor 构造方法创建具有类似属性但细节不同（例如超时参数）的线程池。
 */
public class NewCachedThreadPool {

  //https://www.cnblogs.com/dolphin0520/p/3932921.html

    /**
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，
     * 可灵活回收空闲线程，若无可回收，则新建线程。线程池为无限大，当执行第二个
     * 任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     * 示例代码如下：
     */
    public static void newCachedThreadPoolDemos(){

        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 0; i < 100; i++){
            final int index = i;
          try{
              Thread.sleep(index * 10);
          }catch (InterruptedException e) {
              e.printStackTrace();
          }
           //提交要执行的值返回任务，并返回 表示任务的待处理结果。的 Future的{@code get}方法将返回任务的结果 成功完成。
            pool.submit(()->{
                System.out.println("---------submit->" + index);
                return null;
            });
          //在将来的某个时候执行给定的命令。命令 可以在新线程、池化线程或调用中执行 线程，由{@code Executor}实现决定。
          pool.execute(()->{
              System.out.println("--------------------执行->" + index);
          });
        }
        System.out.println("------------------------------");
        //启动先前提交的有序关闭 执行任务，但不接受新任务。如果已经关闭，调用不会产生额外效果。
        pool.shutdown();
        //试图停止所有正在积极执行的任务 处理等待的任务，并返回任务列表 等待处决。
        pool.shutdownNow();
        System.out.println("判断线程池是否已关闭: " + pool.isShutdown());

        try {
            pool.execute(()->{
                System.out.println("后关闭");
            });
        } catch (Exception e) {
            System.out.println("异常关闭后" + e.getMessage());
        }

    }

   /* public static void main(String[] args) {
        newCachedThreadPoolDemos();
    }*/


    }
