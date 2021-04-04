package com.stu.asyncJdbc.jdbc;

import io.netty.channel.Channel;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 20:19
 * @Description:
 */
public class ChannelPack {

    private boolean useFLag = false;
    private final Channel channel;
    private ClientConnectionPool connectionPool;

    public ChannelPack(Channel channel) {
        this.channel = channel;
    }

    public boolean isUse() {
        return useFLag;
    }

    public void setUseFLag(boolean useFLag) {
        this.useFLag = useFLag;
    }

    public Channel getChannel() {
        return channel;
    }

    public ClientConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(ClientConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
