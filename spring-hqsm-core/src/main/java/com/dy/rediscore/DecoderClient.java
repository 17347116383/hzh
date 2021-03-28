package com.dy.rediscore;

import com.dy.netty.TcpClientInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.rediscore
 * @ClassName: DecoderClient
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/15 10:34
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class DecoderClient {

    public static void main(String[] args) throws InterruptedException {
        //
        EventLoopGroup bventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bventLoopGroup).channel(NioSocketChannel.class).handler(new DecoderClientInit());
        //绑定端口
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 9999).sync();
        //阻塞同步
        sync.channel().closeFuture().sync();
        //
        bventLoopGroup.shutdownGracefully();

    }
}
