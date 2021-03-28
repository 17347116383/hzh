package com.itdy.hqsm.concurrentservice.runnables;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 *        concurrent 并发包
 有几个要点：
 此类提供对外的操作是同步的；
 用于成对出现的线程之间交换数据；
 可以视作双向的同步队列；
 可应用于基因算法、流水线设计等场景
 接着看api文档，这个类提供对外的接口非常简洁，一个无参构造函数，两个重载的范型exchange方法：
 public V exchange(V x) throws InterruptedException
 public V exchange(V x, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException
 从官方的javadoc可以知道，当一个线程到达exchange调用点时，如果它的伙伴线程此前已经调用了此方法，
 那么它的伙伴会被调度唤醒并与之进行对象交换，然后各自返回。如果它的伙伴还没到达交换点，
 那么当前线程将会被挂起，直至伙伴线程到达——完成交换正常返回；或者当前线程被中断——抛出中断异常；
 又或者是等候超时——抛出超时异常
 *
 */
public class MyExchanger {


    protected static final Logger log = Logger.getLogger(MyExchanger.class);
    private static volatile boolean isDone = false;

    //
    static class ExchangerProducer implements Runnable {
        private Exchanger<Integer> exchanger;
        private static int data = 1;
        ExchangerProducer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !isDone) {
                for (int i = 1; i <= 10; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        data = i;
                        System.out.println("生产之前:--------- " + data);
                        data = exchanger.exchange(data);
                        System.out.println("生产之后: ---------" + data);
                    } catch (InterruptedException e) {
                        log.error(e, e);
                    }
                }
                isDone = true;
            }
        }
    }

    static class ExchangerConsumer implements Runnable {
        private Exchanger<Integer> exchanger;
        private static int data = 0;
        ExchangerConsumer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (!Thread.interrupted() && !isDone) {
                data = 0;
                System.out.println("消费前 : -----------" + data);
                try {
                    //线程阻塞一秒
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
                } catch (InterruptedException e) {
                    log.error(e, e);
                }
                System.out.println("消费后 : -----------" + data);
            }
        }
    }

    /**
     * @param
     */
    public  void mainD() {
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        ExchangerProducer producer = new ExchangerProducer(exchanger);
        ExchangerConsumer consumer = new ExchangerConsumer(exchanger);
        exec.execute(producer);
        exec.execute(consumer);
        exec.shutdown();
        try {
            //阻塞，直到所有任务在关机后完成执行。  请求，或超时，或当前线程   ，管先发生什么，都会被打断。
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e, e);
        }
        boolean shutdown = exec.isShutdown();
        System.out.println("---------"+shutdown);
        //输出结果验证了以下两件事情：
        //exchange方法真的帮一对线程交换了数据；
        //exchange方法真的会阻塞调用方线程直至另一方线程参与交易。
        //https://lixuanbin.iteye.com/blog/2166772
    }


   /* public static void main(String[] args) {
        new MyExchanger().mainD();
    }*/

}
