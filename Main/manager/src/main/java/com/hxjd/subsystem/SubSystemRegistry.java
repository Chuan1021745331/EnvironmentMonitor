package com.hxjd.subsystem;

import com.hxjd.utils.EventBusUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Time: 15:25
 * Date: 2017/9/29
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 所有连接上控制中心的子系统都需要在此处注册
 */
public class SubSystemRegistry
{
    private Map<String, Boolean> subSystemStatusMap = new HashMap<>();

    public Map<String, Boolean> getSubSystemStatusMap()
    {
        return subSystemStatusMap;
    }

    public boolean register(String name, boolean status)
    {
        return subSystemStatusMap.put(name, status);
    }

    /**
     * 更新子系统状态，这里利用map键不能重复的特性更新状态
     * 但在更新前需要判断子系统是否已注册，未注册直接返回false
     * @param name 子系统名称
     * @param status 子系统新的状态
     * @return true：更新成功，false：更新失败。
     */
    public boolean updateStatus(String name, boolean status)
    {
        try
        {
            //如果子系统未注册过将会抛出异常
            subSystemStatusMap.get(name);
            return register(name, status);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /*^_^*------以下业务无关------*^_^*/
    private final static Logger logger = LoggerFactory.getLogger(SubSystemRegistry.class);
    private static volatile SubSystemRegistry instance = null;

    private SubSystemRegistry()
    {
    }

    public static SubSystemRegistry getInstance()
    {
        if (instance == null)
        {
            synchronized (SubSystemRegistry.class)
            {
                if (instance == null)
                {
                    instance = new SubSystemRegistry();
                }
            }
        }
        return instance;
    }
}
