package com.stu.asyncJdbc.handler;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:09
 * @Description:
 */
public class ChannelService {
    public static final ChannelService INSTANCE = new ChannelService();
    Map<Channel, ChannelContext> map = new ConcurrentHashMap<>();

    private ChannelService() {
    }

    public boolean hasChannel(Channel channel) {
        return map.containsKey(channel);
    }

    public void addContext(Channel channel, ChannelContext channelContext) {
        map.put(channel, channelContext);
    }

    public ChannelContext getChannelContext(Channel channel) {
        return map.get(channel);
    }
}
