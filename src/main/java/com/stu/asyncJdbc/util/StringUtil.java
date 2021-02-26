package com.stu.asyncJdbc.util;

import com.stu.asyncJdbc.common.CommonConfig;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/26 14:15
 * @Description:
 */
public class StringUtil {
    public static String valueOf(byte[] value) {
        try {
            return new String(value, 0, value.length, CommonConfig.DEFAULT_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

    public static String valueOf(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        int i = 0;
        for (byte buf : list) bytes[i++] = buf;
        return StringUtil.valueOf(bytes);
    }

    public static byte[] withAscii(String value) {
        return value.getBytes(CommonConfig.DEFAULT_CHARSET);
    }

    public static boolean isNull(String value) {
        return value == null || value.equals("");
    }

}
