package com.example.tmapi.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Repository
public class EmailServiceImp implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中发送人的qq邮箱
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 简单文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        System.out.println(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }


    /**
     * html邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            //日志信息
            logger.info("邮件已经发送。");
            System.out.println("");
        } catch (MessagingException e) {
            logger.error("邮件发送失败！", e);
        }
    }

    /**
     * 带附件的邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param filePath 附件
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            //新建文件
            File file=new File(filePath);
            //获取文件路径
            String path=file.getPath();
            //获取文件名  如 F:/picture/img1.jpg 则为img1.jpg
            String fileName=path.substring(path.lastIndexOf("/")+1);
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            logger.info("邮件发送成功");
            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            logger.error("邮件发送失败！", e);
        }

    }


    /**
     * 发送纯单个或多个带附件邮件
     * @param str
     * @param subject
     * @param content
     * @param fileNames
     * @return
     */
    public  boolean sendAttachmentMail(String str, String subject, String content, String[] fileNames) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            /*helper.setTo(to);*/
            helper.setSubject(subject);
            helper.setText(content);
            helper.setFrom(from);
            //发送给多人 这里是我的实体类 可以改为对应的邮箱集合
            String[] To = str.split(";");
            for (String to : To){
                mimeMessage.addRecipients(Message.RecipientType.TO, to);
            }
            //读取附件文件（传入文件路径）遍历文件数组，实现多个附件的添加
            if(fileNames.length>0){
                for (Object string : fileNames) {
                    FileSystemResource file = new FileSystemResource(string.toString());
                    String fileName = file.getFilename();
                    helper.addAttachment(fileName, file);
                }

                try {
                    mailSender.send(mimeMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }else {
                return false;
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            //捕获到创建MimeMessageHelper的异常
        }
        return true;
    }



}
