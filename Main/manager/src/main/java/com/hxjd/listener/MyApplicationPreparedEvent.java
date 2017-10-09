package com.hxjd.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Nandem on 2017/8/24.
 */
public class MyApplicationPreparedEvent implements ApplicationListener<ApplicationPreparedEvent>
{
    private final static Logger logger = LoggerFactory.getLogger(MyApplicationPreparedEvent.class);

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent)
    {

    }
}
