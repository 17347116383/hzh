package com.dy.rediscore;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: MessageDecoder
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 13:30
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MessageDecoder  extends ReplayingDecoder<Void> {
    /**
     *解码器
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder  ------------");
        int i = in.readInt();
        byte[] by = new  byte[i];
        in.readBytes(by);
        Message message = new Message();
        message.setLength(i);
        message.setContent(by);
        message.getCmdId();
        out.add(message);


    }
}
