package com.dy.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.netty
 * @ClassName: HeartBeatServerInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 19:05
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class HeartBeatServerInit  extends ChannelInitializer<SocketChannel> {

    /**
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器  心跳检查判断是否断掉链接
        pipeline.addLast(new IdleStateHandler(5,6,3, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatServerHandler());
    }
}
