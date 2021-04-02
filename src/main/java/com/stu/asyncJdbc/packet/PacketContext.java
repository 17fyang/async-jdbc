package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.net.ChannelConnection;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/3/5 23:33
 * @Description:
 */
public class PacketContext {
    ChannelConnection connection;

    public ChannelConnection getConnection() {
        return connection;
    }

    public void setConnection(ChannelConnection connection) {
        this.connection = connection;
    }
}
