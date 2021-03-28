package com.dy.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.netty
 * @ClassName: NettyServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/8 21:22
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        msg.getClass();
        ctx.channel().remoteAddress();
        Thread.sleep(2000);
        if (msg instanceof HttpRequest){
            HttpRequest httpRequest=   (HttpRequest)msg;
            httpRequest.method().name();
            URI ur=new URI(httpRequest.uri());

        }
        ByteBuf content= Unpooled.copiedBuffer("XXXXXXX XXXX", CharsetUtil.UTF_8);
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush("XXXXXXXXXX");//下一个handler流动  减少handler
            }
        });
        ctx.writeAndFlush(defaultFullHttpResponse);
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----1----------");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----2----------");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----3----------");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----4----------");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----5----------");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("-----6----------");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----7----------");
        super.channelUnregistered(ctx);
    }
}
