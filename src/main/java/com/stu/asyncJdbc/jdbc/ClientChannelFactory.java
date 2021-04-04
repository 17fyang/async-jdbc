package com.stu.asyncJdbc.jdbc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 20:00
 * @Description:
 */
public class ClientChannelFactory<T extends Channel> implements ChannelFactory<T> {
    private final Class<? extends T> clazz;
    private final List<T> list = new ArrayList<>();

    public ClientChannelFactory(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T newChannel() {
        try {
            T t = clazz.newInstance();
            list.add(t);
            return t;
        } catch (Throwable t) {
            throw new ChannelException("Unable to create Channel from class " + clazz, t);
        }
    }

    public List<T> getList() {
        return list;
    }
}
