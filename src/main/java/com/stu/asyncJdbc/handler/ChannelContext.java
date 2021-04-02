package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.common.exception.ChannelStatusException;
import com.stu.asyncJdbc.packet.ServerHelloPacket;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:10
 * @Description:
 */
public class ChannelContext {
    public static final int STATUS_TCP_CONNECTED = 0;
    public static final int STATUS_RECEIVE_HANDSHAKE_INIT = 1;
    public static final int RESP_HANDSHAKE = 2;
    public static final int MYSQL_CONNECTED = 3;
    public static final int WAIT_RESP = 4;

    private int channelStatus = STATUS_TCP_CONNECTED;
    private ServerHelloPacket serverHelloPacket;

    /**
     * 将该Channel标识为已接受服务端init包状态
     *
     * @param serverHelloPacket
     */
    public void flagReceiveServerHello(ServerHelloPacket serverHelloPacket) {
        if (getChannelStatus() != STATUS_TCP_CONNECTED) throw new ChannelStatusException();
        this.channelStatus = STATUS_RECEIVE_HANDSHAKE_INIT;

        this.serverHelloPacket = serverHelloPacket;
    }

    public int getChannelStatus() {
        return channelStatus;
    }

    public ServerHelloPacket getServerHelloPacket() {
        return serverHelloPacket;
    }
}
