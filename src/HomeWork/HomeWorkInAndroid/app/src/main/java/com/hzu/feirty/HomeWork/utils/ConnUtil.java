package com.hzu.feirty.HomeWork.utils;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class ConnUtil {
    private static Store store = null;
    public static Store login(String host, String user, String password) {
        Properties props =  System.getProperties();
        Session session = Session.getDefaultInstance(props);
        try {
            props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.starttls.enable","true");
            props.setProperty("mail.pop3.port", "995");
            props.setProperty("mail.pop3.socketFactory.port", "995");
            store = session.getStore("pop3");
            store.connect(host, user, password);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return store;
    }
}
