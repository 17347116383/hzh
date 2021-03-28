package com.itdy.hqsm.async;



import java.util.Date;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
@Async
public class MyAsync {

        public Future<String> test1() throws InterruptedException {
            long begin = System.currentTimeMillis();
            Thread.sleep(1000);
            long end = System.currentTimeMillis();
            System.out.println("[ " + "任务1耗时 : " + (end - begin) + " ]");
            return new AsyncResult<String>("任务1");
        }

        public Future<String> test2() throws InterruptedException {
            long begin = System.currentTimeMillis();
            Thread.sleep(2000);
            long end = System.currentTimeMillis();
            System.out.println("[ " + "任务2耗时 : " + (end - begin) + " ]");
            return new AsyncResult<String>("任务2");
        }

        public Future<String> test3() throws InterruptedException {
            long begin = System.currentTimeMillis();
            Thread.sleep(3000);
            long end = System.currentTimeMillis();
            System.out.println("[ " + "任务3耗时 : " + (end - begin) + " ]");
            return new AsyncResult<String>("任务3");
        }

        //cron 定时任务表达式 @Scheduled(cron="*/1 * * * * *") 表示每秒
        //cron工具 https://tool.lu/crontab/    或者 http://cron.qqe2.com/
        //fixedRate: 定时多久执行一次（上一次开始执行时间点后xx秒再次执行；）
        //fixedDelay: 上一次执行结束时间点后xx秒再次执行
        //fixedDelayString: 字符串形式，可以通过配置文件指定
        //cron = "0/1 00 24 * * ?" 每天11:40触发，每一秒执行一次
        //@Scheduled(cron = "0/1 00 24 * * ?")
        /**
         *
         */
        @Scheduled(fixedRate = 100000)
        public void test() {
            System.out.println("[ " + "每十秒执行一次当前时间 : " + new Date() + " ]");
        }
}
