package com.dy.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ProjectName: hqsm-parent  聊天测试
 * @Package: com.dy.netty
 * @ClassName: ChitchatTcpClient
 * @Author: Administrator
 * @Description: ${description}
 * @Date: 2019/11/9 17:54
 * @Version: 1.0
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ChitchatTcpClient {

    public static void main(String[] args) throws InterruptedException {
        //
        EventLoopGroup bventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bventLoopGroup).
        channel(NioSocketChannel.class).handler(new ChitchatTcpClientInit());
        //绑定端口
        Channel sync =
        bootstrap.connect("127.0.0.1", 9999).sync().channel();
        //阻塞同步
        //sync.channel().closeFuture().sync();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        for (;;){
        try {
          sync.writeAndFlush(bufferedReader.readLine()+"\r\n");
        } catch (IOException e) {
          bventLoopGroup.shutdownGracefully();
          e.printStackTrace();
        }
  }



    }
}
