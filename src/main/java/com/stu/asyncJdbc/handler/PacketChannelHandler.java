package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.net.ByteBufAdapter;
import com.stu.asyncJdbc.packet.HandshakeResponsePacket;
import com.stu.asyncJdbc.packet.LoginConfigBuilder;
import com.stu.asyncJdbc.packet.PacketContext;
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
    private volatile boolean isRead = false;
    private volatile boolean isWrite = false;

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

        if (!ChannelService.INSTANCE.hasChannel(ctx.channel())) {
            //读取server handshake包
            //todo 补上context内容
            ServerHelloPacket serverHelloPacket = PacketHandleFactory.SERVER_HELLO_HANDLER.read(byteBufAdapter, new PacketContext());
            ChannelContext channelContext = new ChannelContext();
            channelContext.flagReceiveServerHello(serverHelloPacket);
            ChannelService.INSTANCE.addContext(ctx.channel(), channelContext);
            System.out.println(serverHelloPacket);
        }


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

        ChannelContext channelContext = ChannelService.INSTANCE.getChannelContext(ctx.channel());
        if (channelContext == null) return;

        //server handshake包响应
        if (channelContext.getChannelStatus() == ChannelContext.STATUS_RECEIVE_HANDSHAKE_INIT) {
            ServerHelloPacket serverHelloPacket = channelContext.getServerHelloPacket();
            LoginConfigBuilder builder = LoginConfigBuilder.build().withUser("visitor")
                    .withDatabase("otsea").withPassword("123456")
                    .withServerRandomCode(serverHelloPacket.getAuthPluginPart1() + serverHelloPacket.getAuthPluginPart2());
            HandshakeResponsePacket responsePacket = new HandshakeResponsePacket(builder);
            ByteBufAdapter byteBufAdapter = PacketHandleFactory.HANDSHAKE_RESPONSE_PACKET_PACKET_HANDLER.write(responsePacket);
            ctx.writeAndFlush(byteBufAdapter.getByteBuf());
        }

    }

}
