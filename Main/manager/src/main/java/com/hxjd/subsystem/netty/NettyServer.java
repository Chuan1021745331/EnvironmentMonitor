package com.hxjd.subsystem.netty;

import com.hxjd.subsystem.SubSystemName;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Time: 9:28
 * Date: 2017/9/25
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class NettyServer
{
    //默认端口9003，修改端口需要在application.yaml中配置
    private static int port = 9003;

    private static Map<String, Channel> clientMap = new HashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public void start()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            logger.info("子系统监测服务启动成功");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            /*
             * 此语句将导致阻塞直到服务器被关闭
             * finally暂时也不会执行
             */
            f.channel().closeFuture().sync();

        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            logger.error("子系统监测服务启动失败");
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.error("子系统监测服务启动失败");
        }
    }

    public void addToClientMap(String name, Channel channel)
    {
        clientMap.put(name, channel);
    }

    public String removeFromClientMap(Channel channel)
    {
        String temKey = getClientName(channel);

        try
        {
            clientMap.remove(temKey);
        }
        catch(Exception e)
        {
            logger.error("该子系统未连接，无需移除");
        }

        return temKey;
    }

    public String getClientName(Channel channel)
    {
        String temKey = "";
        for(Map.Entry<String, Channel> me : clientMap.entrySet())
        {
            if(channel.remoteAddress().toString().equals(me.getValue().remoteAddress().toString()))
            {
                temKey = me.getKey();
            }
        }

        return temKey;
    }

    public void sendMsg(SubSystemName name, String msg)
    {
        clientMap.get(name.getName()).writeAndFlush(msg);
    }

    /*^_^*------以下业务无关------*^_^*/
    private static volatile NettyServer instance;

    static
    {
        Yaml yaml = new Yaml();
        String path = NettyServer.class.getClassLoader().getResource("ports.yaml").getPath();
        path = path.substring(1, path.length());
        try
        {
            Map ports = yaml.loadAs(new FileInputStream(new File(path)), Map.class);
            Map netty = (Map) ports.get("netty");
            port = (int) netty.get("port");
        }
        catch(FileNotFoundException e)
        {
            logger.error("加载ports.yaml出错或未配置netty port，将使用缺省端口");
            port = 9003;
        }
    }

    private NettyServer()
    {
    }

    public static NettyServer getInstance()
    {
        if(instance == null)
        {
            synchronized(NettyServer.class)
            {
                if(instance == null)
                {
                    return new NettyServer();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args)
    {
        NettyServer.getInstance().start();
    }
}
