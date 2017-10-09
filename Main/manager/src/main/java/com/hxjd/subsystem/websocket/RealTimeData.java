package com.hxjd.subsystem.websocket;

import com.hxjd.subsystem.SubSystem;

/**
 * Time: 13:55
 * Date: 2017/9/28
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 网前端发送的实时数据（会转成json）
 */
public class RealTimeData
{
    private RealTimeDataType type;
    private String content;
    private SubSystem subSystem;

    public RealTimeData(RealTimeDataType type, String content)
    {
        this.type = type;
        this.content = content;
    }

    public RealTimeData(RealTimeDataType type, SubSystem subSystem)
    {
        this.type = type;
        this.subSystem = subSystem;
    }

    public SubSystem getSubSystem()
    {
        return subSystem;
    }

    public void setSubSystem(SubSystem subSystem)
    {
        this.subSystem = subSystem;
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
