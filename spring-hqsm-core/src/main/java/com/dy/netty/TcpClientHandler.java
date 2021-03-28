package com.dy.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.netty
 * @ClassName: TcpClientHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 17:12
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class TcpClientHandler  extends SimpleChannelInboundHandler<String> {


    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("-----"+ctx.channel().remoteAddress());
        System.out.println("--client output---"+msg);
        //向客户端发送数据
        ctx.channel().write("2222222222");
        ctx.writeAndFlush("23123131231---"+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // super.channelActive(ctx);
        ctx.writeAndFlush("客户端");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      //  super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
