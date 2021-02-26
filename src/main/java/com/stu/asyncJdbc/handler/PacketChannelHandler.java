package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.net.ByteBufAdapter;
import com.stu.asyncJdbc.packet.HandshakeResponsePacket;
import com.stu.asyncJdbc.packet.LoginConfigBuilder;
import com.stu.asyncJdbc.packet.ServerHelloPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/27 12:22
 * @Description:
 */
public class PacketChannelHandler extends ChannelInboundHandlerAdapter {
    private boolean isRead = false;
    private boolean isWrite = false;
    private ServerHelloPacket helloPacket;


    /**
     * 通道就绪
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channel has ready ...");
    }

    /**
     * 通道数据可读
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (isRead) return;
        isRead = true;

        ByteBuf byteBuf = (ByteBuf) msg;
        ByteBufAdapter byteBufAdapter = new ByteBufAdapter(byteBuf);
        ServerHelloPacket serverHelloPacket = PacketHandleFactory.SERVER_HELLO_HANDLER.read(byteBufAdapter);

        this.helloPacket = serverHelloPacket;
        System.out.println(serverHelloPacket);
    }

    /**
     * 通道数据读取完毕
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        if (isWrite) return;
        isWrite = true;

        LoginConfigBuilder builder = LoginConfigBuilder.build().withUser("visitor")
                .withDatabase("otsea").withPassword("123456").withServerRandomCode(this.helloPacket.getAuthPluginPart1() + this.helloPacket.getAuthPluginPart2());
        HandshakeResponsePacket responsePacket = new HandshakeResponsePacket(builder);

        System.out.println(responsePacket);

        ByteBufAdapter byteBufAdapter = PacketHandleFactory.HANDSHAKE_RESPONSE_PACKET_PACKET_HANDLER.write(responsePacket);
        ctx.writeAndFlush(byteBufAdapter.getByteBuf());
    }

}
