package com.hxjd.subsystem.websocket;

import com.alibaba.fastjson.JSON;
import com.hxjd.subsystem.SubSystem;
import com.hxjd.subsystem.SubSystemRegistry;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Time: 12:00
 * Date: 2017/9/25
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 与控制中心前端交互
 */
@Component
@ServerEndpoint("/subSystemManager")
public class SubSystemManagerWebSocket
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<SubSystemManagerWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException
    {
        this.session = session;
        webSocketSet.add(this);
        logger.info("控制中心界面已连接");
    }

    @OnClose
    public void onClose() throws IOException
    {
        webSocketSet.remove(this);
        for(SubSystemManagerWebSocket item : webSocketSet)
        {
            logger.info("控制中心界面已关闭");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException
    {
    }

    private void send(String data)
    {
        try
        {
            this.session.getBasicRemote().sendText(data);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Object message)
    {
        for(SubSystemManagerWebSocket dw : webSocketSet)
        {
            dw.send(JSON.toJSONString(message));
        }
    }

    public static void sendSubSystemsStatus()
    {
        Map<String, Boolean> subSystemStatus = SubSystemRegistry.getInstance().getSubSystemStatusMap();

        for(Map.Entry<String, Boolean> me : subSystemStatus.entrySet())
        {
            SubSystem subSystem = new SubSystem(me.getKey(), me.getValue());
            RealTimeData realTimeData = new RealTimeData(RealTimeDataType.STATUS, subSystem);
            sendMessage(realTimeData);
        }
    }
}
