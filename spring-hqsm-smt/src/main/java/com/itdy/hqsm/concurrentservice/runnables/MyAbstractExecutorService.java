package com.itdy.hqsm.concurrentservice.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyAbstractExecutorService {



   // public static void main(String[] args) {
      //  new MyAbstractExecutorService().execute();

    //    getExecutorCompletionService();
  //  }


    /**
     *
     *
     */
    public void execute(){
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(12, 12, 2000, TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<Runnable>(1000));

        List<Handler> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            list.add(new Handler(String.valueOf(i)));
        }
        try {
            //会按照添加到List的次序返回，该方法阻塞至所有任务完成
            List<Future<String>> results = executor.invokeAll(list);
            results.stream().forEach( future -> {
                try {
                    System.out.println("--1-------->"+future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭线程池
        executor.shutdown();
    }

    private class Handler implements Callable<String> {
        //模拟传参，实际可为对象类型
        String value;
        public Handler(String value){
            this.value = value;
        }


        /**
         *
         *
         * @return
         */
        public String call(){
            try {
                //模拟任务的处理过程0-1000ms
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //模拟返回参数，实际可为对象类型
            System.out.println("--------"+value);
            return value + "value";
        }
    }


//---------------------------------------------------------------------------------
   /* public static void getExecutorCompletionService() {
        ExecutorCompletionService<String> service = new ExecutorCompletionService<String>(
                Executors.newCachedThreadPool());
        // 生产者
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    int index = i;
                    service.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception {
                            System.out.println("-12---------->"+index);
                            return "------->" + index;
                        }
                    });
                }
            }
        }.start();


        // 消费者
        new Thread() {
            @Override
            public void run() {
                try {
                    Future<String> takes = service.take();
                     System.out.println("-1----"+takes.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }*/

}