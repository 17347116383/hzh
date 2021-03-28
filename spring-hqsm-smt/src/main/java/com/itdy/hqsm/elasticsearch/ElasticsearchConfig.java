package com.itdy.hqsm.elasticsearch;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @ClassName:  ElasticsearchConfig   
 * @Description:TODO 
 * @author: 
 * @date:   2019年5月12日 下午6:04:47   
 * @version V1.0   
 * @Copyright: 2019 
 *
 */


@Configuration
public class ElasticsearchConfig {

    /*
     Elasticsearch JAVA操作有三种客户端:
     1、TransportClient
     2、JestClient
     3、RestClient
     */
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfig.class);

    /**
     * elk集群地址
     */
   // @Value("${elasticsearch.ip}")
    private String hostName="127.0.0.1";

    /**
     * 端口
     */
   // @Value("${elasticsearch.port}")
    private String port="9300";

    /**
     * 集群名称
     */
   // @Value("${elasticsearch.cluster.name}")
    private String clusterName="elasticsearch";

    /**
     * 连接池
     */
  // @Value("${elasticsearch.pool}")
    private String poolSize="5";

    /**
     * Bean name default  函数名字
     *
     * @return
     */
    @Bean(name = "transportClient")
    public TransportClient transportClient() {
        LOGGER.info("Elasticsearch初始化开始。。。。。");
        TransportClient transportClient = null;
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name", clusterName) //集群名字
                    .put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为5
                    .build();
            //配置信息Settings自定义
            transportClient = new PreBuiltTransportClient(esSetting);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
            transportClient.addTransportAddresses(transportAddress);
        } catch (Exception e) {
            LOGGER.error("elasticsearch TransportClient create error!!", e);
        }
        return transportClient;
    }

}