package com.dy.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ProjectName: hqsm-parent   http 长连接
 * @Package: com.dy.netty
 * @ClassName: HeartBeatServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 19:19
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
    System.out.println("---------"+msg.text());
    ctx.channel().writeAndFlush(new TextWebSocketFrame("ffffqqq"+""));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
       // super.handlerAdded(ctx);
        System.out.println("---1------"+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //super.handlerRemoved(ctx);
        System.out.println("---2------"+ctx.channel().id().asLongText());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        System.out.println("----3-----");
        ctx.close();

    }
}
