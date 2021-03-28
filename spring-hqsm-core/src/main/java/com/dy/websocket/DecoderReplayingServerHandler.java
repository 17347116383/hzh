package com.dy.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: DecoderReplayingServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 18:31
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderReplayingServerHandler  extends ReplayingDecoder<Void> {
    /**
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        out.add(in.readLong());//将输入的字符转为Long 型
    }
}
