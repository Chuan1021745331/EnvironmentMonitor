package com.hxjd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Nandem on 2017/8/25.
 */
@Component
public class SmartWorkSiteConfig
{
    @Value("${server.port}")
    private int port;

    public int getPort()
    {
        return port;
    }

    public String getDataRealTimeUrl()
    {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "获取IP失败" + e;
        }

        return "ws://" + host + ":" + this.getPort() + "/subSystemManager";
    }
}
