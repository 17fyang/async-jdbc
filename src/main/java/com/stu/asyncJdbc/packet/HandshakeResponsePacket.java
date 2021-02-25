package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.enumeration.MysqlCharset;
import com.stu.asyncJdbc.net.ByteBufAdapter;


/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/2/25 10:43
 * @Description: 客户端接收到服务端的握手请求包之后回复的握手包
 * packet版本为mysql4.1+客户端的版本
 * 4.1+客户端发送的握手响应包支持CLIENT_PROTOCOL_41能力，如果服务器在其初始握手包中宣布它。
 * 否则(与旧服务器通信)必须使用Protocol::HandshakeResponse320报文。
 */
public class HandshakeResponsePacket extends SendPacket {
    public static final int DEFAULT_MAX_PACKET_SIZE = 256 * 256 * 256 - 1;
    private int maxPacketSize = DEFAULT_MAX_PACKET_SIZE;
    private byte charset = MysqlCharset.UTF8_GENERAL_CI.getCode();
    private LoginConfigBuilder loginConfigBuilder;

    @Override
    public void writeBody(ByteBufAdapter byteBufAdapter) {
        //client capability
        int clientCapability = loginConfigBuilder.getClientCapability();
        byteBufAdapter.writeInt4(clientCapability);

        //max packet size
        byteBufAdapter.writeInt4(maxPacketSize);

        //unUse code
        byteBufAdapter.writeSameBytes((byte) 0, 23);

        //user name
        byteBufAdapter.writeStringNull(loginConfigBuilder.getUser());

        
    }


}
