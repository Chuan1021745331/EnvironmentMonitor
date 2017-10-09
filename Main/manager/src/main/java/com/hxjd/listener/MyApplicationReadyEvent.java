package com.hxjd.listener;

import com.hxjd.subsystem.netty.NettyServer;
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
        logger.info("控制中心启动成功");
        NettyServer.getInstance().start();
    }
}
