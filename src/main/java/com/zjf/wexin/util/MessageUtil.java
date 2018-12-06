package com.zjf.wexin.util;

import com.thoughtworks.xstream.XStream;
import com.zjf.wexin.po.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_NEWS="news";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_MUSIC="music";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_VIDEO="video";
    public static final String MESSAGE_LINK="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVNET="event";
    public static final String MESSAGE_SUBSCRIBE="subscribe";
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
    public static final String MESSAGE_CLICK="click";
    public static final String MESSAGE_VIEW="view";
    public static final String MESSAGE_SCANCODE= "scancode_push";
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map=new HashMap<>();
        SAXReader reader=new SAXReader();

        InputStream ins=request.getInputStream();
        Document doc=reader.read(ins);

        Element root = doc.getRootElement();
        List<Element> list=root.elements();

        for (Element e:list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }

    public static String  textMessageToXml(TextMessage textMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",textMessage.getClass());
       return xStream.toXML(textMessage);

    }
    public static String  newsMessageToXml(NewsMessage newsMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item",new News().getClass());
        return xStream.toXML(newsMessage);
    }
    public static String  imageMessageToXml(ImageMessage imageMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",imageMessage.getClass());
        return xStream.toXML(imageMessage);

    }
    /**
     * 音乐消息转为xml
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }
    public static String initText(String toUserName,String formUserName,String content){
        TextMessage textMessage=new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(formUserName);
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        textMessage.setContent(content);
        return MessageUtil.textMessageToXml(textMessage);
    }
    public static String initNews(String toUserName,String formUserName){
        News news=new News();
        news.setTitle("自己");
        news.setDescription("年轻时候的帅照");
        news.setPicUrl("http://pnrbge.natappfree.cc/images/1.jpg");
        news.setUrl("www.baidu.com");
        List<News> newsList=new ArrayList<>();
        newsList.add(news);
        NewsMessage newsMessage=new NewsMessage();
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(formUserName);
        newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
        newsMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        newsMessage.setArticleCount(newsList.size());
        newsMessage.setArticles(newsList);
        return MessageUtil.newsMessageToXml(newsMessage);
    }
    /**
     * 组装图片消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        String message = null;
        Image image = new Image();
        image.setMediaId("nYH6jc1tQvbmQ2H_PLkcBA4-6p6vYAtZ48_8LGdKJFfnj_BaeSCDxdmuVBqPIECQ");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        imageMessage.setImage(image);
        message = imageMessageToXml(imageMessage);
        return message;
    }

    /**
     * 组装音乐消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initMusic(String toUserName,String fromUserName){
        String message = null;
        Music music = new Music();
        music.setThumbMediaId("Butn96SmHf_yHvE82yGgJVah9LpQmxiY6fFmgteCwe5H9zpJg9n0CAgLHtADgVYc");
        music.setTitle("see you again");
        music.setDescription("速7片尾曲");
        music.setMusicUrl("http://95jwf3.natappfree.cc/music/1.mp3");
        music.setHQMusicUrl("http://95jwf3.natappfree.cc/music/1.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        musicMessage.setMusic(music);
        message = musicMessageToXml(musicMessage);
        return message;
    }
    public static String first(){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("本公众号完全由开发者自主开发，仅供娱乐,本公众号提供翻译,讲笑话以及一些基础功能!后续功能正在完善，欢迎" +
                "提建议。\n");
        return stringBuffer.toString();
    }
    public static String mainMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，本公众号提供智能机器人对话与讲笑话服务！\n");
        sb.append("1、例：讲个笑话\n");
        sb.append("2、或者回复任意内容\n");
//        sb.append("2、图文介绍\n");
//        sb.append("3、获取图片\n");
//        sb.append("4、获取音乐\n");
//        sb.append("5、翻译功能介绍\n");
//        sb.append("6、讲笑话功能介绍\n");
//        sb.append("回复？调出此菜单。");
        return sb.toString();
    }
    public static String fiveMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("词组翻译使用指南\n\n");
        sb.append("使用示例：\n");
        sb.append("翻译足球\n");
        sb.append("翻译中国足球\n");
        sb.append("翻译football\n\n");
        sb.append("回复？显示主菜单。");
        return sb.toString();
    }
    public static String sixMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("回复：讲个笑话，即可获取\n\n");
        return sb.toString();
    }
}

