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
    //packet head使用的byte个数
    public static final int PACKET_HEAD_BYTES = 4;
    //荷载长度 表示实际内容的长度
    private final int contentLength;
    //包id
    private final byte sequenceId;

    public PacketHead(byte[] length, byte sequenceId) {
        //转成Integer类型的长度
        if (length == null || length.length != PACKET_HEAD_LENGTH_BYTES) throw new PacketAnalysisException();
        this.contentLength = length[0] + length[1] * 256 + length[2] * 256 * 256;

        this.sequenceId = sequenceId;
    }

    public PacketHead(int contentLength, byte sequenceId) {
        this.contentLength = contentLength;
        this.sequenceId = sequenceId;
    }

    /**
     * 转成byte数组输出(小端)
     *
     * @return
     */
    public byte[] toBytes() {
        byte[] result = new byte[PACKET_HEAD_BYTES];

        result[0] = (byte) (this.contentLength & 0xff);
        result[1] = (byte) (this.contentLength >> 8 & 0xff);
        result[2] = (byte) (this.contentLength >> 16 & 0xff);
        result[3] = this.sequenceId;
        return result;
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
