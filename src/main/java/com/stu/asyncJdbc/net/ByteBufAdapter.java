package com.stu.asyncJdbc.net;

import com.stu.asyncJdbc.common.CommonConfig;
import com.stu.asyncJdbc.common.enumeration.ByteEnum;
import com.stu.asyncJdbc.common.exception.CodeConversionException;
import com.stu.asyncJdbc.type.StringNull;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 18:06
 * @Description: mysql传输协议用的是小端传输
 */
public class ByteBufAdapter {
    private ByteBuf byteBuf;

    public ByteBufAdapter(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public boolean hasNext() {
        return byteBuf.isReadable();
    }

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

    public StringNull readStringNull() {
        List<Byte> list = new LinkedList<>();
        byte temp;
        while ((temp = byteBuf.readByte()) != ByteEnum.FILLER) {
            list.add(temp);
        }
        return StringNull.valueOf(list);
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

}
