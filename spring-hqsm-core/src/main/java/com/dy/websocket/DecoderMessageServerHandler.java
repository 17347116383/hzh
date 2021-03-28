package com.dy.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: DecoderMessageServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 19:16
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderMessageServerHandler extends MessageToMessageDecoder <Void>{
   /* *//**
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     *//*
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {

    }*/

    /**
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    protected void decode(ChannelHandlerContext ctx, Void msg, List<Object> out) throws Exception {

    }
}
