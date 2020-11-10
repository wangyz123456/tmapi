package com.example.tmapi.email;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.example.tmapi.utils.DataUtil;
import com.taobao.api.ApiException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;



@Repository
@EnableScheduling
public class RobotSendServiceImpl {

   public   void sendMsg(String path,String title,String top1,String serverUrl) throws ApiException {
        path=path.substring(path.lastIndexOf("\\")+1);
        DingTalkClient client = new DefaultDingTalkClient(serverUrl);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//      isAtAll类型如果不为Boolean，请升级至最新SDK
        at.setIsAtAll(true);
        request.setAt(at);

//       request.setMsgtype("link");
//       OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
//       link.setMessageUrl("https://www.dingtalk.com/");
//       link.setPicUrl(path);
//       link.setTitle("时代的火车向前开");
//       link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
//       request.setLink(link);

       request.setMsgtype("markdown");
       OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
       markdown.setTitle(title);
       markdown.setText("#### "+title+" \n" +
               "> "+top1+"\n" +
               "> ![screenshot](http://www.xttetbh.com/ribaobiao/dailyPic/"+path+")\n"  +
               "> ###### "+ DataUtil.getNow() +"  \n");
       request.setMarkdown(markdown);
       OapiRobotSendResponse response = client.execute(request);



   }


    public   void sendMsg(String str,String serverUrl) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(serverUrl);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(str);
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);

        OapiRobotSendResponse response = client.execute(request);

    }
}
