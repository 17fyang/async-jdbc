package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:06
 * @Description:
 */
public abstract class TypicalReadPacket extends ReadPacket {
    private byte type;

    @Override
    protected final void readBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
        setType(byteBufAdapter.readByte());

        readPacketBody(byteBufAdapter, channelContext);
    }

    public abstract void readPacketBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext);

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
