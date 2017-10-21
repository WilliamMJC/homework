package com.hzu.feirty.HomeWork.utils.mail;
import android.content.Context;
import android.os.Environment;
import com.hzu.feirty.HomeWork.activity.index.MailApplication;
import  com.hzu.feirty.HomeWork.db.Student;
import com.hzu.feirty.HomeWork.utils.IOUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

public class MailHelper {

    private static MailHelper instance;
    private List<MailReceiver> mailList;
    private HashMap<String, Integer> serviceHashMap;
    private ArrayList<InputStream> attachmentsInputStreams;
    private static final String IDENTITY = "saveidentity";
    private Context context;
    private Student student=null;
    public static MailHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MailHelper(context);
        }
        return instance;
    }

    /**
     * 构造函数
     *
     */
    private MailHelper(Context context) {
        this.context = context;
    }

    public String getUpdateUrlStr() throws Exception {
        String urlStr = null;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("update") == 1) {
            urlStr = mailList.get(1).getSubject();
        }
        return urlStr;
    }

    public String getUserHelp() throws Exception {
        String userandmoney = null;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("userhelp") == 1) {
            userandmoney = mailList.get(3).getSubject();
        }
        return userandmoney;
    }

    public int getAllUserHelp() throws Exception {
        String userandmoney = null;
        int money = 0;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("userhelp") == 1) {
            userandmoney = mailList.get(3).getSubject();
        }
        if (userandmoney != null && userandmoney.contains("all-user-100")) {
            money = Integer.parseInt(userandmoney.substring(userandmoney.lastIndexOf("-" + 1),
                    userandmoney.length()));
        }
        return money;
    }

    public boolean getAdControl() throws Exception {
        String ad = null;
        if (serviceHashMap == null) {
            serviceHashMap = this.getServeHashMap();
        }
        if (serviceHashMap.get("adcontrol") == 1) {
            ad = mailList.get(2).getSubject();
        }
        if (ad.equals("ad=close")) {
            return false;
        }
        return true;
    }

    public HashMap<String, Integer> getServeHashMap() throws Exception {
        serviceHashMap = new HashMap<String, Integer>();
        if (mailList == null) {
            mailList = getMailByTeacher("INBOX");
        }
        String serviceStr = mailList.get(0).getSubject();
        if (serviceStr.contains("update 1.0=true")) {
            serviceHashMap.put("update", 1);
        } else if (serviceStr.contains("update 1.0=false")) {
            serviceHashMap.put("update", 0);
        }
        if (serviceStr.contains("adcontrol 1.0=true")) {
            serviceHashMap.put("adcontrol", 1);
        } else if (serviceStr.contains("adcontrol 1.0=false")) {
            serviceHashMap.put("adcontrol", 0);
        }
        if (serviceStr.contains("userhelp 1.0=true")) {
            serviceHashMap.put("userhelp", 1);
        } else if (serviceStr.contains("userhelp 1.0=false")) {
            serviceHashMap.put("userhelp", 0);
        }
        return serviceHashMap;
    }

    /**
     * 取得所有的邮件
     * 
     * @param folderName 文件夹名，例：收件箱是"INBOX"
     * @return　List<MailReceiver> 放有ReciveMail对象的List
     * @throws MessagingException
     */
    /**
     *学号收集类
     *
     */
    public List<MailReceiver> getStudentId(String folderName) throws Exception {
        List<MailReceiver> mailList = new ArrayList<MailReceiver>();
        // 连接服务器
        Store store = ((MailApplication) context.getApplicationContext()).getStore();
        // 打开文件夹
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);
        // 总的邮件数
        int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
            for ( int i = 0; i < messages.length; i++) {
                MailReceiver reciveMail = new MailReceiver((MimeMessage) messages[i]);
                if (reciveMail.getSubject().equals("学号")){
                    if (reciveMail.isContainAttach(messages[i])) {
                        reciveMail.getMailContent();
                        attachmentsInputStreams = reciveMail.getAttachmentsInputStreams();
                        if (attachmentsInputStreams != null) {
                            InputStream is = attachmentsInputStreams.get(0);
                            new IOUtil().stream2file(is, Environment.getExternalStorageDirectory().toString() + "/temp/" + reciveMail.getAttachments().get(0));
                            IOUtil.Txt(Environment.getExternalStorageDirectory().toString() + "/temp/" + reciveMail.getAttachments().get(0));
                        }
                    }
                }else if(reciveMail.getSubject().equals("密码")){
                    if (reciveMail.isContainAttach(messages[i])) {
                        reciveMail.getMailContent();
                        attachmentsInputStreams = reciveMail.getAttachmentsInputStreams();
                        if (attachmentsInputStreams != null) {
                            InputStream is = attachmentsInputStreams.get(0);
                            new IOUtil().stream2file(is, Environment.getExternalStorageDirectory().toString() + "/temp/" + reciveMail.getAttachments().get(0));
                            IOUtil.Txt2(Environment.getExternalStorageDirectory().toString() + "/temp/" + reciveMail.getAttachments().get(0));
                        }
                    }
                }

            }
            store.close();
            return null;
        }
    }
    /**
     *教师邮件方法
     **
     **
     */
    public List<MailReceiver> getMailByTeacher(String folderName) throws Exception {
        List<MailReceiver> mailList = new ArrayList<MailReceiver>();
        // 连接服务器
        Store store = ((MailApplication) context.getApplicationContext()).getStore();
        // 打开文件夹
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);
        // 总的邮件数
        int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
            for ( int i = 0; i < messages.length; i++) {
                MailReceiver reciveMail = new MailReceiver((MimeMessage) messages[i]);
                if(reciveMail.getSubject().contains("[布置作业]")||reciveMail.getSubject().contains("[作业]")){
                    if (reciveMail.isContainAttach(messages[i])) {
                        reciveMail.getMailContent();
                        attachmentsInputStreams = reciveMail.getAttachmentsInputStreams();
                        if (attachmentsInputStreams != null) {
                            InputStream is = attachmentsInputStreams.get(0);
                            new IOUtil().stream2file(is, Environment.getExternalStorageDirectory().toString() + "/temp/" + reciveMail.getAttachments().get(0));
                        }
                    }
                    mailList.add(reciveMail);// 添加到邮件列表中
                }
            }
        }
        return mailList;
    }
    /**
     *学生邮件方法
     **
     **
     */
    public List<MailReceiver> getMailByStudent(String folderName) throws Exception {
        List<MailReceiver> mailList = new ArrayList<MailReceiver>();
        // 连接服务器
        Store store = ((MailApplication) context.getApplicationContext()).getStore();
        // 打开文件夹
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);
        // 总的邮件数
        int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
            for ( int i = 0; i < messages.length; i++) {
                MailReceiver reciveMail = new MailReceiver((MimeMessage) messages[i]);
                if(reciveMail.getSubject().contains("[布置作业]")||reciveMail.getSubject().contains("[作业]")){
                    mailList.add(reciveMail);// 添加到邮件列表中
                }
            }
        }
        return mailList;
    }

}
