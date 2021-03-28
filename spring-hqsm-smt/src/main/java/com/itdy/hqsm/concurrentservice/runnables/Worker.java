package com.itdy.hqsm.concurrentservice.runnables;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * 分而治之不算一种模式, 而是一种思想.
 * 它可以将一个大任务拆解为若干个小任务并行执行, 提高系统吞吐量.
 *
 *
 */
public class Worker  implements Runnable {

       private ConcurrentLinkedQueue<Task> workQueue;
       private ConcurrentHashMap<String, Object> resultMap;

              @Override
      public void run() {
                 while (true) {
                         Task input = this.workQueue.poll();
                         // 所有任务已经执行完毕
                         if (input == null) {
                                 break;
                             }
                         // 模拟对task进行处理, 返回结果
                         int result = input.getPrice();
                         this.resultMap.put(input.getId() + "", result);
                         System.out.println("任务执行完毕, 当前线程: ----------" + Thread.currentThread().getName());
                     }
             }
             public ConcurrentLinkedQueue<Task> getWorkQueue() {
                 return workQueue;
             }
             public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
                 this.workQueue = workQueue;
             }
             public ConcurrentHashMap<String, Object> getResultMap() {
                 return resultMap;
             }
             public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
                 this.resultMap = resultMap;
             }




}
