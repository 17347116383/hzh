package com.dy.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: hqsm-parent  http 长连接
 * @Package: com.dy.netty
 * @ClassName: HeartBeatServerInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 19:05
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class WebSocketServerInit extends ChannelInitializer<SocketChannel> {

    /**
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
       pipeline.addLast(new HttpServerCodec());
       pipeline.addLast(new ChunkedWriteHandler());
       pipeline.addLast(new HttpObjectAggregator(8192));
       pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));//ws协议
       pipeline.addLast(new WebSocketServerHandler());
    }
}
