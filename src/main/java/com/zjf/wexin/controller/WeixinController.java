package com.zjf.wexin.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjf.wexin.request.ItaokeRequestBaseParam;
import com.zjf.wexin.request.WxCallBackParam;
import com.zjf.wexin.util.*;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class WeixinController {
    @Autowired
    private ItaokeRequestBaseParam baseParam;

    @RequestMapping(value = "/wexin", method = RequestMethod.GET)
    public void getWeixin1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        resp.reset();
        PrintWriter out = resp.getWriter();
        if (CheckUitl.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
            out.flush();
            out.close();
        }
    }

    @RequestMapping(value = "/wexin", method = RequestMethod.POST)
    public void postWexin(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, NoSuchAlgorithmException, KeyManagementException, NoSuchProviderException {
        response.reset();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String formUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String message = null;
        if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
            if ("讲个笑话".equals(content)) {
                message = MessageUtil.initText(toUserName, formUserName, Joke.getJoke());
            } else {
                System.out.println(content);
                message = MessageUtil.initText(toUserName, formUserName, UnitService.utterance(content));
            }

//            if ("1".equals(content)){
//               message= MessageUtil.initText(toUserName,formUserName,MessageUtil.first());
//            }else if ("2".equals(content)){
//                message= MessageUtil.initNews(toUserName,formUserName);
//            }else if ("3".equals(content)){
//                message= MessageUtil.initImageMessage(toUserName,formUserName);
//            }else if ("4".equals(content)){
//                message= MessageUtil.initMusic(toUserName,formUserName);
//            }else if ("5".equals(content)){
//                message = MessageUtil.initText(toUserName, formUserName, MessageUtil.fiveMenu());
//            } else if ("6".equals(content)){
//                message = MessageUtil.initText(toUserName, formUserName, MessageUtil.sixMenu());
//            } else if ("讲个笑话".equals(content)){
//                message = MessageUtil.initText(toUserName, formUserName, Joke.getJoke());
//            } else if ("?".equals(content)||"？".equals(content)){
//                message=  MessageUtil.initText(toUserName,formUserName,MessageUtil.mainMenu());
//            }else if (content.startsWith("翻译")){
//                String word=content.replaceFirst("^翻译","").trim();
//                if("".equals(word)){
//                    message = MessageUtil.initText(toUserName, formUserName, MessageUtil.fiveMenu());
//                }else{
//                    message = MessageUtil.initText(toUserName, formUserName, Translate.translate(word));
//                }
//            } else {
//
//            }
        } else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
            String eventType = map.get("Event");
            if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                message = MessageUtil.initText(toUserName, formUserName, MessageUtil.mainMenu());
            } else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
                message = MessageUtil.initText(toUserName, formUserName, MessageUtil.mainMenu());
            } else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
                String url = map.get("EventKey");
                message = MessageUtil.initText(toUserName, formUserName, url);
            } else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
                String key = map.get("EventKey");
                message = MessageUtil.initText(toUserName, formUserName, key);
            }
        } else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
            String label = map.get("Label");
            message = MessageUtil.initText(toUserName, formUserName, label);
        }
        System.out.println(message);
        out.print(message);
        out.flush();
        out.close();
    }

    @PostMapping("/monitorPush")
    public void createItaokeWxGroupNew(@RequestBody WxCallBackParam param) {
        String wxId = "wxid_rr2kq2tgd0pq22";
//        String wxId = "wxid_xy1l64swqm0022";
//        String wxId="wxid_1wm8ybzv8jkw22";
        if (param.getMessageType() == 5) {
            if (param.getData().getFromUser().equals(wxId)) {
                sendText(10727, param.getData().getFromUser(), UnitService.utterance(param.getData().getContent()), "127.0.0.1");
            }
        }

    }
    public JSONObject sendText(Integer robotId, String toWxId, String content, String client) {
        baseParam.setClient(client);
        baseParam.setMethod("itaoke.robot.macsend.text");
        baseParam.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        TreeMap<String, Object> param = SignMD5Util.transBean2Map(baseParam);
        String sendUrl = "http://router.itaoke.org/api?" + Utils.asUrlParams(param);
        param.put("robot_id", robotId);
        param.put("toWxId", toWxId);
        param.put("content", content);
        sendUrl = sendUrl + "&sign=" + SignMD5Util.getItaokeSignStr(param, "4a078c6b-db8a-8ac1-f376-40f7d50b57e3");
        Map<String, Object> originParam = new HashMap<>();
        originParam.put("robot_id", robotId);
        originParam.put("toWxId", toWxId);
        originParam.put("content", content);
        return getResponse(sendUrl, originParam);
    }

    public JSONObject getResponse(String sendUrl, Map<String, Object> originParam) {
        String result = HttpRequest.post(sendUrl).form(originParam).execute().body();
        return JSON.parseObject(result);
    }
}
