package com.hxjd.handler.receiver.socket.netty;

/*import com.hxjd.utils.MyPath;*/
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

import java.io.*;
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
public final class DataReceiveServer
{
    //默认端口9003，修改端口需要在application.yaml中配置
    private static int port = 9003;

    private static Map<String, Channel> clientMap = new HashMap<>();

    private final static Logger logger = LoggerFactory.getLogger(DataReceiveServer.class);

    public void start()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new DataReceiveServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            logger.info("硬件数据接收服务已经启动");

            //logger.info("调试信息：" + ClassUtils.getDefaultClassLoader().getResource("").getPath());
//            InputStream inputStream = this.getClass().getResourceAsStream(MyPath.getProjectPath() + "/configs/port.txt");
//            File file = new File(MyPath.getProjectPath() + "/configs/port.txt");
////            inputStream = file.getAbsolutePath();
//            try
//            {
//                byte[] test = readStream(inputStream);
//                logger.info("调试信息：" + new String(test));
////                logger.info("getProjectPath调试信息：" + MyPath.getProjectPath());
////                logger.info("getRealPath调试信息：" + MyPath.getRealPath());
////                logger.info("getAppPath调试信息：" + MyPath.getAppPath(this.getClass()));
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//                logger.info("调试信息：又出错" + e.getMessage());
//            }

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
            logger.error("硬件数据接收服务启动失败");
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.error("硬件数据接收服务启动失败");
        }
    }

    /*^_^*------以下业务无关------*^_^*/
    private static volatile DataReceiveServer instance;

    static
    {
        Yaml yaml = new Yaml();
        String path = DataReceiveServer.class.getClassLoader().getResource("ports.yaml").getPath();
        path = path.substring(1, path.length());
        try
        {
            Map ports = yaml.loadAs(new FileInputStream(new File(path)), Map.class);
            Map netty = (Map) ports.get("hardware");
            port = (int) netty.get("dataReceiveServerPort");
        }
        catch(FileNotFoundException e)
        {
            logger.error("加载ports.yaml出错或未配置dataReceiveServerPort，将使用缺省端口9003");
            port = 9003;
        }
    }

    private DataReceiveServer()
    {
    }

    public static DataReceiveServer getInstance()
    {
        if(instance == null)
        {
            synchronized(DataReceiveServer.class)
            {
                if(instance == null)
                {
                    return new DataReceiveServer();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args)
    {
        DataReceiveServer.getInstance().start();
    }
}
