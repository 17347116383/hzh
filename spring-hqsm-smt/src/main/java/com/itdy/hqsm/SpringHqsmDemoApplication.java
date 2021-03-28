package com.itdy.hqsm;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/9/21
 * @Author Administrator
 */
@SpringBootApplication
//@MapperScan("com.itdy.hqsm.security.shiro.dao.mapper")//扫描mapper接口
@EnableTransactionManagement   //事物开启
@EnableAsync   //Spring通过任务执行器（TaskExecutor）来实现多线程和并发编程。使用ThreadPoolTaskExecutor可实现一个基于线程池的TaskExecutor.在开发中实现异步任务,配置类中添加@EnableAsync开始对异步任务的支持，方法中使用@Async注解来声明一个异步任务。
//@EnableJms  //ActiveMQ
//@ServletComponentScan  //自定义过滤器
@EnableScheduling  // 定时任务
@EnableKafka   //Kafka
public class SpringHqsmDemoApplication {

    public static void main(String[] args) {

        // 设置环境变量，解决Es的netty与Netty服务本身不兼容问题
            //System.setProperty("es.set.netty.runtime.available.processors","false");
            SpringApplication.run(SpringHqsmDemoApplication.class, args);

}

}
