package com.stu.asyncJdbc;

import com.stu.asyncJdbc.jdbc.AsyncStatement;
import com.stu.asyncJdbc.jdbc.ClientConnectionPool;
import com.stu.asyncJdbc.jdbc.LoginBuilder;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 20:30
 * @Description:
 */
public class Application {

    public static void main(String[] args) {
        ClientConnectionPool connectionPool = LoginBuilder.build().withHost("120.79.175.145").withUser("visitor")
                .withDatabase("otsea").withPassword("123456").login();

        AsyncStatement stat = connectionPool.createAsyncStatement();

        stat.execute("select * from user", (statement, result) -> {
            System.out.println("Get the result successful");
        });
    }
}
