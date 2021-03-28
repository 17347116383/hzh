package com.dy.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ProjectName: hqsm-parent   心跳检查
 * @Package: com.dy.netty
 * @ClassName: HeartBeatServerHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 19:19
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class HeartBeatServerHandler  extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            IdleStateEvent e = (IdleStateEvent) evt;
            String  ee =null;
            switch (e.state()){
                case READER_IDLE:
                    ee="读空";
                    break;
                case WRITER_IDLE:
                    ee="写空";
                    break;
                case ALL_IDLE:
                    ee="读写空";
                    break;

            }
            ctx.channel().close();
        }
    }
}
