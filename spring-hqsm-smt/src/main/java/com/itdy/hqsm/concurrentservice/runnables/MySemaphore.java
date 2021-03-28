package com.itdy.hqsm.concurrentservice.runnables;

import java.util.Random;
import java.util.concurrent.*;

/**   concurrent 并发包
 信号量(Semaphore)
 信号量(Semaphore)为多线程协作提供了更为强大的控制用法。
 无论是内部锁Synchronized还是ReentrantLock，一次都只允许一个线程访问资源，
 而信号量可以多个线程访问同一资源。Semaphore是用来保护一个或者多个共享资源的访问，
 Semaphore内部维护了一个计数器，其值为可以访问的共享资源的个数。一个线程要访问共享资源，
 先获得信号量，如果信号量的计数器值大于1，意味着有共享资源可以访问，则使其计数器值减去1，
 再访问共享资源。如果计数器值为0,线程进入休眠。当某个线程使用完共享资源后，释放信号量，
 并将信号量内部的计数器加1，之前进入休眠的线程将被唤醒并再次试图获得信号量。
 和ReentrantLock一样，Semaphore也包含了sync对象，sync是Sync类型；而且，
 Sync是一个继承于AQS的抽象类。Sync包括两个子类：
 "公平信号量"FairSync 和 "非公平信号量"NonfairSync。sync是"FairSync的实例"，
 或者"NonfairSync的实例"；默认情况下，sync是NonfairSync(即，默认是非公平信号量)
 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制


 ReentrantLock  synchronized两种锁机制类型都是“互斥锁”，，互斥是进程同步关系的一种特殊情况，相当于只存在一个临界资源，因此同时最多只能给一个线程提供服务。但是，在实际复杂的多线程应用程序中，可能存在多个临界资源，这时候我们可以借助Semaphore信号量来完成多个临界资源的访问。
 Semaphore基本能完成ReentrantLock的所有工作，使用方法也与之类似，通过acquire()与release()方法来获得和释放临界资源。
 Semaphore的锁释放操作也由手动进行，因此与ReentrantLock一样，为避免线程因抛出异常而无法正常释放锁的情况发生，释放锁的操作也必须在finally代码块中完成。

 */
public class MySemaphore {

    private int customer;
    public MySemaphore() {
        customer = 0;
    }
    /**
     * 银行存钱类
     */
    class Bank {
        private int account = 100;
        public int getAccount() {
            return account;
        }
        public void save(int money) {
            account += money;
        }
    }

    /**
     * 线程执行类，每次存10块钱
     */
    class NewThread implements Runnable {
        private Bank bank;
        private Semaphore semaphore;
        public NewThread(Bank bank, Semaphore semaphore) {
            this.bank = bank;
            this.semaphore = semaphore;
        }

        public void run() {
            //计数器
            int tempCustomer = customer++;
            //返回此信号量中可用的许可证的当前数量。
            if (semaphore.availablePermits() > 0) {
                System.out.println("客户" + tempCustomer + "启动，进入银行,有位置立即去存钱");
            } else {
                System.out.println("客户" + tempCustomer + "启动，进入银行,无位置，去排队等待等待");
            }
            try {
                semaphore.acquire();

                bank.save(10);
                System.out.println(tempCustomer + "银行余额为：" + bank.getAccount());
                //Thread.sleep(10);
                System.out.println("客户" + tempCustomer + "存钱完毕，离开银行");
                semaphore.release(); //释放许可证，将其返回到信号量。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建立线程，调用内部类，开始存钱
     */
    public void useThread() {
        Bank bank = new Bank();
        // 定义5个新号量
        Semaphore semaphore = new Semaphore(5,true);
        // 建立一个缓存线程池
        ExecutorService es = Executors.newCachedThreadPool();
        // 建立10个线程
        for (int i = 0; i < 100; i++) {
            // 执行一个线程
            es.submit(new Thread(new NewThread(bank, semaphore)));
           // es.execute();
        }
        // 关闭线程池
        es.shutdown();
        // 从信号量中获取两个许可，并且在获得许可之前，一直将线程阻塞
        semaphore.acquireUninterruptibly(2);
        System.out.println("到点了，工作人员要吃饭了");
        // 释放两个许可，并将其返回给信号量
        semaphore.release(2);
    }








    //公平信号量
    private Semaphore smp = new Semaphore(3,true);
    private Random rnd = new Random();

    class TaskDemo implements Runnable{
        private String id;
        TaskDemo(String id){
            this.id = id;
        }
        @Override
        public void run(){
            try {
                smp.acquire();
                System.out.println("Thread ----" + id + " is working");
                Thread.sleep(rnd.nextInt(500));
                smp.release();
                System.out.println("Thread ----" + id + " is over");
            } catch (InterruptedException e) {
            }
        }
    }


    //非公平信号量
    private static final Semaphore semaphore=new Semaphore(3);
    //创建线程池
    private static final ThreadPoolExecutor threadPool=new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

    private static class InformationThread extends Thread {
        private final String name;
        private final int age;

        public InformationThread(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ":大家好，我是" + name + "我今年" + age + "岁当前时间为：" + System.currentTimeMillis());
                Thread.sleep(500);
                System.out.println(name + "要准备释放许可证了，当前时间为：" + System.currentTimeMillis());
                System.out.println("当前可使用的许可数为：" + semaphore.availablePermits());
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 测试
     * @param
     */
   // public static void main(String[] args) {
       // MySemaphoreThread test = new MySemaphoreThread();
       // test.useThread();

       /* MySemaphore semaphoreDemo = new MySemaphore();
        //创建的线程池类型，
        ExecutorService se = Executors.newCachedThreadPool();
        se.submit(semaphoreDemo.new TaskDemo("1"));
        se.submit(semaphoreDemo.new TaskDemo("2"));
        se.submit(semaphoreDemo.new TaskDemo("3"));
        se.submit(semaphoreDemo.new TaskDemo("4"));
        se.submit(semaphoreDemo.new TaskDemo("5"));
        se.submit(semaphoreDemo.new TaskDemo("6"));
        se.shutdown();*/


       /* String[] name= {"李明","王五","张杰","王强","赵二","李四","张三"};
        int[] age= {26,27,33,45,19,23,41};
        for(int i=0;i<7;i++) {
            Thread t1=new InformationThread(name[i],age[i]);
            threadPool.execute(t1);
        }*/
  //  }



    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一个机器在生产...");
                //执行业务处理
                Thread.sleep(2000);
                System.out.println("工人"+this.num+"释放出机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

  /*  public static void main(String[] args) {
        int N = 8;            //工人数
        Semaphore semaphore = new Semaphore(5); //机器数目
        for(int i=0;i<N;i++)
            new Worker(i,semaphore).start();
    }*/
}
