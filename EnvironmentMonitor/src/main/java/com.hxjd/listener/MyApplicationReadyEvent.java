package com.hxjd.listener;

import com.hxjd.connection.netty.NettyClient;
import com.hxjd.handler.receiver.socket.netty.DataReceiveServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Nandem on 2017/8/24.
 */
public class MyApplicationReadyEvent implements ApplicationListener<ApplicationReadyEvent>
{
    private final static Logger logger = LoggerFactory.getLogger(MyApplicationReadyEvent.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        logger.info("视频监控系统启动成功");
        new Thread(() -> NettyClient.getInstance().start()).start();
        new Thread(() -> DataReceiveServer.getInstance().start()).start();
    }
}
