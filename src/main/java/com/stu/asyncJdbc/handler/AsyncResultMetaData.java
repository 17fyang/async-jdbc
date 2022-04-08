package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.packet.TypicalReadPacket;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/16 20:38
 * @Description:
 */
public class AsyncResultMetaData {
    public static final int INIT = 0;
    public static final int GET_TYPE_NUM = 1;
    public static final int GET_TYPES = 2;
    public static final int READY_TO_READ = 3;

    private int status = INIT;

    public void dealTypicalPacket(TypicalReadPacket typicalReadPacket) {
        
    }

}
