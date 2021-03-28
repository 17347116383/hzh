package com.dy.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ProjectName: hqsm-parent   聊天测试
 * @Package: com.dy.netty
 * @ClassName: ChitchatTcpClientInit
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 17:55
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ChitchatTcpClientInit extends ChannelInitializer<SocketChannel> {


    /**
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
    //    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
    //    pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ChitchatTcpClientHandler());
    }
}
