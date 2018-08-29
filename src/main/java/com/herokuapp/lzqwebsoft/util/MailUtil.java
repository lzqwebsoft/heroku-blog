package com.herokuapp.lzqwebsoft.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 用于发送邮件的公共类
 *
 * @author johnny
 */
public class MailUtil {

    private static Properties mail_config = new Properties();

    static {
        // 加载Email配置文件
        InputStream in = MailUtil.class.getResourceAsStream("/mail-config.properties");
        try {
            mail_config.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件给指定的用户
     *
     * @param to      收件人邮件地址
     * @param subject 邮件主题
     * @param content 邮件内容，支持HTML
     * @return 返回是否发送成历
     */
    public static boolean sendEMail(String to, String subject, String content) {
        // 创建邮件会话
        Session session = Session.getInstance(mail_config);
        // 是否开始调试状态
        session.setDebug(Boolean.valueOf(mail_config.getProperty("mail.isDebug", "false")));
        // 创建消息
        MimeMessage message = new MimeMessage(session);

        try {
            //设置自定义发件人昵称  
            String nick = MimeUtility.encodeText("Heroku-Blog");
            StringBuffer form_str = new StringBuffer(nick)
                    .append("<").append(mail_config.getProperty("mail.address.from")).append(">");
            InternetAddress from = new InternetAddress(form_str.toString());
            message.setFrom(from);

            InternetAddress to_mail = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, to_mail);
            message.setSubject(subject, "UTF-8");
            message.setSentDate(new Date());

            BodyPart mdp = new MimeBodyPart();
            mdp.setContent(content, "text/html;charset=utf-8");

            Multipart mm = new MimeMultipart();
            mm.addBodyPart(mdp);

            message.setContent(mm);

            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(mail_config.getProperty("mail.smtp.host"),
                    mail_config.getProperty("mail.address.username"),
                    mail_config.getProperty("mail.address.password"));
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
