package com.itdy.hqsm.io.mynio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

/**
 *
 *
 *
 */
public class NettyServer {

  /*  public static void main(String[] args) throws Exception {
        new NettyServer().main();
    }*/

    public  void main() throws Exception {
        //这就是主要的服务启动器
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //下面我们设置线程池
        //BOSS线程池针对一个本地IP的端口，BOSS线程池中有一条线程工作，工作内容也相对简单，就是发现新的连接；
        // Netty是支持同时监听多个端口的，所以BOSS线程池的大小按照需要监听的服务器端口数量进行设置就行了。
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        //WORK线程池：这样的申明方式，主要是为了向读者说明Netty的线程组是怎样工作的
        ThreadFactory threadFactory = new DefaultThreadFactory("work thread pool");
        //CPU个数
        //确定Netty中工作线程池的大小，这个大小一般是物理机器/虚拟机器 可用内核的个数 * 2。work线程池中的线程
        // （如果封装的是JAVA NIO，那么具体的线程实现类就是NioEventLoop）都固定负责指派给它的网络连接的事件监听，
        // 并根据事件状态，调用不同的ChannelHandler事件方法。
        // 而最后一个参数SelectorProvider说明了这个EventLoop所使用的多路复用IO模型为操作系统决定。
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        EventLoopGroup workLoogGroup = new NioEventLoopGroup(processorsNumber * 2, threadFactory, SelectorProvider.provider());
        //指定Netty的Boss线程和work线程
        serverBootstrap.group(bossLoopGroup , workLoogGroup);
        //如果是以下的申明方式，说明BOSS线程和WORK线程共享一个线程池
        //（实际上一般的情况环境下，这种共享线程池的方式已经够了）
        //serverBootstrap.group(workLoogGroup);

        //========================下面我们设置我们服务的通道类型
        //只能是实现了ServerChannel接口的“服务器”通道类
        serverBootstrap.channel(NioServerSocketChannel.class);
        //当然也可以这样创建（那个SelectorProvider是不是感觉很熟悉？）
        //serverBootstrap.channelFactory(new ChannelFactory<NioServerSocketChannel>() {
        //  @Override
        //  public NioServerSocketChannel newChannel() {
        //      return new NioServerSocketChannel(SelectorProvider.provider());
        //  }
        //});

        //========================设置处理器
        //为了演示，这里我们设置了一组简单的ByteArrayDecoder和ByteArrayEncoder
        //Netty的特色就在这一连串“通道水管”中的“处理器”
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            /* (non-Javadoc)
             * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
             */
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new TCPServerHandler());
                ch.pipeline().addLast(new ByteArrayDecoder());
            }
        });

        //设置netty服务器绑定的ip和端口
        //option方法，可以设置这个ServerChannel相应的各种属性（在代码中我们使用的是NioServerSocketChannel）；
        // childOption方法用于设置这个ServerChannel收到客户端事件后，所生成的新的Channel的各种属性
        // （代码中，我们生成的是NioSocketChannel）。详细的option参数可以参见ChannelOption类中的注释说明
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 8083));
        //还可以监控多个端口
        //serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 8084));
        //serverBootstrap.bind(new InetSocketAddress("0.0.0.0", 8085));
    }


}
