package com.zjf.wexin.request;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yujin
 */
@Data
@Component
public class ItaokeRequestBaseParam {
    private String app_key="1033361545";
    private String v = "1.0";
    private String format = "json";
    private String sign_method = "md5";
    private String method;
    private Long timestamp;
    private String domain="http://zjfzwx.vaiwan.com";
    private String client;
    private String partner_id = "top-sdk-php-20190618";
}
