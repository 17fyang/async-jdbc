package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.net.ByteBufAdapter;
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
        ByteBuf byteBuf = (ByteBuf) msg;
        ByteBufAdapter byteBufAdapter = new ByteBufAdapter(byteBuf);
        ServerHelloPacket serverHelloPacket = PacketHandleFactory.SERVER_HELLO_HANDLER.read(byteBufAdapter);
        System.out.println(serverHelloPacket);
    }

    /**
     * 通道数据读取完毕
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("complete read ...");
    }

}
