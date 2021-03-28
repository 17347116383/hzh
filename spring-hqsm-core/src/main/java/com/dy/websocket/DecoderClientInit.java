package com.dy.websocket;

import com.dy.netty.TcpClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: DecoderClientInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:34
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderClientInit  extends ChannelInitializer<SocketChannel> {


    /**
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
        //pipeline.addLast(new DecoderMyServerHandler());
        pipeline.addLast(new DecoderReplayingServerHandler());
        pipeline.addLast(new DecoderMyLongServerHandler());

        pipeline.addLast(new DecoderClientHandler());
    }
}
