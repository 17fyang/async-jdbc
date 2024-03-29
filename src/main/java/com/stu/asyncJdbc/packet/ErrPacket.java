package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 17:42
 * @Description:
 */
public class ErrPacket extends TypicalReadPacket {
    @Override
    public void readPacketBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
        int errCode = byteBufAdapter.readInt2();

        String stateMaker = byteBufAdapter.readString(1);

        String sqlSate = byteBufAdapter.readString(5);

        String msg = byteBufAdapter.readStringEOF();
    }
}
