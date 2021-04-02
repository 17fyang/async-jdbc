package com.stu.asyncJdbc.packet;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:06
 * @Description:
 */
public abstract class TypicalReadPacket extends ReadPacket {
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
