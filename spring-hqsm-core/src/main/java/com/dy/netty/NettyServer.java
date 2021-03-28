package com.dy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ProjectName: hqsm-parent
 * @Package: com.dy.netty
 * @ClassName: NettyServer
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/8 20:58
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class NettyServer {



    public static void main(String[] args) throws InterruptedException {
        //特殊的{@link EventExecutorGroup}，允许注册{@link通道}获取 在事件循环期间处理以供以后选择。
        EventLoopGroup  boosGroup =new NioEventLoopGroup();
        EventLoopGroup   wookGroup = new NioEventLoopGroup();

        ServerBootstrap  serverBootstrap =new ServerBootstrap();
        serverBootstrap.group(boosGroup,wookGroup).channel(NioServerSocketChannel.class)
        .childHandler(new NettyServerInit());
        //绑定端口
        ChannelFuture sync = serverBootstrap.bind("127.0.0.1", 9999).sync();
        //阻塞同步
        sync.channel().closeFuture().sync();
        //
        boosGroup.shutdownGracefully();
        wookGroup.shutdownGracefully();


    }
}
