package com.dy.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ProjectName: hqsm-parent  入栈
 * @Package: com.dy.websocket
 * @ClassName: DecoderMyLongServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 11:40
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderMyLongServerHandler extends MessageToByteEncoder<Long> {

    /**
     *  出栈
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
       System.out.println("------------>"+msg);
        out.writeLong(msg);
    }
}
