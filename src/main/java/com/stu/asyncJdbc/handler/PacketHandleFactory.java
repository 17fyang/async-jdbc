package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.packet.ServerHelloPacket;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/24 15:19
 * @Description: PacketHandler的工厂类
 */
public class PacketHandleFactory {
    public static final PacketHandler<ServerHelloPacket> SERVER_HELLO_HANDLER = new PacketHandler<>(ServerHelloPacket.class);
}
