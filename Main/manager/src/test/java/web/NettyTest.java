package web;

import com.hxjd.subsystem.netty.NettyServer;
import org.junit.Test;

/**
 * Time: 10:42
 * Date: 2017/9/25
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class NettyTest
{
    @Test
    public void startNettyTest()
    {
        NettyServer.getInstance().start();
    }
}
