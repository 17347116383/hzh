package com.dy.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ProjectName: hqsm-parent 聊天测试
 * @Package: com.dy.netty
 * @ClassName: ChitchatTcpClientHandler
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 17:55
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ChitchatTcpClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("--------"+msg.toString());
    }
}
