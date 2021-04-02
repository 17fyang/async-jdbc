package com.stu.asyncJdbc.common.exception;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/3/5 23:36
 * @Description:
 */
public class UnsupportVersionException extends RuntimeException {
    public UnsupportVersionException() {
        super("unSupport version packet, may be update your mysql client");
    }
}
