package com.itdy.hqsm.concurrentservice.runnables;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**高并发
 * 分而治之不算一种模式, 而是一种思想.
 * 它可以将一个大任务拆解为若干个小任务并行执行, 提高系统吞吐量.
 *
 */
public class Master {


              // 盛装任务的集合
              private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<Task>();
              // 所有worker
              private HashMap<String, Thread> workers = new HashMap<>();
              // 每一个worker并行执行任务的结果
              private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

              public Master(Worker worker, int workerCount) {
                 // 每个worker对象都需要持有queue的引用, 用于领任务与提交结果
                 worker.setResultMap(resultMap);
                 worker.setWorkQueue(workQueue);
                 for (int i = 0; i < workerCount; i++) {
                         workers.put("子节点:----- " + i, new Thread(worker));
                     }
             }

             /*
              *提交任务
              */
             public void submit(Task task) {
                 workQueue.add(task);
             }

             /*
              *启动所有的子任务
              *
              */
             public void execute(){
                 for (Map.Entry<String, Thread> entry : workers.entrySet()) {
                         entry.getValue().start();
                     }
             }

             /*
              *判断所有的任务是否执行结束
              *
              */
             public boolean isComplete() {
                 for (Map.Entry<String, Thread> entry : workers.entrySet()) {
                         if (entry.getValue().getState() != Thread.State.TERMINATED) {
                                 return false;
                             }
                     }
                 return true;
             }

             /*
              *获取最终汇总的结果
              */
             public int getResult() {
                 int result = 0;
                 for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                         result += Integer.parseInt(entry.getValue().toString());
                     }
                 return result;
             }



    /*public static  void  main (String[] arge){
        Master master = new Master(new Worker(), 10);
        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            task.setId(i);
            task.setName("任务" + i);
            task.setPrice(new Random().nextInt(10000));
            master.submit(task);
        }
        master.execute();
        while (true) {
            if (master.isComplete()) {
                System.out.println("执行的结果为: " + master.getResult());
                break;
            }
        }
    }*/



   /* public static void main(String[] args) throws ExecutionException, InterruptedException {


        // Lock lock =new ReentrantLock();
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key", 1);
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        //CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //是原子操作。
                    int key = map.get("key") + 1;
                    map.put("key", key);
                    System.out.println("------------>"+key);
                }
            });
        }
        Thread.sleep(500); //模拟等待执行结束
        System.out.println("------" + map.get("key") + "------");
        executorService.shutdown();
    }*/
}
