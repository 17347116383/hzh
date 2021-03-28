package com.itdy.hqsm.concurrentservice.runnables;

import java.util.concurrent.*;

/**
 *   concurrent 并发包
 *  测试
 *  CountDownLatch
 *
 */
public class MyCountDownLatch {

  //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
  //public void await() throws InterruptedException { };
  //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
  //public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
  //将count值减1
  //public void countDown() { };
  //
  //CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
  // 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
    public  void getMa( ) {
        CountDownLatch latch = new CountDownLatch(3);// 需要等待3个线程
        new Thread() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" 正在执行");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+" 执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" xxx正在执行");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+" xxx执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" ------正在执行");
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+" -------执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();

        try {
            System.out.println("等待3个子线程执行完毕...");
            latch.await();// 判断cd计数器是否为0，否则继续等待
            System.out.println("3个子线程已经执行完毕");
            System.out.println("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }









    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 8;
    private static final long KEEP_ALIVE_TIME = 5L;
    private final static int QUEUE_SIZE = 1600;

    protected final static ExecutorService THREAD_POOL =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));

    public  void getCeis() throws InterruptedException {
        // 新建一个为5的计数器
        CountDownLatch countDownLatch = new CountDownLatch(5);
       // OrderInfo orderInfo = new OrderInfo();
        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            //业务代码
            // orderInfo.setCustomerInfo(new CustomerInfo());
            countDownLatch.countDown();
        });
        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            //业务代码
            // orderInfo.setCustomerInfo(new CustomerInfo());
            countDownLatch.countDown();
        });
        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            //业务代码
            // orderInfo.setCustomerInfo(new CustomerInfo());
            countDownLatch.countDown();
        });
        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            //业务代码
            // orderInfo.setCustomerInfo(new CustomerInfo());
            countDownLatch.countDown();
        });
        THREAD_POOL.execute(() -> {
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            //业务代码
            // orderInfo.setCustomerInfo(new CustomerInfo());
            countDownLatch.countDown();
        });

        //等待一定的时间后count值还没变为0的话就会继续执行
       // countDownLatch.await(1, TimeUnit.SECONDS);
        countDownLatch.await();  // 判断cd计数器是否为0，否则继续等待
        System.out.println("主线程：" + Thread.currentThread().getName());
    }


    /**
     *   测试代码
     * @param args
     */
   /* public static void main(String[] args) {
        //new MyCountDownLatch().getMa();
        try {
            new MyCountDownLatch().getCeis();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

}
