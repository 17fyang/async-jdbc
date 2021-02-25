package com.stu.asyncJdbc.util;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 19:19
 * @Description:mysql协议使用小端格式传输，jvm使用大端格式，所以需要转换
 */
public class EndianUtil {

    /**
     * 切换大小端
     *
     * @param value
     * @return
     */
    public static void switchEndian(byte[] value) {
        byte temp;
        for (int i = 0; i < value.length / 2; i++) {
            temp = value[value.length - i - 1];
            value[value.length - i - 1] = value[i];
            value[i] = temp;
        }
    }
}
