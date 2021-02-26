package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.auth.IAuthPlugin;
import com.stu.asyncJdbc.common.auth.SecureAuthentication;
import com.stu.asyncJdbc.common.exception.IllegalConnectException;
import com.stu.asyncJdbc.util.MockUtil;
import com.stu.asyncJdbc.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 22:04
 * @Description:
 */
public class LoginConfigBuilder {
    private String user = "";
    private String password = "";
    private String authRandomCode = "";
    private String databaseName = "";
    private int clientCapability = MockUtil.mockClientCapability();
    //todo 当前仅支持mysql_native_password验证方式
    private IAuthPlugin authPlugin = new SecureAuthentication();
    private Map<String, String> connectAttribute = new HashMap<>();

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

    public LoginConfigBuilder withDatabase(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public LoginConfigBuilder withServerRandomCode(String randomCode) {
        this.authRandomCode = randomCode;
        return this;
    }

    public LoginConfigBuilder withAttribute(String key, String value) {
        if (StringUtil.isNull(key)) throw new IllegalConnectException();
        connectAttribute.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "LoginConfigBuilder{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", authRandomCode='" + authRandomCode + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", clientCapability=" + clientCapability +
                ", authPlugin=" + authPlugin +
                ", connectAttribute=" + connectAttribute +
                '}';
    }

    public Map<String, String> getConnectAttribute() {
        return connectAttribute;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getAuthRandomCode() {
        return authRandomCode;
    }

    public IAuthPlugin getAuthPlugin() {
        return authPlugin;
    }

    public String getUser() {
        return user;
    }


    public String getPassword() {
        return password;
    }


    public int getClientCapability() {
        return clientCapability;
    }
}
