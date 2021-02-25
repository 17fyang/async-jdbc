package com.stu.asyncJdbc.common.enumeration;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 16:00
 * @Description:
 */
public class ServerStatus {
    public static final int SERVER_STATUS_IN_TRANS = 1;
    public static final int SERVER_STATUS_AUTOCOMMIT = 2; // 服务器的auto-commit状态;
    public static final int SERVER_MORE_RESULTS_EXISTS = 8; // Multi query - next query exists
    public static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
    public static final int SERVER_QUERY_NO_INDEX_USED = 32;
    public static final int SERVER_STATUS_CURSOR_EXISTS = 64;
    public static final int SERVER_STATUS_LAST_ROW_SENT = 128; // The server status for 'last-row-sent'
    public static final int SERVER_QUERY_WAS_SLOW = 2048;
}
