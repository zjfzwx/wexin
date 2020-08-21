package com.zjf.wexin.request;

import lombok.Data;

/**
 * @author yujin
 */
@Data
public class WxCallBackParam {
    private String wcId;
    private String account;
    private Integer messageType;
    private MessageData data;

    @lombok.Data
    public static class MessageData{
        private String fromUser;
        private String fromGroup;
        private String toUser;
        private Long msgId;
        private Long newMsgId;
        private Integer msgType;
        private Long timestamp;
        private String content;
        private Boolean self;
    }
}
