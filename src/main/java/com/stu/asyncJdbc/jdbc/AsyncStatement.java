package com.stu.asyncJdbc.jdbc;

import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.handler.PacketHandleFactory;
import com.stu.asyncJdbc.packet.QueryPacket;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 19:10
 * @Description:
 */
public class AsyncStatement {
    private final ClientConnectionPool clientConnectionPool;
    private final ChannelContext channelContext;

    public AsyncStatement(ClientConnectionPool clientConnectionPool, ChannelContext channelContext) {
        this.clientConnectionPool = clientConnectionPool;
        this.channelContext = channelContext;
    }

    public void execute(String sql, IExecuteResult executeResult) {
        QueryPacket queryPacket = new QueryPacket(sql);

        channelContext.flagStartQuery();

        PacketHandleFactory.QUERY_PACKET.write(queryPacket, channelContext);
    }

    public void close() {
        clientConnectionPool.addChannel(channelContext);
    }
}
