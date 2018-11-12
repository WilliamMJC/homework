package com.zt.homework.Utils;

import javax.mail.*;
import java.util.Properties;

public class ConnUtill {
    /**
     * 获取邮箱popHost
     * @param user
     * @return
     */
    public static String getPOP3Host(String user) {
        if (user.contains("qq")) {
            return "pop.qq.com";
        }
        if (user.contains("163")) {
            return "pop.163.com";
        }
        if (user.contains("126")) {
            return "pop.126.com";
        }else
            return null;
    }

    /**
     * 获取邮箱smtpHost
     * @param user
     * @return
     */
    public static String getSMTPHost(String user) {
        if (user.contains("qq")) {
            return "smtp.qq.com";
        }
        if (user.contains("163")) {
            return "smtp.163.com";
        }
        if (user.contains("126")) {
            return "smtp.126.com";
        } else {
            return null;
        }
    }

    /**
     * 创建smtp连接
     * @param host
     * @param user
     * @param mailPwd
     * @return 连接session
     */
    public static Session smtpConnect(String host, String user, String mailPwd) {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        // 防止编码后附件文件名过长被截断导致出现未命名或者奇怪命名
        System.setProperty("mail.mime.splitlongparameters","false");
        System.setProperty("mail.mime.charset","UTF-8");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, mailPwd);
            }
        });

        return session;
    }

    /**
     * 创建pop连接
     * @param host
     * @param user
     * @param mailPwd
     * @return
     * @throws MessagingException
     */
    public static Store popConnect(String host, String user, String mailPwd) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.pop3.host", host);
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.starttls.enable", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.pop3.socketFactory.port", "995");
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.fallback", "false");
        // 设置连接超时时间以及读超时时间,防止线程堵塞
        props.put("mail.pop3.connectiontimeout", 10 * 1000);
        props.put("mail.pop3.timeout", 5 * 60 * 1000);

        // 防止编码后附件文件名过长被截断导致出现未命名或者奇怪命名
        System.setProperty("mail.mime.splitlongparameters","false");
        System.setProperty("mail.mime.charset","UTF-8");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, mailPwd);
            }
        });

        Store store = session.getStore("pop3");
        store.connect(host, user, mailPwd);

        return store;
    }
}
