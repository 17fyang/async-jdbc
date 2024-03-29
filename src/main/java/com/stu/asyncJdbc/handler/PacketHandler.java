package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.jdbc.ByteBufAdapter;
import com.stu.asyncJdbc.packet.Packet;
import com.stu.asyncJdbc.packet.ReadPacket;
import com.stu.asyncJdbc.packet.SendPacket;
import io.netty.channel.ChannelFuture;
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
    public P read(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
        try {
            P instance = clazz.newInstance();

            if (!(instance instanceof ReadPacket)) throw new RuntimeException("un readable type");

            ReadPacket readPacketInstance = (ReadPacket) instance;
            readPacketInstance.read(byteBufAdapter, channelContext);

            return instance;
        } catch (Exception e) {
            logger.error("fail to read packet, maybe ReadPacket should has empty constructor");
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
    public ChannelFuture write(P packet, ChannelContext channelContext) {
        try {
            if (!(packet instanceof SendPacket)) throw new RuntimeException("un writable type");
            
            SendPacket sendPacketInstance = (SendPacket) packet;
            ByteBufAdapter byteBufAdapter = sendPacketInstance.write(channelContext);
            return channelContext.channel().writeAndFlush(byteBufAdapter.getByteBuf());
        } catch (Exception e) {
            System.out.println(("fail to write packet " + packet));
            e.printStackTrace();
        }
        return null;
    }
}
