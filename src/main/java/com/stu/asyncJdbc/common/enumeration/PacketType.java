package com.stu.asyncJdbc.common.enumeration;

import com.stu.asyncJdbc.packet.EofPacket;
import com.stu.asyncJdbc.packet.ErrPacket;
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
    public static final byte OK_PACKET = (byte) 0x00;
    public static final byte EOF_PACKET = (byte) 0xfe;
    public static final byte ERR_PACKET = (byte) 0xff;


    static {
        bind(OK_PACKET, OkPacket.class);
        bind(EOF_PACKET, EofPacket.class);
        bind(ERR_PACKET, ErrPacket.class);
    }

    public static void bind(byte packetId, Class<? extends TypicalReadPacket> clazz) {
        map.put(packetId, clazz);
    }

    public static Class<? extends TypicalReadPacket> get(byte packedId) {
        return map.get(packedId);
    }
}
