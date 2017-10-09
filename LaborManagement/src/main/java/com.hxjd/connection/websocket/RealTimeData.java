package com.hxjd.connection.websocket;

/**
 * Time: 13:55
 * Date: 2017/9/28
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class RealTimeData
{
    private RealTimeDataType type;
    private String content;

    public RealTimeData(RealTimeDataType type, String content)
    {
        this.type = type;
        this.content = content;
    }

    public RealTimeDataType getType()
    {
        return type;
    }

    public void setType(RealTimeDataType type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
