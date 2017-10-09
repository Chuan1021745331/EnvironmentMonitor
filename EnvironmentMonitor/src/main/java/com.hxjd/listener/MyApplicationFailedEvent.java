package com.hxjd.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author Nandem on 2017/8/24.
 */
public class MyApplicationFailedEvent implements ApplicationListener<ApplicationFailedEvent>
{

    private final static Logger logger = LoggerFactory.getLogger(MyApplicationFailedEvent.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent)
    {
        logger.error(applicationFailedEvent.getException().getLocalizedMessage());
    }
}
