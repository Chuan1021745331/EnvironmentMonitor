package com.hxjd.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Nandem on 2017/8/24.
 */
public class MyApplicationStartingEvent implements ApplicationListener<ApplicationStartingEvent>
{
    private final static Logger logger = LoggerFactory.getLogger(MyApplicationStartingEvent.class);

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent)
    {

    }
}
