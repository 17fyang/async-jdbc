package com.stu.asyncJdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;

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
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        conn.createStatement().executeQuery("select * from lesson");
        System.out.println(conn);
        conn.close();
    }
}
