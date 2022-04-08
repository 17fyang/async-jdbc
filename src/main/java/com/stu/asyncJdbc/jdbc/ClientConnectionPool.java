package com.stu.asyncJdbc.jdbc;

import com.stu.asyncJdbc.handler.ChannelContext;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 19:46
 * @Description:
 */
public class ClientConnectionPool {
    private final ClientStarter clientStarter;
    LinkedBlockingQueue<ChannelContext> freeChannels = new LinkedBlockingQueue<>();

    protected ClientConnectionPool(ClientStarter clientStarter) {
        this.clientStarter = clientStarter;
    }

    public AsyncStatement createAsyncStatement() {
        try {
            ChannelContext channelContext = freeChannels.take();
            return new AsyncStatement(this, channelContext);
        } catch (InterruptedException e) {
            throw new RuntimeException("fail to build connect");
        }
    }

    public void addChannel(ChannelContext channel) {
        freeChannels.add(channel);
    }

    public void closeAll() {
        clientStarter.close();
    }
}
