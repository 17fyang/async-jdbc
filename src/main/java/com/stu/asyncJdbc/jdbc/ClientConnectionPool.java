package com.stu.asyncJdbc.jdbc;

import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 19:46
 * @Description:
 */
public class ClientConnectionPool {
    private ClientStarter clientStarter;
    Set<ChannelPack> usedChannels = new HashSet<>();
    Set<ChannelPack> freeChannels = new HashSet<>();

    protected ClientConnectionPool(ClientStarter clientStarter) {
        this.clientStarter = clientStarter;
    }

    public AsyncStatement createAsyncStatement() {
        return new AsyncStatement(this);
    }

    public void addChannel(Channel channel) {
        ChannelPack channelPack = new ChannelPack(channel);
        freeChannels.add(channelPack);
    }

    public void close() {
        clientStarter.close();
    }
}
