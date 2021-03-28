package com.itdy.hqsm.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

/**
 *
 *
 * 配置任务线程池，开启同步任务
 */
@Configuration
public class MyScheduleConfig implements SchedulingConfigurer {

    /**
     * 添加定时任务到线程池
     * @param scheduledTaskRegistrar
     */
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(getScheduler());
    }
    /**
     * 线程池配置类
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler getScheduler(){
        ThreadPoolTaskScheduler taskScheduler=new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(100);
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setThreadNamePrefix("task-");
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return taskScheduler;
    }
    /**
     * 设置定时触发器
     * @return
     */
   /* private Trigger getTrigger(String cron){
        return new Trigger() {
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger trigger=new CronTrigger(cron);
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }*/
}
