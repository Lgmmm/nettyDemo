package com.day10.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by lgm
 * on 2020/4/5.
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务端启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        //通过链式编程来设置
        bootstrap.group(bossGroup,workerGroup)//设置两个线程
        .channel(NioServerSocketChannel.class)//设置NioServerSocketChannel作为服务器的通道实现
        .option(ChannelOption.SO_BACKLOG,128) //设置线程队列得到的连接个数
        .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
        .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象（匿名对象）
            //给pipeline设置处理器
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(null);
            }
        });//给我们的workerGroup的EventLoop对应的管道设置处理器

        System.out.println("服务器准备好。。。。");
       //绑定一个端口并且同步，生成了一个ChannelFuture对象
        ChannelFuture cf = bootstrap.bind(6666).sync();

        //对关闭通道进行监听
        cf.channel().closeFuture().sync();

    }
}
