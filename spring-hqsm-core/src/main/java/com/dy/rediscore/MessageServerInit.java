package com.dy.rediscore;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: MessageServerInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 13:44
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MessageServerInit    extends ChannelInitializer<SocketChannel> {

    EventLoopGroup group = new NioEventLoopGroup(3);

    /**
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器处理器
        pipeline.addLast(new MessageDecoder());
         pipeline.addLast(new MessageEncoder());
        //  pipeline.addLast(new DecoderMyServerHandler());
        //  pipeline.addLast(group ,new DecoderMyServerHandler());
        pipeline.addLast(new MessageServerHandler());
    }
}
