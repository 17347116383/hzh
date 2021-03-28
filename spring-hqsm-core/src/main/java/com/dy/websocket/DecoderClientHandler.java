package com.dy.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: DecoderClientHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:33
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderClientHandler  extends SimpleChannelInboundHandler<Long> {

    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("-----"+ctx.channel().remoteAddress());
        System.out.println("--client output---"+msg);
        //向客户端发送数据
       // ctx.channel().write(msg);
       // ctx.writeAndFlush("23123131231---"+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // super.channelActive(ctx);
        //向服务器端发送数据
        ctx.writeAndFlush(99999L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
