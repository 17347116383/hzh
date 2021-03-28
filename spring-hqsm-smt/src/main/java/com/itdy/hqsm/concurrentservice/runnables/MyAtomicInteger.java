package com.itdy.hqsm.concurrentservice.runnables;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**  原子操作类
 *  java.util.concurrent.atomic
 *
 *AtomicInteger这个类的存在是为了满足在高并发的情况下,原生的整形数值自增线程不安全的问题
 *使用synchronized关键字去做同步的话系统的性能将会大大下降。
 * i++ , ++i  , --i , i++  线程不安全
 AtomicInteger位于java.util.concurrent.atomic包下，是对int的封装，提供原子性的访问和更新操作，
 其原子性操作的实现是基于CAS。
    CAS(compare-and-swap)直译即比较并交换，提供原子化的读改写能力，是Java 并发中所谓 lock-free 机制的基础。
    CAS的思想很简单：三个参数，一个当前内存值V、旧的预期值A、即将更新的值B，
    当且仅当预期值A和内存值V相同时，将内存值修改为B并返回true，否则什么都不做，并返回false。
 CAS的缺点有以下几个方面：
 *ABA问题
 *  如果某个线程在CAS操作时发现，内存值和预期值都是A，就能确定期间没有线程对值进行修改吗？
 *  答案未必，如果期间发生了 A -> B -> A 的更新，仅仅判断数值是 A，可能导致不合理的修改操作。
 *  针对这种情况，Java 提供了 AtomicStampedReference 工具类，通过为引用建立类似版本号（stamp）的方式，
 *  来保证 CAS 的正确性。
 *循环时间长开销大
    CAS中使用的失败重试机制，隐藏着一个假设，即竞争情况是短暂的。大多数应用场景中，
    确实大部分重试只会发生一次就获得了成功。但是总有意外情况，所以在有需要的时候，
    还是要考虑限制自旋的次数，以免过度消耗 CPU。
 只能保证一个共享变量的原子操作
 */
public class MyAtomicInteger {

    //这里value使用了volatile关键字，volatile在这里可以做到的作用是使得多个线程可以共享变量，
    // 但是问题在于使用volatile将使得VM优化失去作用，导致效率较低，所以要在必要的时候使用，
    // 因此AtomicInteger类不要随意使用，要在使用场景下使用。



    public static final AtomicInteger atomicInteger = new AtomicInteger(0);



    private  static void atomicIntegerTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 4; j++) {
                    System.out.println(atomicInteger.getAndIncrement());
                }
            });
        }
        executorService.shutdown();
    }





    public static int value = 0;
    public  void setYi() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 4; j++) {
                    System.out.println(value++);
                }
            });
        }
        executorService.shutdown();
        Thread.sleep(1000);
        System.out.println("最终结果是" + value);
    }





    static long randomTime() {
        return (long) (Math.random() * 1000);
    }
    public  void ceiShi() {
        // 阻塞队列，能容纳100个文件
        final BlockingQueue<File> queue = new LinkedBlockingQueue<File>(100);
        // 线程池
        final ExecutorService exec = Executors.newFixedThreadPool(5);
        final File root = new File("D:\\ISO");
        // 完成标志
        final File exitFile = new File("");
        // 原子整型，读个数
        // AtomicInteger可以在并发情况下达到原子化更新，避免使用了synchronized，而且性能非常高。
        final AtomicInteger rc = new AtomicInteger();
        // 原子整型，写个数
        final AtomicInteger wc = new AtomicInteger();
        // 读线程
        Runnable read = new Runnable() {
            public void run() {
                scanFile(root);
                scanFile(exitFile);
            }
            public void scanFile(File file) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles(new FileFilter() {
                        public boolean accept(File pathname) {
                            return pathname.isDirectory() || pathname.getPath().endsWith(".iso");
                        }
                    });
                    for (File one : files)
                        scanFile(one);
                } else {
                    try {
                        // 原子整型的incrementAndGet方法，以原子方式将当前值加 1，返回更新的值
                        int index = rc.incrementAndGet();
                        System.out.println("Read0: ------" + index + " " + file.getPath());
                        // 添加到阻塞队列中
                        queue.put(file);
                    } catch (InterruptedException e) {

                    }
                }
            }
        };
        // submit方法提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future。
        exec.submit(read);
        // 四个写线程
        for (int index = 0; index < 4; index++) {
            // 写线程
            final int num = index;
            Runnable write = new Runnable() {
                String threadName = "Write" + num;
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(randomTime());
                            // 原子整型的incrementAndGet方法，以原子方式将当前值加 1，返回更新的值
                            int index = wc.incrementAndGet();
                            // 获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。
                            File file = queue.take();
                            // 队列已经无对象
                            if (file == exitFile) {
                                // 再次添加"标志"，以让其他线程正常退出
                                queue.put(exitFile);
                                break;
                            }
                            System.out.println(threadName + ":-- " + index + " " + file.getPath());
                        } catch (InterruptedException e) {
                        }
                    }
                }
            };
            exec.submit(write);
        }
        exec.shutdown();
    }

   // public static void main(String[] args) throws InterruptedException {
      /*  atomicIntegerTest();
        Thread.sleep(1000);
        System.out.println("最终结果是" + atomicInteger.get());  */

        //并发量不够高i++,++i 没有影响  高并发就会丢失数据
        //new MyAtomicInteger().setYi();

        //

    //    new MyAtomicInteger().ceiShi();
   // }
}
