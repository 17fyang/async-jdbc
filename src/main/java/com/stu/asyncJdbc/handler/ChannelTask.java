package com.stu.asyncJdbc.handler;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/5/4 11:52
 * @Description:
 */
public class ChannelTask {
    private boolean hasResultSet = false;

    public boolean isHasResultSet() {
        return hasResultSet;
    }

    public void setHasResultSet(boolean hasResultSet) {
        this.hasResultSet = hasResultSet;
    }
}
