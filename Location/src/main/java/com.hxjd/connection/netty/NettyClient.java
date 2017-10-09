package com.hxjd.connection.netty;

import com.hxjd.config.SurveillanceConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Time: 14:27
 * Date: 2017/9/15
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: Netty客户端，向控制中心提供子系统状态
 *       说白了Netty就只有三板斧，如果百度太复杂就直接跟我讨论一下
 *       第一板斧：初始化并添配置。此处是NettyClientInitializer中的配置
 *       第二板斧：消息处理器。NettyClientHandler
 *       第三板斧：连接。连接时注意是异步的
 */
public class NettyClient
{
    private static String host;
    private static int port;

    private static Channel channel;
    private static Bootstrap bootstrap;
    private static ChannelFuture future;

    private final static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public void start()
    {
        EventLoopGroup group = new NioEventLoopGroup();
        try
        {
            bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
                    // 有数据立即发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 保持连接
                    .option(ChannelOption.SO_KEEPALIVE, true).handler(new NettyClientInitializer());
            future = bootstrap.connect(host, port).sync();
            future.addListener((ChannelFutureListener) channelFuture -> {
                channel = future.channel();
                sendActiveMsg(channel);
                logger.info("控制中心监听服务启动成功");
            });


        }
        catch(Exception e)
        {
            logger.error("控制中心未启动");
            reConnect();
        }
    }

    /**
     * 给控制中心发送消息
     * @param msg 消息内容
     */
    public void sendMsg(String msg)
    {
        if(channel == null || !channel.isActive())
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            sendMsg(msg);
        }
        else
        {
            String msgJsonStr = "{'type':'3', 'content':'" + msg + "'}";
            channel.writeAndFlush(msgJsonStr);
        }
    }

    /**
     * 当服务端未启动或网络问题等原因导致失去连接时，调用此方法可以断线重连
     */
    public void reConnect()
    {
        if(channel != null && channel.isActive())
        {
            return;
        }

        future = bootstrap.connect(host, port);

        future.addListener((ChannelFutureListener) futureListener ->
        {
            if(futureListener.isSuccess())
            {
                channel = futureListener.channel();
                sendActiveMsg(channel);
                logger.info("已重新连接控制中心");
            }
            else
            {
                logger.error("5s后重连");
                futureListener.channel().eventLoop().schedule(this::reConnect, 5, TimeUnit.SECONDS);
            }
        });
    }

    private void sendActiveMsg(Channel channel)
    {
        new NioSocketChannel().remoteAddress();
        sendMsg("#init#" + SurveillanceConfig.FLAG);
    }

    /*^_^*------以下业务无关------*^_^*/
    private static volatile NettyClient instance;

    static
    {
        Yaml yaml = new Yaml();
        String path = NettyClient.class.getClassLoader().getResource("configs.yaml").getPath();
        path = path.substring(1, path.length());
        try
        {
            Map ports = yaml.loadAs(new FileInputStream(new File(path)), Map.class);
            Map server = (Map) ports.get("server");
            port = (int) server.get("port");
            host = (String) server.get("host");
        }
        catch(FileNotFoundException e)
        {
            logger.error("configs.yaml出错或未配置server port&host，将使用缺省配置");
            port = 9003;
        }
    }

    private NettyClient()
    {
    }

    public static NettyClient getInstance()
    {
        if(instance == null)
        {
            synchronized(NettyClient.class)
            {
                if(instance == null)
                {
                    return new NettyClient();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args)
    {
        NettyClient.getInstance().start();
    }
}