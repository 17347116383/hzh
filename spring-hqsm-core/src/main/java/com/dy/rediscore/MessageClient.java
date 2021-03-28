package com.dy.rediscore;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: MessageClient
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/16 13:58
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MessageClient {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bventLoopGroup).channel(NioSocketChannel.class).handler(new MessageClientInit());
        //绑定端口
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 9999).sync();
        //阻塞同步
        sync.channel().closeFuture().sync();
        //
        bventLoopGroup.shutdownGracefully();
    }
}
