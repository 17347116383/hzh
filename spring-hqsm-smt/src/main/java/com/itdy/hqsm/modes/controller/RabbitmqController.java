package com.itdy.hqsm.modes.controller;



import com.itdy.hqsm.mq.RabbitmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 * @ClassName:  RabbitmqController   
 * @Description:TODO 
 * @author: 
 * @date:   2019年4月28日 下午8:32:11   
 * @version V1.0   
 * @Copyright: 2019 
 *
 */
//@CrossOrigin(origins = "http://domain2.com", maxAge = 3600)
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/a")
public class RabbitmqController {

   @Autowired
    private RabbitmqService rabbitmqService ;



    /**
     * 测试类
     * @Title: directSend   
     * @Description: 
     * @param:       
     * @return: void      
     * @throws
     */
    @RequestMapping("/1")
    public String  directSend() {

    	rabbitmqService.directSend();
    return    "ok";
    }
    /**
     * 测试类
     * @Title: directSend   
     * @Description: 
     * @param:       
     * @return: void      
     * @throws
     */
    @RequestMapping("/2")
    public String topicSend() {
    	rabbitmqService.topicSend();
    	 return    "ok";
    }
    /**
     * 测试类
     * @Title: directSend   
     * @Description: 
     * @param:       
     * @return: void      
     * @throws
     */
    @RequestMapping("/3")
    public String fanoutSend() {
    	rabbitmqService.fanoutSend();
    	 return    "ok";
    }
    /**
     * 测试类
     * @Title: directSend   
     * @Description: 
     * @param:       
     * @return: void      
     * @throws
     */
    @RequestMapping("/4")
    public String headersSend() {
    	rabbitmqService.headersSend();
    	 return    "ok";
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @Description: 消费消息
     * @method: handleMessage
     * @Param: message
     * @return: void
     * @auther: LHL
     * @Date: 2018/11/18 21:41
     */
   /* @RabbitListener(queues = "DirectQueue")
    @RabbitHandler
    public void directMessage(String message) {
           LOGGER.info("DirectConsumer {-----1--------} directMessage :" + message);
    }

    @RabbitListener(queues = "TopicQueue")
    @RabbitHandler
    public void topicMessage(String message) {
        LOGGER.info("TopicConsumer {-------2--------} topicMessage :" + message);
    }

    @RabbitListener(queues = "FanoutQueue")
    @RabbitHandler
    public void fanoutMessage(String message) {
        LOGGER.info("FanoutConsumer {-------3--------} fanoutMessage :" + message);
    }

    @RabbitListener(queues = "HeadersQueue")
    @RabbitHandler
    public void headersMessage(Message message) {
        LOGGER.info("HeadersConsumer {-------4--------} headersMessage :" + message);
    }*/
    
    
    /*@RabbitListener(queues = "DirectQueue")
    @RabbitHandler
    public void directMessage(String sendMessage, Channel channel, Message message) throws Exception {
        try {
            Assert.notNull(sendMessage, "sendMessage------777------ 消息体不能为NULL");
            LOGGER.info("处理MQ消息");
            // prefetchCount限制每个消费者在收到下一个确认回执前一次可以最大接受多少条消息,通过basic.qos方法设置prefetch_count=1,这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message
                channel.basicQos(1);
            LOGGER.info("DirectConsumer {-------888-------} directMessage :" + message);
            // 确认消息已经消费成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{-------999--------}，消息体:{}", message.getMessageProperties().getCorrelationId(),sendMessage,e);
            // 拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/
    
    //https://blog.csdn.net/Amor_Leo/article/details/85085697
}

