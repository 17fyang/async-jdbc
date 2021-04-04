package com.stu.asyncJdbc.jdbc;

import com.stu.asyncJdbc.common.auth.IAuthPlugin;
import com.stu.asyncJdbc.common.auth.SecureAuthentication;
import com.stu.asyncJdbc.common.exception.IllegalConnectException;
import com.stu.asyncJdbc.util.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 22:04
 * @Description:
 */
public class LoginBuilder {
    private String host = "127.0.0.1";
    private int port = 3306;
    private String user = "root";
    private String password = "123456";
    private String databaseName = "";
    private String authRandomCode = "";
    private int clientCapability = 0x003ea20f;
    //todo 当前仅支持mysql_native_password验证方式
    private final IAuthPlugin authPlugin = new SecureAuthentication();
    private final Map<String, String> connectAttribute = new HashMap<>();

    private LoginBuilder() {
    }

    /**
     * 使用建造者模式创建对象
     *
     * @return
     */
    public static LoginBuilder build() {

        return new LoginBuilder();
    }


    /**
     * 发起登录请求
     *
     * @return
     */
    public ClientConnectionPool login() {
        try {
            ClientStarter clientStarter = new ClientStarter(host, port);
            clientStarter.start();

            ClientConnectionPool connectionPool = new ClientConnectionPool(clientStarter);
            ClientChannelFactory<NioSocketChannel> channelFactory = clientStarter.getChannelFactory();
            for (Channel channel : channelFactory.getList()) connectionPool.addChannel(channel);
            
            return connectionPool;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public LoginBuilder withClientCapability(int clientCapability) {
        this.clientCapability = clientCapability;
        return this;
    }

    public LoginBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public LoginBuilder withHost(String host) {
        this.host = host;
        return this;
    }


    public LoginBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginBuilder withDatabase(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public LoginBuilder withServerRandomCode(String randomCode) {
        this.authRandomCode = randomCode;
        return this;
    }

    public LoginBuilder withAttribute(String key, String value) {
        if (StringUtil.isNull(key)) throw new IllegalConnectException();
        connectAttribute.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "LoginBuilder{" +
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
