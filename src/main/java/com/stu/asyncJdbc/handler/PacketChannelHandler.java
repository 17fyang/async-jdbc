package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.jdbc.ByteBufAdapter;
import com.stu.asyncJdbc.jdbc.ClientConnectionPool;
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
    private final LoginBuilder loginBuilder;
    private final ClientConnectionPool connectionPool;
    private final ChannelContext channelContext;

    public PacketChannelHandler(LoginBuilder loginBuilder, ClientConnectionPool connectionPool, ChannelContext channelCtx) {
        this.loginBuilder = loginBuilder;
        this.connectionPool = connectionPool;
        this.channelContext = channelCtx;
    }

    /**
     * 通道就绪
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

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
    }

    /**
     * 通道数据可读
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBufAdapter byteBufAdapter = new ByteBufAdapter((ByteBuf) msg);

        if (channelContext.getChannelStatus() == ChannelContext.TCP_CONNECTED) {
            ServerHelloPacket serverHelloPacket = PacketHandleFactory.SERVER_HELLO_HANDLER.read(byteBufAdapter, channelContext);

            //修改channel状态
            channelContext.flagReceiveServerHello(serverHelloPacket);
        } else if (channelContext.getChannelStatus() == ChannelContext.RESP_HANDSHAKE) {
            TypicalReadPacket typicalPacket = PacketHandleFactory.TYPICAL_PACKET_HANDLER.readTypical(byteBufAdapter, channelContext);

            //建立连接成功
            if (typicalPacket instanceof OkPacket) {
                channelContext.flagMysqlConnected();
                connectionPool.addChannel(channelContext);
            } else {
                System.out.println("建立连接失败");
            }
        } else if (channelContext.getChannelStatus() == ChannelContext.WAIT_RESP) {
            //todo 这里读取方式和其他的不一样，包内容没有标明包类型
            TypicalReadPacket typicalPacket = PacketHandleFactory.TYPICAL_PACKET_HANDLER.readTypical(byteBufAdapter, channelContext);

            //todo 默认执行语句成功
            channelContext.flagMysqlConnected();
        }
        channelContext.sequenceAdd();
    }

    /**
     * 通道数据读取完毕
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //唤醒一个写入线程
        synchronized (ctx.channel()) {
            ctx.channel().notify();
        }

        //server handshake包响应
        if (channelContext.getChannelStatus() == ChannelContext.RECEIVE_HANDSHAKE_INIT) {
            ServerHelloPacket serverHelloPacket = channelContext.getServerHelloPacket();
            String authPluginCode = serverHelloPacket.getAuthPluginPart1() + serverHelloPacket.getAuthPluginPart2();

            HandshakeResponsePacket responsePacket = new HandshakeResponsePacket(loginBuilder, authPluginCode);

            //发送数据包
            PacketHandleFactory.HANDSHAKE_RESPONSE_PACKET_PACKET_HANDLER.write(responsePacket, channelContext);

            channelContext.flagHasResponseHandshake();
            channelContext.sequenceAdd();
        }
    }

}
