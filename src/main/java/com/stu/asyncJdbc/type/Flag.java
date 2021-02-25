package com.stu.asyncJdbc.type;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 16:01
 * @Description:
 */
public class Flag {
    public static final byte FLAG_ONE = 0b00000001;
    public static final byte FLAG_TWO = 0b00000010;
    public static final byte FLAG_THREE = 0b00000100;
    public static final byte FLAG_FOUR = 0b00001000;
    public static final byte FLAG_FIVE = 0b00010000;
    public static final byte FLAG_SIX = 0b00100000;
    public static final byte FLAG_SEVEN = 0b01000000;
    public static final byte FLAG_EIGHT = (byte) 0b10000000;

    protected boolean judge(byte flag, int location) {
        if (location == 1) return (flag & FLAG_ONE) == FLAG_ONE;
        if (location == 2) return (flag & FLAG_TWO) == FLAG_TWO;
        if (location == 3) return (flag & FLAG_THREE) == FLAG_THREE;
        if (location == 4) return (flag & FLAG_FOUR) == FLAG_FOUR;
        if (location == 5) return (flag & FLAG_FIVE) == FLAG_FIVE;
        if (location == 6) return (flag & FLAG_SIX) == FLAG_SIX;
        if (location == 7) return (flag & FLAG_SEVEN) == FLAG_SEVEN;
        if (location == 8) return (flag & FLAG_EIGHT) == FLAG_EIGHT;
        return false;
    }
}
