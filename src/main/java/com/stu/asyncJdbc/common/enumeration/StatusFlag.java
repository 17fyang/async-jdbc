package com.stu.asyncJdbc.common.enumeration;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/3/5 19:08
 * @Description:
 */
public class StatusFlag {
    public static final int SERVER_STATUS_IN_TRANS = 0x0001;
    public static final int SERVER_STATUS_AUTOCOMMIT = 0x0002; // Server in auto_commit mode
    public static final int SERVER_MORE_RESULTS_EXISTS = 0x0008; // Multi query - next query exists
    public static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 0x0010;
    public static final int SERVER_QUERY_NO_INDEX_USED = 0x0020;
    public static final int SERVER_STATUS_CURSOR_EXISTS = 0x0040;
    public static final int SERVER_STATUS_LAST_ROW_SENT = 0x0080; // The server status for 'last-row-sent'
    public static final int SERVER_STATUS_DB_DROPPED = 0x0100;
    public static final int SERVER_STATUS_NO_BACKSLASH_ESCAPES = 0x0200;
    public static final int SERVER_STATUS_METADATA_CHANGED = 0x0400;
    public static final int SERVER_QUERY_WAS_SLOW = 0x0800;
    public static final int SERVER_PS_OUT_PARAMS = 0x1000;
    public static final int SERVER_STATUS_IN_TRANS_READONLY = 0x2000;
    public static final int SERVER_SESSION_STATE_CHANGED = 0x4000;
}
