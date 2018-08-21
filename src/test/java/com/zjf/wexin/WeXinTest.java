package com.zjf.wexin;

import com.alibaba.fastjson.JSONObject;
import com.zjf.wexin.po.AccessToken;
import com.zjf.wexin.util.Translate;
import com.zjf.wexin.util.UploadMaterial;
import com.zjf.wexin.util.WeixinUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WeXinTest {
    @Test
    public void testToken() throws IOException {
        AccessToken token= WeixinUtil.getAccessToken();
       System.out.println(token.getToken());
       System.out.println(token.getExpiresIn() );
        String path="E:\\wexin1\\src\\main\\resources\\static\\images\\1.jpg";
        try {
            String mediaId= UploadMaterial.upload(path,token.getToken(),"image");
            System.out.println(mediaId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void createMeun() throws IOException {
        AccessToken token= WeixinUtil.getAccessToken();
        String meun= JSONObject.toJSONString(WeixinUtil.initMenu());
        int result=WeixinUtil.createMenu(token.getToken(),meun);
        if (result==0){
            System.out.println("创建菜单成功");
        }else {
            System.out.println("错误码:"+result);
        }
    }
    @Test
    public void queryMeun() throws IOException {
        AccessToken token= WeixinUtil.getAccessToken();
        String meun= JSONObject.toJSONString(WeixinUtil.queryMenu(token.getToken()));
        System.out.println(meun);
    }
    @Test
    public void deleteMeun() throws IOException {
        AccessToken token= WeixinUtil.getAccessToken();
        int result= WeixinUtil.deleteMenu(token.getToken());
        if (result==0){
            System.out.println("菜单删除成功!");
        }else {
            System.out.println(result);
        }
    }
    @Test
    public void translate(){
        String result = null;
        try {
            result = Translate.translate("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
