package com.stu.asyncJdbc.common.enumeration;

import com.stu.asyncJdbc.packet.OkPacket;
import com.stu.asyncJdbc.packet.TypicalReadPacket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:04
 * @Description:
 */
public class PacketType {
    private static final Map<Byte, Class<? extends TypicalReadPacket>> map = new ConcurrentHashMap<>();
    public static final byte OK_PACKET_1 = 0x00;
    public static final byte OK_PACKET_2 = (byte) 0xfe;


    static {
        bind(OK_PACKET_1, OkPacket.class);
    }

    public static void bind(byte packetId, Class<? extends TypicalReadPacket> clazz) {
        map.put(packetId, clazz);
    }

    public static Class<? extends TypicalReadPacket> get(byte packedId) {
        return map.get(packedId);
    }
}
