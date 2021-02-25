package com.stu.asyncJdbc.common.enumeration;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 15:52
 * @Description: mysql码表映射
 */
public enum MysqlCharset {
    LATIN1((byte) 0x08, "latin1_swedish_ci");

    private byte code;
    private String charSetName;

    MysqlCharset(byte code, String charSetName) {
        this.code = code;
        this.charSetName = charSetName;
    }

    /**
     * 返回枚举对象，找不到返回空
     *
     * @param code
     * @return
     */
    public static MysqlCharset valueOf(byte code) {
        for (MysqlCharset charset : MysqlCharset.values()) {
            if (code == charset.getCode()) return charset;
        }
        return null;
    }

    /**
     * 是否支持该码表
     *
     * @param code
     * @return
     */
    public boolean isSupports(byte code) {
        for (MysqlCharset charset : MysqlCharset.values()) {
            if (code == charset.getCode()) return true;
        }
        return false;
    }


    public byte getCode() {
        return code;
    }

    public String getCharSetName() {
        return charSetName;
    }

    @Override
    public String toString() {
        return this.charSetName;
    }
}
