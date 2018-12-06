package com.zjf.wexin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;



public class Joke {
    private static final String AppKey="99b6ec519caa10321247dfd0f6ef97ad";
    private static final String url="http://v.juhe.cn/joke/randJoke.php?key=";

    public static String getJoke(){
        String sendUrl=url+AppKey;
        JSONObject jsonObject =HttpGetPost.doGetStr(sendUrl);
        JSONArray jsarr=jsonObject.getJSONArray("result");
        StringBuffer sb=new StringBuffer();
        //此处只获取一条笑话，可按需获取条数，最多10条
        for (int i=0;i<jsarr.size()-9;i++){
            sb.append(jsarr.getJSONObject(i).get("content")+"\n");
        }
        return sb.toString();
    }

}
