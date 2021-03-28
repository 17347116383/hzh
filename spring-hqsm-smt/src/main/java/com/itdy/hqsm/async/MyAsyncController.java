package com.itdy.hqsm.async;

import com.itdy.hqsm.common.lang.DateUtils;
import com.itdy.hqsm.modes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 *
 *
 */
@RestController
@RequestMapping(value = "/mongo")
public class MyAsyncController {

   // @Autowired
   // private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    //@Autowired
    // private CronService cronService;
   // private ScheduledFuture<?> DynamicTask1Future;
    //private ScheduledFuture<?> DynamicTask2Future;
    @Autowired
    private MyAsync task;


    /**
     *
     * @return
     */
    @GetMapping("/test")
    private Object test() {
        long begin = System.currentTimeMillis();
        try {
            task.test1();
            task.test2();
            task.test3();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("[ " + "Controller耗时 : " + (end - begin) + " ]");
        return "test";
    }










}
