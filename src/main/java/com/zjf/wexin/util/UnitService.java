package com.zjf.wexin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/*
 * unit对话服务
 */
public class UnitService {
    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String utterance(String source) {
        // 请求URL
        String talkUrl = "https://aip.baidubce.com/rpc/2.0/unit/bot/chat";
        try {
            // 请求参数
            String params = "{\"bot_session\":\"\",\"log_id\":\"7758521\",\"request\":{\"bernard_level\":1,\"client_session\":\"{\\\"client_results\\\":\\\"\\\", \\\"candidate_options\\\":[]}\",\"query\":\""+source+"\",\"query_info\":{\"asr_candidates\":[],\"source\":\"KEYBOARD\",\"type\":\"TEXT\"},\"updates\":\"\",\"user_id\":\"88888\"},\"bot_id\":5,\"version\":\"2.0\"}";
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(talkUrl, accessToken, "application/json", params);
            JSONObject jsonObject= JSON.parseObject(result);
            System.out.println(jsonObject);
            String result1=jsonObject.getString("result");
            JSONObject jsonObject1=JSON.parseObject(result1);
            String response=jsonObject1.getString("response");
            JSONObject jsonObject3=JSON.parseObject(response);
            JSONArray jsarr=jsonObject3.getJSONArray("action_list");
            return jsarr.getJSONObject(0).get("say").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
