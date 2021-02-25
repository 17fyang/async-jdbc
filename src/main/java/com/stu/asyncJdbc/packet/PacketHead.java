package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.exception.PacketAnalysisException;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 18:08
 * @Description:
 */
public class PacketHead {
    //载荷长度使用的byte个数
    public static final int PACKET_HEAD_LENGTH_BYTES = 3;
    //荷载长度 表示实际内容的长度
    private int contentLength;
    //包id
    private byte sequenceId;

    public PacketHead(byte[] contentLength, byte sequenceId) {
        this.contentLength = PacketHead.toIntContentLength(contentLength);
        this.sequenceId = sequenceId;
    }

    public PacketHead(int contentLength, byte sequenceId) {
        this.contentLength = contentLength;
        this.sequenceId = sequenceId;
    }

    /**
     * 转成Integer类型的长度
     *
     * @return
     */
    public static int toIntContentLength(byte[] length) {
        if (length == null || length.length != PACKET_HEAD_LENGTH_BYTES) throw new PacketAnalysisException();
        return length[0] + length[1] * 256 + length[2] * 256 * 256;
    }

    public int getContentLength() {
        return contentLength;
    }

    public byte getSequenceId() {
        return sequenceId;
    }


    @Override
    public String toString() {
        return "PacketHead{" +
                "length=" + this.getContentLength() +
                ", sequenceId=" + sequenceId +
                '}';
    }
}
