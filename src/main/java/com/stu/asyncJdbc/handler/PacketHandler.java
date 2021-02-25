package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.net.ByteBufAdapter;
import com.stu.asyncJdbc.packet.ReadPacket;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/24 15:12
 * @Description:
 */
public class PacketHandler<P extends ReadPacket> {
    Class<P> clazz;

    public PacketHandler(Class<P> clazz) {
        this.clazz = clazz;
    }

    /**
     * 从byteBuf中解析内容到packet中
     *
     * @param byteBufAdapter
     * @return
     */
    public P read(ByteBufAdapter byteBufAdapter) {
        try {

            P instance = clazz.newInstance();

            instance.read(byteBufAdapter);

            return instance;

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("fail to read class data with " + clazz.getName());
        }
    }

}
