package com.hxjd.handler.receiver.socket;

import com.hxjd.handler.receiver.socket.netty.DataReceiveServerHandler;
import com.hxjd.model.Device;
import com.hxjd.model.Environment;
import com.hxjd.service.DeviceService;
import com.hxjd.service.EnvironmentService;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * Time: 11:45
 * Date: 2017/9/15
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 硬件数据处理类（socket方式）
 */
public class DispatcherCenterSocket extends DataReceiveServerHandler
{
    private final static Logger logger = LoggerFactory.getLogger(DispatcherCenterSocket.class);

    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private DeviceService deviceService;



    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, String string)
    {
        Environment environment = new Environment();
        /*environment.setSerialNo(string);*/
        //#env#deviceId#deviceCode#temperature#humidity#pm2p5#pm10#noise#windSpeed#windDirection#
        //如果是环境数据
        if(string.startsWith("#env#"))
        {
            //将字符串封装为Environment对象
            System.out.println(string);
            String[] envDataArr = string.split("#");
            Device device=new Device();
            //补全信息
            device.setDeviceId(envDataArr[2]);
            device.setDeviceCode(envDataArr[3]);
            environment.setDevice(device);
            environment.setTemperature(Double.parseDouble(envDataArr[4]));
            environment.setHumidity(Double.parseDouble(envDataArr[5]));
            environment.setPm2p5(Double.parseDouble(envDataArr[6]));
            environment.setPm10(Double.parseDouble(envDataArr[7]));
            environment.setNoise(Double.parseDouble(envDataArr[8]));
            environment.setWindSpeed(Double.parseDouble(envDataArr[9]));
            environment.setWindDirection(Double.parseDouble(envDataArr[10]));
            environment.setRecordTime(String.valueOf(new Date().getTime()));

            //初始化超标数据
            environment.setOutSideArgs();
            //存储设备信息
            deviceService.merge(device);

            //存入数据库
            environmentService.save(environment);

            //向前台传输数据
            environmentService.sendDataToWeb(environment);
            //向控制中心提交数据
            environmentService.sendDataToController(environment);
        }
        logger.debug("您发送的数据为："+string);

    }

    public static void main(String[] args)
    {
        String t = "#env#deviceId#deviceCode#temperature#humidity#pm2p5#pm10#noise#windSpeed#windDirection#";
        String[] tarr = t.split("#");
        System.out.println(tarr[1]);
        System.out.println(tarr[2]);
        System.out.println(tarr[3]);
    }
}