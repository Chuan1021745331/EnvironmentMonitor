package com.hxjd.connection.netty;

import com.alibaba.fastjson.JSON;
import com.hxjd.connection.websocket.DataRealTimeWebSocket;
import com.hxjd.connection.websocket.RealTimeData;
import com.hxjd.connection.websocket.RealTimeDataType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Time: 14:27
 * Date: 2017/9/15
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: Netty客户端消息处理类
 */
public class NettyClientHandler extends HeartBeatHandler
{
    private final static Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    public NettyClientHandler()
    {
        /*
         * 开关心跳检测日志
         * true：开
         * false：关
         */
        switchHeartBeatLogger(false);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception
    {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
    {
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        super.channelActive(ctx);
        logger.info("与控制中心建立连接");
        //TODO 建立连接后发送状态更新消息给前端
        /*
         * TODO 但这里有一点需要注意，当界面没有启动时，建立连接时发送连接成功是否真的合适？
         * 如果界面是在连接建立后启动的，该如何处理？
         * 是否改成在心跳包响应时发送状态更新更好？响应时还是发送时这是个问题，我觉得还是发送时比较好，今天的我你能知道昨天的我为什么这么想吗？
         */
        DataRealTimeWebSocket.sendMessage(new RealTimeData(RealTimeDataType.STATUS, "在线"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        super.channelInactive(ctx);
        logger.info("与控制中心断开连接");
        NettyClient.getInstance().reConnect();
        DataRealTimeWebSocket.sendMessage(new RealTimeData(RealTimeDataType.STATUS, "掉线"));
    }

    protected void receivePongMsg()
    {
        DataRealTimeWebSocket.sendMessage(new RealTimeData(RealTimeDataType.STATUS, "在线"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        super.exceptionCaught(ctx, cause);
        logger.error("控制中心服务关闭或网络异常");
    }

    /*^_^*------以下------*^_^*/
    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, String string)
    {
        String result = string;

        logger.debug("收到服务器数据：" + result);
    }

    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx)
    {
        sendPingMsg(ctx);
    }
}
