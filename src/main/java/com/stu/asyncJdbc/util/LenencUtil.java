package com.stu.asyncJdbc.util;

import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/26 14:39
 * @Description:
 */
public class LenencUtil {
    public static byte[] ofInt(int value) {
        if (value < 251) {
            return new byte[]{(byte) value};
        } else if (value < 1 << 16) {
            return new byte[]{(byte) 0xfc, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff)};
        } else if (value < 1 << 24) {
            return new byte[]{(byte) 0xfd, (byte) (value & 0xff), (byte) ((value >> 8) & 0xff), (byte) ((value >> 16) & 0xff)};
        }
        //todo 不支持过长的lenenc 数据
        throw new RuntimeException("lenenc value is too long!");
    }

    public static int read(ByteBufAdapter byteBufAdapter) {
        int b = 0xff & byteBufAdapter.readByte();
        if (b < 0xfb) {
            return b;
        } else if (b == 0xfc) {
            return byteBufAdapter.readInt2();
        }
        //todo 不支持过长的lenenc 数据
        throw new RuntimeException("lenenc value is too long!");
    }

}
