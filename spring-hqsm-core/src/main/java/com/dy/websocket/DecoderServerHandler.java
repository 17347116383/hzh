package com.dy.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.websocket
 * @ClassName: DecoderServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:33
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderServerHandler  extends SimpleChannelInboundHandler<Long> {
    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("-----"+ctx.channel().remoteAddress());
        //向客户端发送数据
        // ctx.channel().write("8888888"); //经过所有handler
        //ctx.writeAndFlush("11111111");//下一个handler流动
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(msg+1L);//下一个handler流动  减少handler
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
