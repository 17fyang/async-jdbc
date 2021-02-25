package com.stu.asyncJdbc.net;

import com.stu.asyncJdbc.handler.PacketChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 12:11
 * @Description:
 */
public class ClientBootstrap {
    //客户端loop线程数量
    public static final int LOOP_THREAD_NUMBER = 2;
    public static final String HOST = "120.79.175.145";
    public static final int PORT = 3306;

    public static void main(String[] args) {
        NioEventLoopGroup clientLoopGroup = new NioEventLoopGroup(LOOP_THREAD_NUMBER);
        try {
            Bootstrap clientBootStrap = new Bootstrap();
            clientBootStrap.group(clientLoopGroup);
            //指定为socketChannel
            clientBootStrap.channel(NioSocketChannel.class);
            //指定handler
            clientBootStrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new PacketChannelHandler());
                }
            });
            //连接
            ChannelFuture channelFuture = clientBootStrap.connect(HOST, PORT).sync();
            //设置监听关闭通道事件
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //优雅退出
            clientLoopGroup.shutdownGracefully();
        }
    }
}
