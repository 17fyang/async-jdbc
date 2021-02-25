package com.stu.asyncJdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/24 14:28
 * @Description:
 */
public class DemoTest {
    public static final String DRIVER = "com.mysql.cj.jdbc";
    public static final String USER = "root";
    public static final int PORT = 3306;
    public static final String URL = "jdbc:mysql://localhost:3306/otsea";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection(URL);
    }
}
