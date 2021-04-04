package com.stu.asyncJdbc.jdbc;

import com.stu.asyncJdbc.common.exception.LoginException;
import com.stu.asyncJdbc.handler.PacketChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 12:11
 * @Description:
 */
public class ClientStarter {
    //客户端loop线程数量
    public int loopThreadNum = 3;
    private int connectionNum = 1;
    public final String host;
    public final int port;
    private NioEventLoopGroup clientLoopGroup;
    private final ClientChannelFactory<NioSocketChannel> channelFactory = new ClientChannelFactory<>(NioSocketChannel.class);

    public ClientStarter(String host, int port) {
        this.host = host;
        this.port = port;

    }


    public Bootstrap start() {
        clientLoopGroup = new NioEventLoopGroup(loopThreadNum);
        try {
            Bootstrap clientBootStrap = new Bootstrap();
            clientBootStrap.group(clientLoopGroup);

            //指定ChannelFactory
            clientBootStrap.channelFactory(channelFactory);

            //指定handler
            clientBootStrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new PacketChannelHandler());
                }
            });

            for (int i = 0; i < connectionNum; i++) {
                //连接
                clientBootStrap.connect(host, port).sync();
            }
            return clientBootStrap;
        } catch (Exception e) {
            throw new LoginException("fail to start boot strap", e);
        }
    }

    /**
     * 调用close退出连接，否则不会停止子线程
     */
    public void close() {
        //优雅退出
        clientLoopGroup.shutdownGracefully();
    }

    public ClientChannelFactory<NioSocketChannel> getChannelFactory() {
        return channelFactory;
    }

    public NioEventLoopGroup getClientLoopGroup() {
        return clientLoopGroup;
    }
}
