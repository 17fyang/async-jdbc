package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 10:52
 * @Description:
 */
public abstract class SendPacket extends Packet {
    /**
     * 写入数据到ByteBufAdapter
     *
     * @return
     */
    public final ByteBufAdapter write(ChannelContext channelContext) {
        ByteBufAdapter byteBufAdapter = new ByteBufAdapter();
        writeBody(byteBufAdapter);

        byte[] bodyBytes = byteBufAdapter.getAllBytes();
        int bodyLength = bodyBytes.length;

        //todo 复制了一遍ByteBuf内容 待优化
        ByteBuf writeByteBuf = Unpooled.buffer(bodyLength + PacketHead.PACKET_HEAD_BYTES);

        PacketHead head = new PacketHead(bodyLength, (byte) channelContext.getSequenceId());

        writeByteBuf.writeBytes(head.toBytes());
        writeByteBuf.writeBytes(bodyBytes);

        return new ByteBufAdapter(writeByteBuf);
    }

    /**
     * 写入packet body
     *
     * @param byteBufAdapter
     */
    public abstract void writeBody(ByteBufAdapter byteBufAdapter);
}
