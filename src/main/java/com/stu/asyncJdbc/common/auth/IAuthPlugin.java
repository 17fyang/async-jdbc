package com.stu.asyncJdbc.common.auth;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 22:56
 * @Description:
 */
public interface IAuthPlugin {
    byte[] verify(byte[] password, byte[] randomCode);
}
