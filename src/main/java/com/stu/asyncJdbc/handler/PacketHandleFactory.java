package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.common.enumeration.PacketType;
import com.stu.asyncJdbc.common.exception.PacketAnalysisException;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;
import com.stu.asyncJdbc.packet.*;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/24 15:19
 * @Description: PacketHandler的工厂类
 */
public class PacketHandleFactory {
    public static final PacketHandler<ServerHelloPacket> SERVER_HELLO_HANDLER = new PacketHandler<>(ServerHelloPacket.class);
    public static final PacketHandler<HandshakeResponsePacket> HANDSHAKE_RESPONSE_PACKET_PACKET_HANDLER = new PacketHandler<>(HandshakeResponsePacket.class);
    public static final TypicalReadPacketHandler TYPICAL_PACKET_HANDLER = new TypicalReadPacketHandler(EmptyTypicalReadPacket.class);
    public static final PacketHandler<OkPacket> OK_PACKET = new PacketHandler<>(OkPacket.class);
    public static final PacketHandler<QueryPacket> QUERY_PACKET = new PacketHandler<>(QueryPacket.class);
    public static final PacketHandler<EmptyTypicalReadPacket> EMPTY_HANDLER = new PacketHandler<>(EmptyTypicalReadPacket.class);

    static class TypicalReadPacketHandler extends PacketHandler<EmptyTypicalReadPacket> {

        public TypicalReadPacketHandler(Class<EmptyTypicalReadPacket> clazz) {
            super(clazz);
        }

        @Override
        public EmptyTypicalReadPacket read(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
            return readTypical(byteBufAdapter, channelContext);
        }

        public <P> P readTypical(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
            try {
                EmptyTypicalReadPacket empty = super.read(byteBufAdapter, channelContext);
                if (empty == null) return null;
                Class<? extends TypicalReadPacket> clazz = PacketType.get(empty.getType());
                if (clazz == null)
                    throw new PacketAnalysisException("not support packet type");
                TypicalReadPacket instance = clazz.newInstance();
                instance.readPacketBody(byteBufAdapter, channelContext);
                return (P) instance;
            } catch (ReflectiveOperationException e) {
                throw new PacketAnalysisException();
            }
        }

    }

    static class EmptyTypicalReadPacket extends TypicalReadPacket {

        @Override
        public void readPacketBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {

        }
    }

}
