package com.dy.rediscore;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: DecoderServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:33
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderServerHandler  extends SimpleChannelInboundHandler<ByteBuf> {

    int  s;
    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        ByteBuf byteBuf = msg.readBytes(bytes);
        String s = new String(bytes, Charset.forName("utf-8"));
        System.out.println("++++++++++++"+s+"/r/n");
        System.out.println("-------------->"+ (++this.s));
        ByteBuf byteBuf1 = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("UTF-8"));
        //输出到客服端
        ctx.writeAndFlush(byteBuf1);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
