package com.stu.asyncJdbc.net;

import com.stu.asyncJdbc.common.CommonConfig;
import com.stu.asyncJdbc.common.enumeration.ByteEnum;
import com.stu.asyncJdbc.common.exception.CodeConversionException;
import com.stu.asyncJdbc.util.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 18:06
 * @Description: mysql传输协议用的是小端传输
 */
public class ByteBufAdapter {
    //初始ByteBuf容量
    public static final int INIT_CAPACITY = 128;
    private ByteBuf byteBuf;


    public ByteBufAdapter() {
        this.byteBuf = Unpooled.buffer(INIT_CAPACITY);
    }

    public ByteBufAdapter(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public boolean hasNext() {
        return byteBuf.isReadable();
    }

    /**
     * 写入一个byte
     *
     * @param value
     * @return
     */
    public ByteBufAdapter writeByte(int value) {
        byteBuf.writeByte(value);
        return this;
    }

    /**
     * 写入一个int
     *
     * @param value
     * @return
     */
    public void writeInt4(int value) {
        byteBuf.writeIntLE(value);
    }

    public void writeSameBytes(byte value, int num) {
        byte[] buf = new byte[num];
        Arrays.fill(buf, value);
        byteBuf.writeBytes(buf);
    }

    public void writeBytes(byte[] value) {
        byteBuf.writeBytes(value);
    }

    public void writeString(String value) {
        byteBuf.writeBytes(value.getBytes());
    }

    public void writeStringNull(String value) {
        byteBuf.writeBytes(value.getBytes());
        byteBuf.writeByte(ByteEnum.FILLER);
    }


    /**
     * 读取一个byte
     *
     * @return
     */
    public byte readByte() {
        return byteBuf.readByte();
    }

    /**
     * 读取int2类型（小端）
     *
     * @return
     */
    public int readInt2() {
        return ((byteBuf.readByte() & 0xff) | (byteBuf.readByte() & 0xff) << 8);
    }

    /**
     * 读取int4类型（小端）
     *
     * @return
     */
    public int readInt4() {
        return byteBuf.readIntLE();
    }

    public byte[] readBytes(int length) {
        byte[] dst = new byte[length];
        byteBuf.readBytes(dst);
        return dst;
    }

    public String readStringNull() {
        List<Byte> list = new LinkedList<>();
        byte temp;
        while ((temp = byteBuf.readByte()) != ByteEnum.FILLER) {
            list.add(temp);
        }
        return StringUtil.valueOf(list);
    }

    public String readString(int length) {
        return this.readString(length, CommonConfig.DEFAULT_CHARSET_NAME);
    }

    public String readString(int length, String charSetName) {
        byte[] value = this.readBytes(length);
        try {
            return new String(value, 0, value.length, charSetName);
        } catch (UnsupportedEncodingException e) {
            throw new CodeConversionException();
        }
    }

    public byte[] getAllBytes() {
        byte[] buf = new byte[this.byteBuf.readableBytes()];
        int readerIndex = this.byteBuf.readerIndex();
        this.byteBuf.getBytes(readerIndex, buf);
        return buf;
    }

    public ByteBuf getByteBuf() {
        return this.byteBuf;
    }


}
