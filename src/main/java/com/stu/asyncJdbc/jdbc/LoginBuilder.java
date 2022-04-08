package com.stu.asyncJdbc.jdbc;

import com.stu.asyncJdbc.common.auth.IAuthPlugin;
import com.stu.asyncJdbc.common.auth.SecureAuthentication;
import com.stu.asyncJdbc.common.exception.IllegalConnectException;
import com.stu.asyncJdbc.util.StringUtil;

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

    //workerGroup线程数量
    private int threadNum = 1;

    //mysql连接个数
    private int channelNum = 3;

    //其余参数
    private final Map<String, String> connectAttribute = new HashMap<>();

    //客户端capability
    private int clientCapability = 0x003ea20f;

    //todo 校验插件，当前仅支持mysql_native_password验证方式
    private final IAuthPlugin authPlugin = new SecureAuthentication();

    //该次登录的连接池对象
    private ClientConnectionPool connectionPool;

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

            ClientConnectionPool connectionPool = new ClientConnectionPool(clientStarter);
            this.connectionPool = connectionPool;

            clientStarter.start(this);

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


    public LoginBuilder withThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public LoginBuilder withChannelNum(int channelNum) {
        this.channelNum = channelNum;
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

    public LoginBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public LoginBuilder withAttribute(String key, String value) {
        if (StringUtil.isNull(key)) throw new IllegalConnectException();
        connectAttribute.put(key, value);
        return this;
    }

    public Map<String, String> getConnectAttribute() {
        return connectAttribute;
    }

    public String getDatabaseName() {
        return databaseName;
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

    public int getThreadNum() {
        return threadNum;
    }

    public int getChannelNum() {
        return channelNum;
    }

    public ClientConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public int getClientCapability() {
        return clientCapability;
    }
}
