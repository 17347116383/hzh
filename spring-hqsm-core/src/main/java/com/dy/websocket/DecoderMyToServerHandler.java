package com.dy.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: DecoderMyServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 11:10
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderMyToServerHandler extends ByteToMessageDecoder {
    /**
     *  入栈
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("----------->>>");
        //判断读取的数据 是否为Long
        if (in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
