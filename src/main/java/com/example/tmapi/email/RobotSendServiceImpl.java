package com.example.tmapi.email;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.example.tmapi.utils.DataUtil;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;



@Repository
@EnableScheduling
public class RobotSendServiceImpl {
    @Value("${serverUrl}")
    private String serverUrl;
   public   void sendMsg(String path,String title,String top1) throws ApiException {
       path=path.substring(path.lastIndexOf("\\")+1);
             String str = "https://oapi.dingtalk.com/robot/send?access_token=15f87cada1b36af4ba9d5637d10c40961cdaac24ef84eb2d77f036bdcf0e61a3";
       // String str = "https://oapi.dingtalk.com/robot/send?access_token=08e98f3bfd68ecf2cff5313aa4ab862b2e1c335d0c3ba8959d0c695f3d626ca3";
        DingTalkClient client = new DefaultDingTalkClient(str);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
//       request.setMsgtype("text");
//       OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//       text.setContent("测试文本消息");
//       request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//       at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
// isAtAll类型如果不为Boolean，请升级至最新SDK
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

}
