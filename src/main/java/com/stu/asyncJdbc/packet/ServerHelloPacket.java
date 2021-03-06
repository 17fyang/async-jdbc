package com.stu.asyncJdbc.packet;

import com.stu.asyncJdbc.common.enumeration.CapabilityFlag;
import com.stu.asyncJdbc.common.enumeration.MysqlCharset;
import com.stu.asyncJdbc.common.exception.PacketAnalysisException;
import com.stu.asyncJdbc.common.exception.UnsupportVersionException;
import com.stu.asyncJdbc.handler.ChannelContext;
import com.stu.asyncJdbc.jdbc.ByteBufAdapter;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/1/25 18:30
 * @Description:
 */
public class ServerHelloPacket extends ReadPacket {
    public static final int SUPPORT_PROTO_VERSION = 10;
    private byte protoVersion;
    private String serverVersion;
    private int connectId;
    private String authPluginPart1;
    private int serverCapability;
    private MysqlCharset mysqlCharset;
    private int serverStatus;
    private byte lengthOfPluginData;
    private String authPluginPart2;
    private String authPluginName;

    @Override
    public void readBody(ByteBufAdapter byteBufAdapter, ChannelContext channelContext) {
        // 协议版本号
        this.protoVersion = byteBufAdapter.readByte();

        //服务器版本号
        this.serverVersion = byteBufAdapter.readStringNull();

        //mysql连接id
        this.connectId = byteBufAdapter.readInt4();

        //第一部分authPlugin字符串
        this.authPluginPart1 = byteBufAdapter.readString(8);

        //一个filler填充位
        byteBufAdapter.readByte();

        //第一部分的serverCapability标志位
        int tempFlag = byteBufAdapter.readInt2();
        if (!byteBufAdapter.hasNext()) return;

        //使用的编码码表
        this.mysqlCharset = MysqlCharset.valueOf(byteBufAdapter.readByte());

        //serverStatus标志位
        this.serverStatus = byteBufAdapter.readInt2();

        //第二部分的serverCapability标志位
        this.serverCapability = byteBufAdapter.readInt2() << 16 | tempFlag;

        //authPlugin的总长度（如果有）
        byte temp = byteBufAdapter.readByte();
        if ((this.serverCapability & CapabilityFlag.CLIENT_PLUGIN_AUTH) != 0) lengthOfPluginData = temp;

        //十个保留的byte
        byteBufAdapter.readBytes(10);

        //第一部分authPlugin字符串
        if ((this.serverCapability & CapabilityFlag.CLIENT_SECURE_CONNECTION) != 0) {
            this.authPluginPart2 = byteBufAdapter.readStringNull();

            //以下这种读取方式是协议中写的，但是实测mysql_native_password插件的报文数据长度和这个不一致，所以改成了上面的方法
//            int len = Math.max(13, this.lengthOfPluginData - 8);
//            this.authPluginPart2 = byteBufAdapter.readString(len);
        }

        //使用的验证插件名字
        if ((this.serverCapability & CapabilityFlag.CLIENT_PLUGIN_AUTH) != 0) {
            this.authPluginName = byteBufAdapter.readStringNull();
        }

    }


    @Override
    public void validate() {
        //校验支持解析的mysql协议版本号
        if (this.protoVersion != SUPPORT_PROTO_VERSION)
            throw new UnsupportVersionException();

        //仅支持4.1的协议
        if ((this.serverCapability & CapabilityFlag.CLIENT_PROTOCOL_41) == 0)
            throw new UnsupportVersionException();

        //校验是否支持该编码
        if (this.mysqlCharset == null)
            throw new PacketAnalysisException("no supports charset ");
    }

    @Override
    public String toString() {
        return "ServerHelloPacket{" +
                "protoVersion=" + protoVersion +
                ", serverVersion=" + serverVersion +
                ", connectId=" + connectId +
                ", authPluginPart1='" + authPluginPart1 + '\'' +
                ", serverCapability=" + serverCapability +
                ", mysqlCharset=" + mysqlCharset +
                ", serverStatus=" + serverStatus +
                ", lengthOfPluginData=" + lengthOfPluginData +
                ", authPluginPart2='" + authPluginPart2 + '\'' +
                ", authPluginName=" + authPluginName +
                '}';
    }

    public byte getProtoVersion() {
        return protoVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getConnectId() {
        return connectId;
    }

    public String getAuthPluginPart1() {
        return authPluginPart1;
    }

    public int getServerCapability() {
        return serverCapability;
    }

    public MysqlCharset getMysqlCharset() {
        return mysqlCharset;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public byte getLengthOfPluginData() {
        return lengthOfPluginData;
    }

    public String getAuthPluginPart2() {
        return authPluginPart2;
    }

    public String getAuthPluginName() {
        return authPluginName;
    }
}
