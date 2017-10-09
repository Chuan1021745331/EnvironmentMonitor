package com.hxjd.connection.http;

import com.hxjd.connection.http.post.PostRequest;

/**
 * Time: 16:52
 * Date: 2017/9/19
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class SmartHttp
{
    /*^_^------post请求------^_^*/
    public static PostRequest post(String url)
    {
        return new PostRequest(url);
    }

    /*^_^------get请求------^_^*/
    public static void get()
    {

    }
}
