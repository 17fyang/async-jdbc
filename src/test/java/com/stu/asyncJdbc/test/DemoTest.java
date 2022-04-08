package com.stu.asyncJdbc.test;

import com.stu.asyncJdbc.jdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/24 14:28
 * @Description:
 */
public class DemoTest {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER = "visitor";
    public static final String PASSWORD = "123456";
    public static final int PORT = 3306;
    public static final String URL = "jdbc:mysql://120.79.175.145:3306/otsea";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        System.out.println("tradition:" + traditionTest());

        tradition();

//        asyncTest();
    }


    private static long asyncTest() {
        System.out.println("async before:" + System.currentTimeMillis());

        ClientConnectionPool connectionPool = LoginBuilder.build().withHost("120.79.175.145").withUser("visitor")
                .withDatabase("otsea").withPassword("123456").withThreadNum(3).withChannelNum(6).login();

        AtomicInteger value = new AtomicInteger(0);
        long before = System.currentTimeMillis();
        for (int i = 0; i < 6; i++) {
            AsyncStatement stat = connectionPool.createAsyncStatement();

            stat.execute("select * from lesson", new Task(value, stat));
        }
        return before;
    }

    private static long traditionTest() throws Exception {
        long before = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            tradition();
        }
        long after = System.currentTimeMillis();

        return after - before;
    }


    private static void tradition() throws Exception {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stat = conn.createStatement();
        stat.executeQuery("select * from lesson_collect");
        conn.close();
    }


    static class Task implements IExecuteResult {
        private AtomicInteger value;
        private AsyncStatement statement;

        public Task(AtomicInteger value, AsyncStatement statement) {
            this.value = value;
            this.statement = statement;
        }

        @Override
        public void handle(ExecuteContext ctx, AsyncResult res) {
            int now = value.incrementAndGet();
            System.out.println(now);
            if (now < 100) {
                statement.execute("select * from lesson", this);
            } else {
                System.out.println("after:" + System.currentTimeMillis());
            }
        }
    }
}
