package com.zjf.wexin.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetPost {

    public static JSONObject doGetStr(String url){
        HttpClient httpClient  = HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        JSONObject jsonObject=null;
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity=response.getEntity();
            if (entity!=null){
                String result= EntityUtils.toString(entity);
                jsonObject= JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static JSONObject doPostStr(String url,String outStr){
        HttpClient httpClient  = HttpClientBuilder.create().build();
        HttpPost httpPost=new HttpPost(url);
        JSONObject jsonObject=null;
        try {
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse response=httpClient.execute(httpPost);
            String result=EntityUtils.toString(response.getEntity(),"UTF-8");
            jsonObject=JSONObject.parseObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
