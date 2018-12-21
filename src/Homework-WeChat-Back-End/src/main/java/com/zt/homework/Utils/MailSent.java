package com.zt.homework.Utils;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;

public class MailSent {

    /**
     * 发送携带附件的邮件
     * @param mailTo
     * @param mailFrom
     * @param mailPwd
     * @param subject
     * @param content
     * @param attachFilePath
     * @param attachFileName
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static void sendMailWithAttach(String mailTo, String mailFrom, String mailPwd, String subject, String content, String attachFilePath, String attachFileName) throws MessagingException, UnsupportedEncodingException {
        // 解決找不到文件类型的错误
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822");

        String host = ConnUtill.getSMTPHost(mailFrom);
        Session session = ConnUtill.smtpConnect(host, mailFrom, mailPwd);

        Message message = new MimeMessage(session);

        // 设置发件人
        message.setFrom(new InternetAddress(mailFrom));
        // 设置收件人
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
        // 设置主题
        message.setSubject(subject);

        // create message part
        BodyPart messageBodyPart = new MimeBodyPart();
        // 设置邮件内容
        messageBodyPart.setContent(content, "text/html;charset=UTF-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // 添加附件
        if(!attachFilePath.equals("")) {
            BodyPart attachBodyPart = new MimeBodyPart();
            FileDataSource source = new FileDataSource(attachFilePath);
            attachBodyPart.setDataHandler(new DataHandler(source));
            attachBodyPart.setFileName(MimeUtility.encodeText(attachFileName));

            multipart.addBodyPart(attachBodyPart);
        }

        message.setContent(multipart);

        // 发送邮件
        message.saveChanges(); // implicit with send()
//Get transport for session
        Transport transport = session.getTransport("smtp");
//Connect
        transport.connect(host, mailFrom, mailPwd);
//repeat if necessary
        transport.sendMessage(message, message.getAllRecipients());
//Done, close the connection
        transport.close();
    }

}
