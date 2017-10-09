package com.hxjd.utils;

import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

/**
 * Time: 9:35
 * Date: 2017/9/14
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 这里左右的工具函数都是符合建委标准的专用函数，不做通用使用
 */
public class AuthenticationUtil
{

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationUtil.class);

    private static String keyId;
    private static String keySecret;

    static
    {
        Yaml yaml = new Yaml();
        String path = AuthenticationUtil.class.getClassLoader().getResource("key.yaml").getPath();
        path = path.substring(1, path.length());
        try
        {
            Map key = yaml.loadAs(new FileInputStream(new File(path)), Map.class);
            keyId = key.get("keyId").toString();
            keySecret = key.get("keySecret").toString();
        }
        catch(FileNotFoundException e)
        {
            logger.error("加载key.yaml出错，使用缺省密钥");
            keyId = "缺省密钥id";
            keySecret = "缺省密钥值";
        }
    }

    /**
     * 获取随机字符串
     *
     * @return 长度为10的随机字符串
     */
    private static String getRandomString()
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++)
        {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取时间戳，建委要求与服务器对时，但是未提供相应的接口，这里暂时使用我们自己的
     *
     * @return 返回以秒为单位的时间戳
     */
    @Deprecated
    private static String getTimeStamp()
    {
        long ts = System.currentTimeMillis() / 1000;
        return String.valueOf(ts);
    }

    /**
     * sha1散列算法
     * @param data 原始字符串
     * @return 经sha1计算后的加密字符串
     */
    private static String sha1(String data)
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA1");
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        assert md != null;
        md.update(data.getBytes());
        StringBuilder buf = new StringBuilder();
        byte[] bits = md.digest();
        for(byte bit : bits)
        {
            int a = bit;
            if(a < 0)
                a += 256;
            if(a < 16)
                buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(sha1("F7R6W7eFmW_1505376020_8Yn8vptQYzsx3WX9nV72NyB4Q3p6U8"));
    }

    private static String getKeyId()
    {
        return keyId;
    }

    private static String getKeySecret()
    {
        return keySecret;
    }

    public static Headers getHeaders()
    {
        String ts = getTimeStamp();
        String rcode = getRandomString();
        String signature = sha1(rcode + "_" + ts + "_" + getKeySecret());

        Headers headers = new Headers.Builder()
                .add("keyId", getKeyId())//授权id
                .add("ts", ts)//时间戳
                .add("rcode", rcode)//随机字符串
                .add("signature", signature)//密钥签名
                .build();

        return headers;
    }
}
