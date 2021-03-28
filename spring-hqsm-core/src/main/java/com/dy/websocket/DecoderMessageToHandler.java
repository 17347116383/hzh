package com.dy.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @ProjectName: hqsm-parent    出栈  入栈
 * @Package: com.dy.websocket
 * @ClassName: DecoderMessageToHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 20:10
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderMessageToHandler  extends MessageToMessageCodec<List, Object>{



    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, List msg, List<Object> out) throws Exception {

    }
}
