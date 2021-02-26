package com.stu.asyncJdbc.common.auth;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 22:56
 * @Description:
 */
public interface IAuthPlugin {
    /**
     * 生成password的密码序列
     *
     * @param password
     * @param randomCode
     * @return
     */
    byte[] verify(byte[] password, byte[] randomCode);

    /**
     * 返回验证插件名字
     *
     * @return
     */
    String getPluginName();
}
