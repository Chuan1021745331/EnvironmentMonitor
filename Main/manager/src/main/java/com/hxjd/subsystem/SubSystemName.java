package com.hxjd.subsystem;

/**
 * Time: 22:23
 * Date: 2017/9/27
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public enum SubSystemName
{

    SURVEILLANCE(0, "Surveillance"),
    LOCATION(1, "Location"),
    LABOR_MANAGEMENT(2, "LaborManagement"),
    ENVIRONMENT_MONITOR(3, "EnvironmentMonitor");

    private int code;
    private String name;

    SubSystemName(int code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public int getCode()
    {
        return this.code;
    }

    /**
     * 通过code判定是否相等
     *
     * @param code code
     * @return 相等返回true，否则返回false;
     */
    public boolean isEquals(int code)
    {
        return this.code == code;
    }

    public static String getNameByCode(int code)
    {
        SubSystemName[] resultCodes = values();
        String returnValue;
        try
        {
            returnValue = resultCodes[code].name;
        }
        catch(Exception e)
        {
            returnValue = "未定义操作码";
        }
        return returnValue;
    }
}
