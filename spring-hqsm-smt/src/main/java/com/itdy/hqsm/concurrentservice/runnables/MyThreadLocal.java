package com.itdy.hqsm.concurrentservice.runnables;

/**
 //ThreadLocal，很多地方叫做线程本地变量，也有些地方叫做线程本地存储，其实意思差不多。
 *都知道ThreadLocal为变量在每个线程中都创建了一个副本，
 *那么每个线程可以访问自己内部的副本变量。
 */
public class MyThreadLocal {

   //最常见的ThreadLocal使用场景为 用来解决 数据库连接、Session管理等。

    ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
    ThreadLocal<String> stringLocal = new ThreadLocal<String>();

    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }
    public long getLong() {
        return longLocal.get();
    }
    public String getString() {
        return stringLocal.get();
    }


    /**
     * 测试本地副本   每个线程中创建变量副本
     * @param args
     * @throws InterruptedException
     */
    public  void maind() throws InterruptedException {
        final MyThreadLocal test = new MyThreadLocal();
        test.set();
        System.out.println("---1------"+test.getLong());
        System.out.println("---2------"+test.getString());

        Thread thread1 = new Thread(){
            public void run() {
                test.set();
                System.out.println("--3-------"+test.getLong());
                System.out.println("--4-------"+test.getString());
            };
        };
        thread1.start();
        thread1.join();
        System.out.println("--5-------"+test.getLong());
        System.out.println("--6-------"+test.getString());
         //实际的通过ThreadLocal创建的副本是存储在每个线程自己的threadLocals中的；
         //为何threadLocals的类型ThreadLocalMap的键值为ThreadLocal对象，因为每个线程中可有多个threadLocal变量，就像上面代码中的longLocal和stringLocal；
         //在进行get之前，必须先set，否则会报空指针异常；如果想在get之前不需要调用set就能正常访问的话，必须重写initialValue()方法。
    }


   /* 总之，ThreadLocal不是用来解决对象共享访问问题的，而主要是提供了保持对象的方法和避免参数传递的方便的对象访问方式。
   归纳了两点：
            1。每个线程中都有一个自己的ThreadLocalMap类对象，可以将线程自己的对象保持到其中，各管各的，线程可以正确的访问到自己的对象。
            2。将一个共用的ThreadLocal静态实例作为key，将不同对象的引用保存到不同线程的ThreadLocalMap中，
            然后在线程执行的各处通过这个静态ThreadLocal实例的get()方法取得自己线程保存的那个对象，
            避免了将这个对象作为参数传递的麻烦。
            当然如果要把本来线程共享的对象通过ThreadLocal.set()放到线程中也可以，
            可以实现避免参数传递的访问方式，但是要注意get()到的是那同一个共享对象，并发访问问题要靠其他手段来解决。
            但一般来说线程共享的对象通过设置为某类的静态变量就可以实现方便的访问了，似乎没必要放到线程中。
            ThreadLocal的应用场合，我觉得最适合的是按线程多实例（每个线程对应一个实例）的对象的访问，并且这个对象很多地方都要用到。

*/
 /*  public static void main(String[] args)  {

       try {
           new MyThreadLocal().maind();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }*/
}
