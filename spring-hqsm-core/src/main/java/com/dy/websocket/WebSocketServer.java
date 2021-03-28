package com.dy.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ProjectName: hqsm-parent   http 长连接
 * @Package: com.dy.netty
 * @ClassName: HeartBeatServer
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 19:03
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        //允许注册{@link通道}获取 在事件循环期间处理以供以后选择。
        EventLoopGroup boosGroup =new NioEventLoopGroup();
        EventLoopGroup   wookGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap =new ServerBootstrap();
        serverBootstrap.group(boosGroup,wookGroup).
                channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new WebSocketServerInit());
        //绑定端口
        ChannelFuture sync = serverBootstrap.
                bind("127.0.0.1", 9999).sync();
        //阻塞同步
        sync.channel().closeFuture().sync();
        //
        boosGroup.shutdownGracefully();
        wookGroup.shutdownGracefully();
    }
}
