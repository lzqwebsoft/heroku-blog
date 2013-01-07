package com.herokuapp.lzqwebsoft.util;

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
 * @author johnny
 *
 */
public class MailUtil {
    public static void sendEMail(String to, String subject, String content) {
        Properties props = new Properties();
        
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        
        Session session = Session.getInstance(props);
        session.setDebug(true);
        
        MimeMessage message = new MimeMessage(session);
        
        try {
            //设置自定义发件人昵称  
            String nick = MimeUtility.encodeText("Heroku-Blog");
            InternetAddress from = new InternetAddress(nick+"<lzqwebsoft@gmail.com>");
            message.setFrom(from);
            
            InternetAddress to_mail = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, to_mail);
            message.setSubject(subject);
            message.setSentDate(new Date());
            
            BodyPart mdp = new MimeBodyPart();
            mdp.setContent(content, "text/html;charset=utf-8");
            
            Multipart mm = new MimeMultipart();
            mm.addBodyPart(mdp);
            
            message.setContent(mm);
            
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "lzqwebsoft@gmail.com", "*********");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        MailUtil.sendEMail("751939573@qq.com", "google邮件测试", "<p style='color:red;'>测试</p>");
    }
}
