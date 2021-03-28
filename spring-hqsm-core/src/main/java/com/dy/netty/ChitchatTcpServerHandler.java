package com.dy.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @ProjectName: hqsm-parent   聊天测试服务
 * @Package: com.dy.netty
 * @ClassName: ChitchatTcpHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 17:29
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ChitchatTcpServerHandler extends SimpleChannelInboundHandler<String> {

     static  ChannelGroup   channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach( channels -> {
            if (channel!=channels){
                channels.writeAndFlush(channel.remoteAddress() + "----" +msg+"11111111");
            }else {
                channels.writeAndFlush("zj"+"--------");
            }
        });
    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //super.handlerAdded(ctx);
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("---加入----"+channel.remoteAddress()+"");
        channelGroup.add(channel);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //super.handlerRemoved(ctx);
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("---离开----"+channel.remoteAddress()+"");
        System.out.println("-----"+channelGroup.size());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // super.channelActive(ctx);
        Channel channel = ctx.channel();
        System.out.println("--上---"+channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      //  super.channelInactive(ctx);
        Channel channel = ctx.channel();
        System.out.println("--下---"+channel.remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
