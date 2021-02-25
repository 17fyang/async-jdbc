package com.stu.asyncJdbc.type;

import com.stu.asyncJdbc.common.CommonConfig;
import com.stu.asyncJdbc.common.exception.CodeConversionException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 19:10
 * @Description:
 */
public class StringNull {
    private String value;

    public StringNull(String value) {
        this.value = value;
    }

    public static StringNull valueOf(byte[] bytes) {
        try {
            String value = new String(bytes, 0, bytes.length, CommonConfig.DEFAULT_CHARSET_NAME);
            return new StringNull(value);
        } catch (UnsupportedEncodingException e) {
            throw new CodeConversionException();
        }
    }

    public static StringNull valueOf(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        int i = 0;
        for (byte buf : list) {
            bytes[i++] = buf;
        }
        return StringNull.valueOf(bytes);
    }

    public String get() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }
}
