package com.dy.rediscore;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: MessageClientHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 14:40
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MessageClientHandler  extends SimpleChannelInboundHandler<Message> {
        int as;
    /**
     *y
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.out.println("-----"+ctx.channel().remoteAddress());
        System.out.println("--client output---"+msg);
        //客户端接收数据
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("---------"+length);
        System.out.println("------"+new String(content,Charset.forName("UTF-8")));
        System.out.println("---"+(++this.as));



    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        //向服务器端发送数据
        //ctx.writeAndFlush(99999L);
        for (int i=0 ;i<10;i++){
           String s ="client   -------";
            byte[] bytes = s.getBytes(Charset.forName("utf-8"));
            int length = s.getBytes(Charset.forName("utf-8")).length;
            Message message = new Message();
            message.setLength(length);
            message.setContent(bytes);
            ctx.writeAndFlush(message);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
