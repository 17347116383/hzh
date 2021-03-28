package com.dy.rediscore;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: DecoderClientHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:33
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     *y
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("-----"+ctx.channel().remoteAddress());
        System.out.println("--client output---"+msg);
        //向客户端发送数据
       // ctx.channel().write(msg);
       // ctx.writeAndFlush("23123131231---"+ LocalDateTime.now());
        byte[] bytes = new byte[msg.readableBytes()];
        ByteBuf byteBuf = msg.readBytes(bytes);
        String s = new String(bytes, Charset.forName("utf-8"));
        System.out.println("++++++client output++++++"+s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //向服务器端发送数据
        //ctx.writeAndFlush(99999L);
        for (int i=0 ;i<10;i++){
            ByteBuf asda_sdfshello_asdasdsa = Unpooled.copiedBuffer("ASDA sdfshello asdasdsa    ", Charset.forName("utf-8"));
            ctx.writeAndFlush(asda_sdfshello_asdasdsa);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
