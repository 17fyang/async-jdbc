package com.stu.asyncJdbc.jdbc;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/3 22:28
 * @Description:
 */
public interface IExecuteResult {
    void handle(ExecuteContext ctx, AsyncResult res);
}
