package com.hxjd.handler.receiver.socket.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public abstract class DataReceiveServerHandler extends SimpleChannelInboundHandler<String>
{
    private final static Logger logger = LoggerFactory.getLogger(DataReceiveServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel incoming = ctx.channel();
        logger.info(incoming.remoteAddress() + "已连接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        handleData(ctx, msg);
    }

    /*^_^*------以下------*^_^*/
    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, String string);
}