package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 18:06
 * @Description:
 */
public abstract class ReadPacket extends Packet {
    private PacketHead head;

    /**
     * 从byteBuf中解析内容到packet中
     */
    public final void read(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
        byte[] headLength = byteBufAdapter.readBytes(PacketHead.PACKET_HEAD_LENGTH_BYTES);
        byte sequenceId = byteBufAdapter.readByte();
        head = new PacketHead(headLength, sequenceId);

        //解析body
        readBody(byteBufAdapter, channelContext);
    }

    /**
     * 解析packet body 留给子类实现
     */
    protected abstract void readBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext);

    /**
     * 验证一个packet是否解析成功,由子类选择是否实现
     *
     * @return
     */
    public void validate() {

    }


    public PacketHead getHead() {
        return head;
    }

    public void setHead(PacketHead head) {
        this.head = head;
    }
}
