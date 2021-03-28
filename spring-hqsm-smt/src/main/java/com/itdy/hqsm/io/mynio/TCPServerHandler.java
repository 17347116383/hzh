package com.itdy.hqsm.io.mynio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.AttributeKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 *
 */
@Sharable   //表示带注释的ChannelHandler的相同实例 可以多次添加到一个或多个ChannelPipeline中 没有竞态条件。
public class TCPServerHandler extends ChannelInboundHandlerAdapter{
    /**
     * 日志
     */
    private static Log LOGGER = LogFactory.getLog(TCPServerHandler.class);

    /**
     * 每一个channel，都有独立的handler、ChannelHandlerContext、ChannelPipeline、Attribute
     * 所以不需要担心多个channel中的这些对象相互影响。<br>
     * 这里我们使用content这个key，记录这个handler中已经接收到的客户端信息。
     */
    private static AttributeKey<StringBuffer> content = AttributeKey.valueOf("content");


    /* (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TCPServerHandler.LOGGER.info("--1-------channelRead(ChannelHandlerContext ctx, Object msg)");
        /*
         * 我们使用IDE工具模拟长连接中的数据缓慢提交。
         * 由read方法负责接收数据，但只是进行数据累加，不进行任何处理
         * */
        ByteBuf byteBuf = (ByteBuf)msg;
        try {
            StringBuffer contextBuffer = new StringBuffer();
            while(byteBuf.isReadable()) {
                contextBuffer.append((char)byteBuf.readByte());
            }

            //加入临时区域
            StringBuffer content = ctx.channel().attr(TCPServerHandler.content).get();
            if(content == null) {
                content = new StringBuffer();
                ctx.channel().attr(TCPServerHandler.content).set(content);
            }
            content.append(contextBuffer);
        } catch(Exception e) {
            throw e;
        } finally {
            byteBuf.release();
        }
    }

    /* (non-Javadoc)
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        TCPServerHandler.LOGGER.info("--2-----super.channelReadComplete(ChannelHandlerContext ctx)");
        /*
         * 由readComplete方法负责检查数据是否接收完了。
         * 和之前的文章一样，我们检查整个内容中是否有“over”关键字
         * */
        StringBuffer content = ctx.channel().attr(TCPServerHandler.content).get();
        //如果条件成立说明还没有接收到完整客户端信息   // 获取数据成功
        if(content.indexOf("over") == -1) {
            return;
        }

        //当接收到信息后，首先要做的的是清空原来的历史信息
        ctx.channel().attr(TCPServerHandler.content).set(new StringBuffer());

        //准备向客户端发送响应
        ByteBuf byteBuf = ctx.alloc().buffer(1024);
        byteBuf.writeBytes("---------------您好客户端:我是服务器,这是回发响应信息！".getBytes());
        ctx.writeAndFlush(byteBuf);

        /*
         * 关闭，正常终止这个通道上下文，就可以关闭通道了
         * （如果不关闭，这个通道的回话将一直存在，只要网络是稳定的，服务器就可以随时通过这个回话向客户端发送信息）。
         * 关闭通道意味着TCP将正常断开，其中所有的
         * handler、ChannelHandlerContext、ChannelPipeline、Attribute等信息都将注销
         * */
        ctx.close();
    }
}

