package com.hxjd.connection.http.post;

import java.util.HashMap;
import java.util.Map;

/**
 * Time: 14:03
 * Date: 2017/9/20
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class Params
{
    private Map<String, String> params = new HashMap<>();

    public Params add(String key, String value)
    {
        params.put(key, value);
        return this;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public boolean isEmpty()
    {
        return params.isEmpty();
    }
}
