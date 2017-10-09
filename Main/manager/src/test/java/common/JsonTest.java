package common;

import com.alibaba.fastjson.JSON;
import com.hxjd.subsystem.SubSystem;
import com.hxjd.subsystem.websocket.RealTimeData;
import com.hxjd.subsystem.websocket.RealTimeDataType;
import org.junit.Test;

/**
 * Time: 16:33
 * Date: 2017/9/29
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class JsonTest
{
    @Test
    public void testJson()
    {
        RealTimeData realTimeData = new RealTimeData(RealTimeDataType.STATUS, new SubSystem("aaa", true));
        System.out.println(JSON.toJSONString(realTimeData));
    }
}
