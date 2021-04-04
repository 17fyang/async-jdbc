package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.jdbc.ByteBufAdapter;
import com.stu.asyncJdbc.jdbc.LoginBuilder;
import com.stu.asyncJdbc.packet.HandshakeResponsePacket;
import com.stu.asyncJdbc.packet.OkPacket;
import com.stu.asyncJdbc.packet.ServerHelloPacket;
import com.stu.asyncJdbc.packet.TypicalReadPacket;
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ChannelContext channelContext = new ChannelContext();
        ChannelService.INSTANCE.addContext(ctx.channel(), channelContext);

        System.out.println("channel has ready ...");
    }

    /**
     * 通道关闭
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        ChannelService.INSTANCE.removeChannel(ctx.channel());
        System.out.println("close channel..");
    }

    /**
     * 通道数据可读
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.channel());
        System.out.println(Thread.currentThread().getName());
        ByteBuf byteBuf = (ByteBuf) msg;
        ByteBufAdapter byteBufAdapter = new ByteBufAdapter(byteBuf);
        ChannelContext channelContext = ChannelService.INSTANCE.getChannelContext(ctx.channel());
        if (channelContext == null) return;

        if (channelContext.getChannelStatus() == ChannelContext.TCP_CONNECTED) {
            ServerHelloPacket serverHelloPacket = PacketHandleFactory.SERVER_HELLO_HANDLER.read(byteBufAdapter, channelContext);

            //修改channel状态
            channelContext.flagReceiveServerHello(serverHelloPacket);

            System.out.println(serverHelloPacket);
        } else if (channelContext.getChannelStatus() == ChannelContext.RESP_HANDSHAKE) {
            TypicalReadPacket typicalPacket = PacketHandleFactory.TYPICAL_PACKET_HANDLER.readTypical(byteBufAdapter, channelContext);

            //修改channel状态
            if (typicalPacket instanceof OkPacket) channelContext.flagMysqlConnected();

            System.out.println(typicalPacket);
        }

    }

    /**
     * 通道数据读取完毕
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ChannelContext channelContext = ChannelService.INSTANCE.getChannelContext(ctx.channel());
        if (channelContext == null) return;

        //server handshake包响应
        if (channelContext.getChannelStatus() == ChannelContext.RECEIVE_HANDSHAKE_INIT) {
            ServerHelloPacket serverHelloPacket = channelContext.getServerHelloPacket();
            LoginBuilder builder = LoginBuilder.build().withUser("visitor")
                    .withDatabase("otsea").withPassword("123456")
                    .withServerRandomCode(serverHelloPacket.getAuthPluginPart1() + serverHelloPacket.getAuthPluginPart2());
            HandshakeResponsePacket responsePacket = new HandshakeResponsePacket(builder);

            //写入到adapter
            ByteBufAdapter byteBufAdapter = PacketHandleFactory.HANDSHAKE_RESPONSE_PACKET_PACKET_HANDLER.write(responsePacket);

            //修改channel状态
            channelContext.flagHasResponseHandshake();

            //发送tcp包
            ctx.writeAndFlush(byteBufAdapter.getByteBuf());
        }

    }

}
