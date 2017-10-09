package com.hxjd.connection.websocket;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Time: 12:00
 * Date: 2017/9/25
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 用于实时更新界面数据，前后端数据处理需要自己实现
 */
@Component
@ServerEndpoint("/dataRealTime")
public class DataRealTimeWebSocket
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * 这里使用set装连接的原因在于可能有多个浏览器查看界面，故若有多个连接需要发送多次
     */
    private static CopyOnWriteArraySet<DataRealTimeWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException
    {
        this.session = session;
        webSocketSet.add(this);
        sendMessage(new RealTimeData(RealTimeDataType.STATUS, "在线"));
        logger.info("实时数据界面已连接");
    }

    @OnClose
    public void onClose() throws IOException
    {
        webSocketSet.remove(this);
        for(DataRealTimeWebSocket item : webSocketSet)
        {
            logger.info("实时数据界面已关闭");
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
        for(DataRealTimeWebSocket dw : webSocketSet)
        {
            dw.send(JSON.toJSONString(message));
        }
    }
}
