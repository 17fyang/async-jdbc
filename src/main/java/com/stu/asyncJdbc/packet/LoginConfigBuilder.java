package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.util.MockUtil;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 22:04
 * @Description:
 */
public class LoginConfigBuilder {
    private String user = "";
    private String password = "";
    private String authVerifiedCode = "";
    private String url = "";
    private int clientCapability = MockUtil.mockClientCapability();

    private LoginConfigBuilder() {
    }

    /**
     * 使用建造者模式创建对象
     *
     * @return
     */
    public static LoginConfigBuilder build() {
        return new LoginConfigBuilder();
    }

    public LoginConfigBuilder withClientCapability(int clientCapability) {
        this.clientCapability = clientCapability;
        return this;
    }

    public LoginConfigBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public LoginConfigBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginConfigBuilder withUrl(String url) {
        this.url = url;
        return this;
    }


    public String getUser() {
        return user;
    }


    public String getPassword() {
        return password;
    }


    public String getUrl() {
        return url;
    }

    public int getClientCapability() {
        return clientCapability;
    }
}
