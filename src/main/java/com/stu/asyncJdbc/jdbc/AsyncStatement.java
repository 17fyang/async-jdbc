package com.stu.asyncJdbc.jdbc;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 19:10
 * @Description:
 */
public class AsyncStatement {
    private ClientConnectionPool clientConnectionPool;

    public AsyncStatement(ClientConnectionPool clientConnectionPool) {
        this.clientConnectionPool = clientConnectionPool;
    }

    public void execute(String sql, IExecuteResult executeResult) {
        
    }

    public void close() {

    }
}
