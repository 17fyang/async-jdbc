package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.enumeration.CapabilityFlag;
import com.stu.asyncJdbc.common.enumeration.MysqlCharset;
import com.stu.asyncJdbc.net.ByteBufAdapter;
import com.stu.asyncJdbc.util.LenencUtil;
import com.stu.asyncJdbc.util.StringUtil;

import java.util.Arrays;
import java.util.Map;


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

    public HandshakeResponsePacket(LoginConfigBuilder loginConfigBuilder) {
        this.loginConfigBuilder = loginConfigBuilder;
    }

    @Override
    public void writeBody(ByteBufAdapter byteBufAdapter) {
        //client capability
        int clientCapability = loginConfigBuilder.getClientCapability();
        byteBufAdapter.writeInt4(clientCapability);

        //max packet size
        byteBufAdapter.writeInt4(maxPacketSize);

        //charset code
        byteBufAdapter.writeByte(MysqlCharset.UTF8_GENERAL_CI.getCode());

        //un use code
        byteBufAdapter.writeSameBytes((byte) 0, 23);

        //user name
        byteBufAdapter.writeStringNull(loginConfigBuilder.getUser());

        //auth-response
        String password = loginConfigBuilder.getPassword();
        String authRandomCode = loginConfigBuilder.getAuthRandomCode();
        byte[] verifiedCode = loginConfigBuilder.getAuthPlugin().verify(StringUtil.withAscii(password), StringUtil.withAscii(authRandomCode));

        System.out.println("verifyCode:" + Arrays.toString(password.getBytes()));
        System.out.println("verifyCode:" + Arrays.toString(authRandomCode.getBytes()));
        System.out.println("verifyCode:" + Arrays.toString(verifiedCode));

        if ((clientCapability & CapabilityFlag.CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA) != 0) {
            byte[] lenencLen = LenencUtil.ofInt(verifiedCode.length);
            byteBufAdapter.writeBytes(lenencLen);
            byteBufAdapter.writeBytes(verifiedCode);
        } else if ((clientCapability & CapabilityFlag.CLIENT_SECURE_CONNECTION) != 0) {
            byteBufAdapter.writeInt4(verifiedCode.length);
            byteBufAdapter.writeBytes(verifiedCode);
        } else {
            byteBufAdapter.writeStringNull(StringUtil.valueOf(verifiedCode));
        }

        //database name
        if ((clientCapability & CapabilityFlag.CLIENT_CONNECT_WITH_DB) != 0) {
            byteBufAdapter.writeStringNull(loginConfigBuilder.getDatabaseName());
        }

        //auth plugin name
        if ((clientCapability & CapabilityFlag.CLIENT_PLUGIN_AUTH) != 0) {
            byteBufAdapter.writeStringNull(loginConfigBuilder.getAuthPlugin().getPluginName());
        }

        //connect attribute
        if ((clientCapability & CapabilityFlag.CLIENT_CONNECT_ATTRS) != 0) {
            ByteBufAdapter attributeByte = new ByteBufAdapter();

            int attributeLength = 0;

            for (Map.Entry<String, String> entry : loginConfigBuilder.getConnectAttribute().entrySet()) {
                byte[] key = entry.getKey().getBytes();
                byte[] keyLen = LenencUtil.ofInt(key.length);
                byte[] value = entry.getValue().getBytes();
                byte[] valueLen = LenencUtil.ofInt(value.length);

                attributeByte.writeBytes(keyLen);
                attributeByte.writeBytes(key);
                attributeByte.writeBytes(valueLen);
                attributeByte.writeBytes(value);
                attributeLength += (keyLen.length + key.length + valueLen.length + value.length);
            }

            byteBufAdapter.writeBytes(LenencUtil.ofInt(attributeLength));
            byteBufAdapter.writeBytes(attributeByte.getAllBytes());
        }
    }

    @Override
    public String toString() {
        return "HandshakeResponsePacket{" +
                "maxPacketSize=" + maxPacketSize +
                ", charset=" + charset +
                ", loginConfigBuilder=" + loginConfigBuilder +
                '}';
    }
}
