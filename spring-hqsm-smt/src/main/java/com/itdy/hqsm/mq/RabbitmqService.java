package com.itdy.hqsm.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 测试类
 * @ClassName:  RabbitmqService   
 * @Description:TODO 
 * @author: 
 * @date:   2019年4月28日 下午8:50:43   
 * @version V1.0   
 * @Copyright: 2019 
 *
 */

@Service
public class RabbitmqService {

	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitSender rabbitSender;

    /**
     * @Description: 发送消息
     * 1.交换机
     * 2.key
     * 3.消息
     * 4消息ID
     * rabbitTemplate.send(message);   发消息;参数对象为org.springframework.amqp.core.Message
     * rabbitTemplate.convertAndSend(message); 转换并发送消息;将参数对象转换为org.springframework.amqp.core.Message后发送,消费者不能有返回值
     * rabbitTemplate.convertSendAndReceive(message) //转换并发送消息,且等待消息者返回响应消息.消费者可以有返回值
     * @method: handleMessage
     * @Param: message
     * @return: void
     * @auther: 
     * @Date: 
     */
    
    public void directSend() {
        String message = "direct 发送消息";
        //rabbitTemplate.convertAndSend("DirectExchange", "DirectKey", message, new CorrelationData(UUID.randomUUID().toString()));
        rabbitSender.sendMessage("DirectExchange", "DirectKey", message);

    }

    @RequestMapping("/2")
    public void topicSend() {
        String message = "topic 发送消息";
        //rabbitTemplate.convertAndSend("TopicExchange", "Topic.Key", message, new CorrelationData(UUID.randomUUID().toString()));
        rabbitSender.sendMessage("TopicExchange", "Topic.Key", message);
    }


    public void fanoutSend() {
        String message = "fanout 发送消息";
        //rabbitTemplate.convertAndSend("FanoutExchange", "", message, new CorrelationData(UUID.randomUUID().toString()));
        rabbitSender.sendMessage("FanoutExchange", "", message);
    }

 
    public void headersSend() {
        String msg = "headers 发送消息";
        MessageProperties properties = new MessageProperties();
        properties.setHeader("headers1", "value1");
        properties.setHeader("headers2", "value2");
        Message message = new Message(msg.getBytes(), properties);
        //rabbitTemplate.convertAndSend("HeadersExchange","",message, new CorrelationData(UUID.randomUUID().toString()));
        rabbitSender.sendMessage("HeadersExchange", "", message);
    }

    
    
}
