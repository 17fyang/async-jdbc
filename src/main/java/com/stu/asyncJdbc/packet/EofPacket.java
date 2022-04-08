package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/14 19:45
 * @Description:
 */
public class EofPacket extends TypicalReadPacket {
    @Override
    public void readPacketBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
        int warnNum = byteBufAdapter.readInt2();

        int statusFlag = byteBufAdapter.readInt2();
    }
}
