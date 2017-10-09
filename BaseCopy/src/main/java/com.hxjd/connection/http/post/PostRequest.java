package com.hxjd.connection.http.post;

import com.hxjd.utils.StringUtil;
import okhttp3.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Time: 9:09
 * Date: 2017/9/20
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 请输入描述
 */
public class PostRequest
{
    public final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private Map<String, String> paramsMap = new HashMap<>();
    private Params params;
    private Map<String, String> headersMap = new HashMap<>();
    private Headers headers;
    private String jsonData;

    private String url;

    public PostRequest(String url)
    {
        this.url = url;
    }

    /**
     * 同一次请求中,若params(String, String)与json(String)同时出现，将默认使用后出现的作为参数，先出现的将被忽略。
     *
     * @param key   参数名
     * @param value 参数值
     * @return PostRequest对象
     * @see #json(String)
     */
    public PostRequest params(String key, String value)
    {
        if(!StringUtil.isEmpty(jsonData))
        {
            jsonData = "";
        }

        paramsMap.put(key, value);
        return this;
    }

    public PostRequest params(Params params)
    {
        this.params = params;
        return this;
    }

    /**
     * 同一次请求中,若params(String, String)与json(String)同时出现，将默认使用后出现的作为参数，先出现的将被忽略。
     *
     * @param jsonData json参数
     * @return PostRequest对象
     * @see #params(String, String)
     */
    public PostRequest json(String jsonData)
    {
        if(!paramsMap.isEmpty())
        {
            paramsMap.clear();
        }

        this.jsonData = jsonData;
        return this;
    }

    public PostRequest headers(String key, String value)
    {
        headersMap.put(key, value);
        return this;
    }

    public PostRequest headers(Headers headers)
    {
        this.headers = headers;
        return this;
    }

    public void execute(Callback callback)
    {
        if(!StringUtil.isEmpty(jsonData))
        {
            jsonRequest(callback);
        }
        else if(!paramsMap.isEmpty())
        {
            formRequest(callback);
        }
        else
        {
            emptyRequest(callback);
        }
    }

    /*^_^------辅助函数------^_^*/
    private void jsonRequest(Callback callback)
    {
        //处理参数，这里是json参数
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonData);

        buildRequest(requestBody, callback);
    }

    private void formRequest(Callback callback)
    {
        //处理参数，这里是普通form参数
        FormBody.Builder builder = new FormBody.Builder();

        //组合参数，因为可同时接受分散添加或整合添加的参数
        combineParams();

        for(Map.Entry<String, String> me : paramsMap.entrySet())
        {
            builder.add(me.getKey(), me.getValue());
        }

        buildRequest(builder.build(), callback);

    }

    private void emptyRequest(Callback callback)
    {
        formRequest(callback);
    }

    private void buildRequest(RequestBody requestBody, Callback callback)
    {
        OkHttpClient okHttpClient = new OkHttpClient();

        //处理headers
        combineHeaders();
        Headers.Builder builder = new Headers.Builder();
        for(Map.Entry<String, String> me : headersMap.entrySet())
        {
            builder.add(me.getKey(), me.getValue());
        }
        Headers headers = builder.build();

        //构造请求
        Request request = new Request.Builder().headers(headers).url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);

        //异步执行
        call.enqueue(callback);
    }

    /**
     * 将headers和headersMap参数进行整合
     */
    private void combineHeaders()
    {
        if(headers == null)
            return;

        Class clazz = headers.getClass();
        try
        {
            Field field = clazz.getDeclaredField("namesAndValues");
            field.setAccessible(true);

            String[] s = (String[]) field.get(headers);

            for(int i = 0; i < s.length; i += 2)
            {
                headersMap.put(s[i], s[i + 1]);
            }
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 将params和paramsMap参数进行整合
     */
    private void combineParams()
    {
        if(params == null)
            return;

        paramsMap.putAll(params.getParams());
    }
}
