package com.hxjd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Time: 9:52
 * Date: 2017/9/19
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 配置类，可以直接拿到application.yaml中的配置
 */
@Component
public class LaborManagementConfig
{
    public static final String FLAG = "LaborManagement";

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

        return "ws://" + host + ":" + this.getPort() + "/dataRealTime";
    }
}
