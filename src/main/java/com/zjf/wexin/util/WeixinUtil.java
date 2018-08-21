package com.zjf.wexin.util;

import com.alibaba.fastjson.JSONObject;
import com.zjf.wexin.menu.Button;
import com.zjf.wexin.menu.ClickButton;
import com.zjf.wexin.menu.Menu;
import com.zjf.wexin.menu.ViewButton;
import com.zjf.wexin.po.AccessToken;
import org.apache.http.ParseException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WeixinUtil {
    private static final String APPID="wxce78491fdf01cffb";
    private static final String APPSECRET="c9f88fcfdc673ee6e2583f7103a67c80";
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken() throws ParseException, IOException{
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = HttpGetPost.doGetStr(url);
        if(jsonObject!=null){
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInteger("expires_in"));
        }
        return token;
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();

        ClickButton button1_1 = new ClickButton();
        button1_1.setName("拍照发图");
        button1_1.setType("pic_sysphoto");
        button1_1.setKey("1_1");

        ClickButton button1_2 = new ClickButton();
        button1_2.setName("相册发图");
        button1_2.setType("pic_photo_or_album");
        button1_2.setKey("1_2");

        ClickButton button1_3 = new ClickButton();
        button1_3.setName("微信相册");
        button1_3.setType("pic_weixin");
        button1_3.setKey("1_3");

        Button button1 = new Button();
        button1.setName("发送图片");
        button1.setSub_button(new Button[]{button1_1,button1_2,button1_3});

        ViewButton button2 = new ViewButton();
        button2.setName("view菜单");
        button2.setType("view");
        button2.setUrl("http://www.imooc.com");

        ClickButton button3_1 = new ClickButton();
        button3_1.setName("扫码事件");
        button3_1.setType("scancode_push");
        button3_1.setKey("3_1");

        ClickButton button3_2 = new ClickButton();
        button3_2.setName("地理位置");
        button3_2.setType("location_select");
        button3_2.setKey("3_2");

//        ClickButton button3_3 = new ClickButton();
//        button3_3.setName("素材");
//        button3_3.setType("media_id");
//        button3_3.setMedia_id("FJaPv19dW7BwbqHThIfpMSqL0dfR_g9sSWQbLmrfFM9NAQSc_j_QnfkQypgIqcCW");

        Button button3 = new Button();
        button3.setName("菜单");
        button3.setSub_button(new Button[]{button3_1,button3_2});

        menu.setButton(new Button[]{button1,button2,button3});
        return menu;
    }

    public static int createMenu(String token,String menu) throws ParseException, IOException{
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = HttpGetPost.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInteger("errcode");
        }
        return result;
    }

    public static JSONObject queryMenu(String token) throws ParseException, IOException{
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = HttpGetPost.doGetStr(url);
        return jsonObject;
    }

    public static int deleteMenu(String token) throws ParseException, IOException{
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = HttpGetPost.doGetStr(url);
        int result = 0;
        if(jsonObject != null){
            result = jsonObject.getInteger("errcode");
        }
        return result;
    }

}
