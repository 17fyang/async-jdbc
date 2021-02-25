package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.net.ByteBufAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 10:52
 * @Description:
 */
public abstract class SendPacket extends Packet {
    //初始ByteBuf容量
    public static final int INIT_CAPACITY = 128;

    /**
     * 写入数据到ByteBufAdapter
     *
     * @return
     */
    public final ByteBufAdapter write() {
        ByteBufAdapter byteBufAdapter = new ByteBufAdapter(Unpooled.buffer(INIT_CAPACITY));
        writeBody(byteBufAdapter);

        int bodyLength = byteBufAdapter.getByteBuf().arrayOffset();
        System.out.println("--debug 待发送的包长度为：" + bodyLength);

        //todo 复制了一遍ByteBuf内容 待优化
        ByteBuf writeByteBuf = Unpooled.buffer(bodyLength + PacketHead.PACKET_HEAD_BYTES);

        //todo-暂未支持多个序列号的包，所以序列号默认为1
        PacketHead head = new PacketHead(bodyLength, (byte) 1);

        writeByteBuf.writeBytes(head.toBytes());
        writeByteBuf.writeBytes(byteBufAdapter.getByteBuf().array());

        return new ByteBufAdapter(writeByteBuf);
    }

    /**
     * 写入packet body
     *
     * @param byteBufAdapter
     */
    public abstract void writeBody(ByteBufAdapter byteBufAdapter);
}
