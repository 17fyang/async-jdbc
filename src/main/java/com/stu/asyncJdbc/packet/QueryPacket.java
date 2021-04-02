package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.enumeration.ByteEnum;
import com.stu.asyncJdbc.net.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/3/5 18:21
 * @Description:
 */
public class QueryPacket extends SendPacket {
    private String sql = "";

    public QueryPacket(String sql) {
        this.sql = sql;
    }

    @Override
    public void writeBody(ByteBufAdapter byteBufAdapter) {
        byteBufAdapter.writeByte(ByteEnum.COM_QUERY);
        byteBufAdapter.writeString(this.sql);
    }
}
