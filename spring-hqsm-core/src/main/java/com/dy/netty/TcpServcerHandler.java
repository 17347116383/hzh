package com.dy.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.netty
 * @ClassName: TcpServcerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 16:55
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class TcpServcerHandler extends SimpleChannelInboundHandler<String> {
    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
     System.out.println("-----"+ctx.channel().remoteAddress());
     //向客户端发送数据
     ctx.channel().write("1231231231231"); //经过所有handler
     //ctx.writeAndFlush("11111111");//下一个handler流动
     ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush("XXXXXXXXXX");//下一个handler流动  减少handler
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().pipeline();
        ctx.close();
    }
}
