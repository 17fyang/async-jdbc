package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.net.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 10:43
 * @Description: 客户端接收到服务端的握手请求包之后回复的握手包
 * packet版本为mysql4.1+客户端的版本
 * 4.1+客户端发送的握手响应包支持CLIENT_PROTOCOL_41能力，如果服务器在其初始握手包中宣布它。
 * 否则(与旧服务器通信)必须使用Protocol::HandshakeResponse320报文。
 */
public class HandshakeResponsePacket extends ReadPacket {
    @Override
    protected void readBody(ByteBufAdapter byteBufAdapter) {
        
    }

    @Override
    public void validate() {

    }
}
