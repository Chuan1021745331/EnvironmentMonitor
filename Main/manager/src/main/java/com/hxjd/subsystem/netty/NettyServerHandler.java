package com.hxjd.subsystem.netty;

import com.hxjd.subsystem.SubSystemRegistry;
import com.hxjd.subsystem.websocket.SubSystemManagerWebSocket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Time: 9:30
 * Date: 2017/9/25
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class NettyServerHandler extends HeartBeatHandler
{

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public NettyServerHandler()
    {
        switchHeartBeatLogger(false);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();
        logger.info(incoming.remoteAddress() + "已连接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();

        //移除断开连接的子系统channel并获取该子系统名称
        String inActiveSubSystemName = NettyServer.getInstance().removeFromClientMap(incoming);

        //更新该子系统状态（前端界面使用）
        SubSystemRegistry.getInstance().updateStatus(inActiveSubSystemName, false);

        //发送子系统状态至前端（全部）
        SubSystemManagerWebSocket.sendSubSystemsStatus();

        logger.info("【" + inActiveSubSystemName + "】断开连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        logger.error("【" + NettyServer.getInstance().getClientName(ctx.channel()) + "】关闭或网络异常");
        //NettyServer.getInstance().removeFromClientMap(incoming);
        ctx.close();
    }

    @Override
    protected void afterToPongMsg()
    {
        SubSystemManagerWebSocket.sendSubSystemsStatus();
    }

    /*^_^*------以下------*^_^*/
    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, String string)
    {
        if(string.startsWith("#init#"))
        {
            //logger.debug("初始化");//格式#init#子系统名称
            String subSystemName = string.split("#")[2];

            //添加子系统channel
            NettyServer.getInstance().addToClientMap(subSystemName, channelHandlerContext.channel());

            //注册子系统状态（前端界面使用）
            SubSystemRegistry.getInstance().register(subSystemName, true);

            //发送子系统状态至前端（全部）
            SubSystemManagerWebSocket.sendSubSystemsStatus();

            /*
             * FIXME 这里有一个更智能的实现，当所有子系统发来初始化消息后动态的网前端界面添加子系统状态信息
             * FIXME 如果子系统没有发来初始化信息则不在界面显示
             * FIXME 如果子系统发来初始化信息后有掉线，则页面显示掉线，重新连接时恢复正常
             */
        }
    }
}