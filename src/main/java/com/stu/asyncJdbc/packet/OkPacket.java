package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.enumeration.CapabilityFlag;
import com.stu.asyncJdbc.common.enumeration.StatusFlag;
import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/3/5 18:38
 * @Description:
 */
public class OkPacket extends TypicalReadPacket {
    private int affectedRows = 0;
    private int lastInsertId = 0;
    private int statusFlag = 0;
    private int warnings = 0;
    private String info;
    private String sessionStageChange;

    @Override
    public void readPacketBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {

        affectedRows = byteBufAdapter.readLenencInt();
        lastInsertId = byteBufAdapter.readLenencInt();

        statusFlag = byteBufAdapter.readInt2();
        warnings = byteBufAdapter.readInt2();

        int serverCapability = channelContext.getServerCapability();
        if ((serverCapability & CapabilityFlag.CLIENT_SESSION_TRACK) != 0) {
            int len = byteBufAdapter.readInt1();
            this.info = byteBufAdapter.readString(len);

            if ((statusFlag & StatusFlag.SERVER_SESSION_STATE_CHANGED) != 0) {
                len = byteBufAdapter.readInt1();
                this.sessionStageChange = byteBufAdapter.readString(len);
            }

        } else {
            this.info = byteBufAdapter.readStringEOF();
        }
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public int getLastInsertId() {
        return lastInsertId;
    }

    public int getStatusFlag() {
        return statusFlag;
    }

    public int getWarnings() {
        return warnings;
    }

    public String getInfo() {
        return info;
    }

    public String getSessionStageChange() {
        return sessionStageChange;
    }
}
