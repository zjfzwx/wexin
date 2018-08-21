package com.zjf.wexin.util;

import com.alibaba.fastjson.JSONObject;
import com.zjf.wexin.trans.Data;
import com.zjf.wexin.trans.Parts;
import com.zjf.wexin.trans.Symbols;
import com.zjf.wexin.trans.TransResult;
import org.apache.http.ParseException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Translate {
    private static final String APPID="20180820000196568";
    private static final String SECRETKEY="ZCITcCdlr6Yo35nA5LzI";

    public static String createSign(String sign){
        return MD5.md5(sign);
    }
    public static String translate(String source) throws ParseException, IOException {
        Integer salt=new Random().nextInt(100);
        String sign=APPID+source+salt+SECRETKEY;
        sign=Translate.createSign(sign);
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?appid=APPID&q=SOURCE&from=auto&to=auto&salt="+salt+"&sign=SIGN";
        url = url.replace("APPID", URLEncoder.encode(APPID, "UTF-8"));
        url = url.replace("SOURCE", URLEncoder.encode(source, "UTF-8"));
        url = url.replace("SIGN", URLEncoder.encode(sign, "UTF-8"));
        JSONObject jsonObject = HttpGetPost.doGetStr(url);
        String errno = jsonObject.getString("errno");
        Object obj = jsonObject.get("data");
        StringBuffer dst = new StringBuffer();
        if("0".equals(errno) && !"[]".equals(obj.toString())){
            TransResult transResult = JSONObject.toJavaObject(jsonObject, TransResult.class);
            Data data = transResult.getData();
            Symbols symbols = data.getSymbols()[0];
            String phzh = symbols.getPh_zh()==null ? "" : "中文拼音："+symbols.getPh_zh()+"\n";
            String phen = symbols.getPh_en()==null ? "" : "英式英标："+symbols.getPh_en()+"\n";
            String pham = symbols.getPh_am()==null ? "" : "美式英标："+symbols.getPh_am()+"\n";
            dst.append(phzh+phen+pham);

            Parts[] parts = symbols.getParts();
            String pat = null;
            for(Parts part : parts){
                pat = (part.getPart()!=null && !"".equals(part.getPart())) ? "["+part.getPart()+"]" : "";
                String[] means = part.getMeans();
                dst.append(pat);
                for(String mean : means){
                    dst.append(mean+";");
                }
            }
        }else{
            dst.append(translateFull(source));
        }
        return dst.toString();
    }

    public static String translateFull(String source) throws ParseException, IOException{
        Integer salt=new Random().nextInt(100);
        String sign=APPID+source+salt+SECRETKEY;
        sign=Translate.createSign(sign);
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?appid=APPID&q=SOURCE&from=auto&to=auto&salt="+salt+"&sign=SIGN";
        url = url.replace("APPID", URLEncoder.encode(APPID, "UTF-8"));
        url = url.replace("SOURCE", URLEncoder.encode(source, "UTF-8"));
        url = url.replace("SIGN", URLEncoder.encode(sign, "UTF-8"));
        JSONObject jsonObject =HttpGetPost.doGetStr(url);
        StringBuffer dst = new StringBuffer();
        List<Map> list = (List<Map>) jsonObject.get("trans_result");
        for(Map map : list){
            dst.append(map.get("dst"));
        }
        return dst.toString();
    }
}
