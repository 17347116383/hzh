package com.dy.rediscore;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: MessageServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 13:42
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MessageServerHandler  extends SimpleChannelInboundHandler<Message> {
    int id;
    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("server  ------------");
        System.out.println("长度  ------------"+length);
        System.out.println("内容  ------------"+new String(content, Charset.forName("utf-8")));
        System.out.println("id ------------"+(++this.id));
        String s = UUID.randomUUID().toString();
        byte[] bytes = s.getBytes("utf-8");
        int length1 = s.getBytes("utf-8").toString().length();
        //
        Message message = new Message();
        message.setContent(bytes);
        message.setLength(length1);
        ctx.writeAndFlush(message);

    }


    /**
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // super.channelActive(ctx);
        System.out.println("---------------------->");
    }
}
