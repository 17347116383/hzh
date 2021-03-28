package com.dy.rediscore;

import com.dy.netty.TcpServcerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: DecoderServerInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:33
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderServerInit  extends ChannelInitializer<SocketChannel> {
    EventLoopGroup group = new NioEventLoopGroup(3);
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器处理器
        //pipeline.addLast(new DecoderMyLongServerHandler());
       // pipeline.addLast(new DecoderReplayingServerHandler());
      //  pipeline.addLast(new DecoderMyServerHandler());
      //  pipeline.addLast(group ,new DecoderMyServerHandler());
        pipeline.addLast(new DecoderServerHandler());
    }
}
