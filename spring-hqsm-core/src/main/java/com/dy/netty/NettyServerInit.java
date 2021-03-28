package com.dy.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.netty
 * @ClassName: NettyServerInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/8 21:14
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class NettyServerInit extends ChannelInitializer<SocketChannel> {

    /**
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        //解码器
        pipeline.addLast("httpClientCodec",new HttpClientCodec());
        pipeline.addLast("nettyServerHandler",new NettyServerHandler());
    }
}
