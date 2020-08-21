package com.zjf.wexin.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class Utils {
    public static String asUrlParams(Map<String, Object> source){
        Iterator<String> it = source.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (it.hasNext()){
            String key = it.next();
            String value = source.get(key).toString();
            if (value.equals(null)){
                continue;
            }
            try {
                // URL 编码
                value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // do nothing
            }
            paramStr.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&
        return paramStr.substring(1);
    }

    public static Long getMemberId(String baseUrl, String sessionKey){
        baseUrl= String.format(baseUrl,sessionKey);
        String result= HttpUtil.get(baseUrl);
        JSONObject object= JSON.parseObject(result);
        return Long.valueOf(object.getString("Obj"));
    }
    public static String trimFirstAndLastChar(String str, String element) {
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do {
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }
}
