package com.stu.asyncJdbc.common.exception;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:15
 * @Description:
 */
public class ChannelStatusException extends RuntimeException {
    public ChannelStatusException() {
        super("not correct channel status");
    }
}
