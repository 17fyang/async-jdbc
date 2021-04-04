package com.stu.asyncJdbc.common.exception;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 20:05
 * @Description:
 */
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
