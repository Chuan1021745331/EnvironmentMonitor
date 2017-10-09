package com.hxjd.subsystem.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Time: 11:12
 * Date: 2017/9/26
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 内置心跳检测的消息处理类，通用类可以直接拷贝在别处使用
 */
public abstract class HeartBeatHandler extends SimpleChannelInboundHandler<String>
{
    private final static Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    public static final byte PING = 1;
    public static final byte PONG = 2;
    public static final byte MSG = 3;
    private boolean loggerFlag;


    protected void switchHeartBeatLogger(boolean onOrOff)
    {
        this.loggerFlag = onOrOff;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, String string) throws Exception
    {
        JSONObject jsonObject = JSON.parseObject(string);
        if(jsonObject.getInteger("type") == PING)
        {
            sendPongMsg(context);
            afterToPongMsg();
        }
        else if(jsonObject.getInteger("type") == PONG)
        {
            if(loggerFlag)
            {
                logger.info("收到心跳响应，来自：" + context.channel().remoteAddress());
            }
            receivePongMsg();
        }
        else
        {
            handleData(context, jsonObject.getString("content"));
        }
    }

    protected void receivePongMsg() {}

    protected void afterToPongMsg() {}

    protected void sendPingMsg(ChannelHandlerContext context)
    {
        String msgJsonStr = "{'type':'1', 'content':''}";
        context.writeAndFlush(msgJsonStr);
        if(loggerFlag)
        {
            logger.info("发送心跳包，目标：" + context.channel().remoteAddress());
        }
    }

    private void sendPongMsg(ChannelHandlerContext context)
    {
        String msgJsonStr = "{'type':'2', 'content':''}";
        context.writeAndFlush(msgJsonStr);
        if(loggerFlag)
        {
            logger.info("响应心跳包，目标：" + context.channel().remoteAddress());
        }
    }

    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, String string);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if(evt instanceof IdleStateEvent)
        {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch(e.state())
            {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx)
    {
        if(loggerFlag)
        {
            logger.error("READER_IDLE");
        }

    }

    protected void handleWriterIdle(ChannelHandlerContext ctx)
    {
        if(loggerFlag)
        {
            logger.error("WRITER_IDLE");
        }
    }

    protected void handleAllIdle(ChannelHandlerContext ctx)
    {
        if(loggerFlag)
        {
            logger.error("ALL_IDLE");
        }
    }
}