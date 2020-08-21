package com.zjf.wexin.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author yujin
 * @Title: SignMD5Util
 * @Description: 签名相关工具类
 * @Version V1.0
 */
public class SignMD5Util {

    /**
     * 获取签名的util
     *
     * @param map       请求参数
     * @param secretKey 密钥
     * @return
     */
    public static String getSignStr(TreeMap<String, Object> map, String secretKey) {

        if (map.size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer("");

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append("&" + key + "=" + map.get(key));
        }
        sb.deleteCharAt(0);
        return sign(sb.toString(), secretKey);
    }
    public static String getSignStr(Map<String, Object> map) {

        if (map.size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer("");

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append("&" + key + "=" + map.get(key));
        }
        sb.deleteCharAt(0);
        return sign(sb.toString());
    }
    public static String getItaokeSignStr(Map<String, Object> map, String appSecret) {

        if (map.size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(appSecret);
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append( key + map.get(key));
        }
//        sb.deleteCharAt(0);
        sb.append(appSecret);
        return sign(sb.toString());
    }
    public static String sign(String content, String key) {
        String signStr = "";
        signStr = content + "&key=" + key;
        //MD5加密后，字符串所有字符转换为大写
        return MD5(signStr).toUpperCase();
    }
    public static String sign(String content) {
        String signStr = "";
        signStr = content;
        //MD5加密后，字符串所有字符转换为大写
        return MD5(signStr).toUpperCase();
    }
    /**
     * MD5加密算法
     *
     * @param s
     * @return
     * @see [类、类#方法、类#成员]
     */
    public final static String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes(StandardCharsets.UTF_8);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 对象装换map
     * @param obj
     * @return
     */
    public static TreeMap<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        TreeMap<String, Object> map = new TreeMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class") && !key.equals("pageNo") && !key.equals("pageSize")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

}

