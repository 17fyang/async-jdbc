package com.stu.asyncJdbc.handler;

import com.stu.asyncJdbc.jdbc.ExecuteContext;
import com.stu.asyncJdbc.packet.ServerHelloPacket;
import io.netty.channel.Channel;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 乌鸦坐飞机亠
 * @date: 2021/4/2 17:10
 * @Description:
 */
public class ChannelContext {
    public static final int TCP_CONNECTED = 0;
    public static final int RECEIVE_HANDSHAKE_INIT = 1;
    public static final int RESP_HANDSHAKE = 2;
    public static final int MYSQL_CONNECTED = 3;
    public static final int WAIT_RESP = 4;

    //该channel的状态
    private int channelStatus = TCP_CONNECTED;

    //对应的channel
    private final Channel channel;

    //mysql服务端的handshake包，包含了服务端的信息
    private ServerHelloPacket serverHelloPacket;

    //该channel的等待执行的任务队列
    private final Queue<ChannelTask> channelTaskQueue = new LinkedList<>();

    //包序列号id，从0开始，每次收到一个新的包则序列号加一，新的查询则将id重置为0
    private final AtomicInteger sequenceId = new AtomicInteger();

    public ChannelContext(Channel channel) {
        this.channel = channel;

        initSequence();
    }

    /**
     * 初始化序列号
     */
    public void initSequence() {
        sequenceId.set(0);
    }

    /**
     * 序列号递增并返回
     *
     * @return
     */
    public int sequenceAdd() {
        return sequenceId.incrementAndGet();
    }

    /**
     * 标记为开启一次新的查询
     */
    public void flagStartQuery() {
        this.initSequence();

        this.channelStatus = WAIT_RESP;
    }

    /**
     * 将该Channel标识为已接受服务端init包状态
     *
     * @param serverHelloPacket
     */
    public void flagReceiveServerHello(ServerHelloPacket serverHelloPacket) {
        this.channelStatus = RECEIVE_HANDSHAKE_INIT;

        this.serverHelloPacket = serverHelloPacket;
    }

    /**
     * 将该Channel标识为已回复Handshake包状态
     */
    public void flagHasResponseHandshake() {
        this.channelStatus = RESP_HANDSHAKE;
    }

    /**
     * 将该Channel标识为已完成mysql连接状态
     */
    public void flagMysqlConnected() {
        this.channelStatus = MYSQL_CONNECTED;
    }


    /**
     * 提交一个channel任务
     *
     * @param task
     */
    public void handInTask(ChannelTask task) {
        channelTaskQueue.add(task);
    }

    /**
     * 拉取一个channel任务，如果没有任务则返回空
     *
     * @return
     */
    public ChannelTask pollTask() {
        if (channelTaskQueue.isEmpty()) return null;
        return channelTaskQueue.poll();
    }

    /**
     * 将该Context转换为ExecuteContext暴露给调用者
     *
     * @return
     */
    public ExecuteContext toExecuteContext() {
        return new ExecuteContext();
    }


    public int getServerCapability() {
        return serverHelloPacket.getServerCapability();
    }

    public int getChannelStatus() {
        return channelStatus;
    }

    public ServerHelloPacket getServerHelloPacket() {
        return serverHelloPacket;
    }

    public int getSequenceId() {
        return sequenceId.get();
    }

    public Channel channel() {
        return channel;
    }
}
