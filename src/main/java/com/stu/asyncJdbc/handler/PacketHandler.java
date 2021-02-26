package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.net.ByteBufAdapter;
import com.stu.asyncJdbc.packet.Packet;
import com.stu.asyncJdbc.packet.ReadPacket;
import com.stu.asyncJdbc.packet.SendPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/24 15:12
 * @Description:
 */
public class PacketHandler<P extends Packet> {
    private static final Logger logger = LoggerFactory.getLogger(PacketHandler.class);
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

            if (!(instance instanceof ReadPacket)) throw new RuntimeException("un readable type");

            ReadPacket readPacketInstance = (ReadPacket) instance;
            readPacketInstance.read(byteBufAdapter);

            return instance;
        } catch (Exception e) {
            logger.error("fail to read packet ");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个packet的内容写入到byteBufAdapter
     *
     * @param packet
     * @return
     */
    public ByteBufAdapter write(P packet) {
        try {
            if (!(packet instanceof SendPacket)) throw new RuntimeException("un writable type");

            SendPacket sendPacketInstance = (SendPacket) packet;
            return sendPacketInstance.write();
        } catch (Exception e) {
            logger.error("fail to write packet " + packet);
            e.printStackTrace();
        }
        return null;
    }

}
