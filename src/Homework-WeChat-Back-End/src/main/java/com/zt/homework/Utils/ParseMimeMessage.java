package com.zt.homework.Utils;

import com.sun.mail.util.QPDecoderStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParseMimeMessage {
    @Value("${homeDir}")
    private String homeDir;

    private String dateFormat = "yyyy-MM-dd HH:mm";     // 默认的日期显示格式

    private MimeMessage mimeMessage;

    public ParseMimeMessage() {}

    /**
     * 构造函数,初始化一个MimeMessage对象
     * @param mimeMessage
     */
    public ParseMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 获取邮件ID
     * @return 邮件ID
     * @throws MessagingException
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }

    /**
     * 获取邮件主题
     * @return 邮件主题
     * @throws MessagingException
     */
    public String getSubject() throws MessagingException, UnsupportedEncodingException {
        String subject;
        subject = MimeUtility.decodeText(mimeMessage.getSubject());
        if(subject == null) subject = "";
        return  subject;
    }

    /**
     * 获取发件人邮箱
     * @return String
     * @throws MessagingException
     */
    public String getMailFromString() throws MessagingException {
        String mailFrom = String.valueOf(mimeMessage.getFrom()[0]);
        if(mailFrom.contains("<")) {
            mailFrom = mailFrom.substring(mailFrom.indexOf("<") + 1, mailFrom.length() - 1);
        }
        return mailFrom;
    }

    /**
     * 获取发件人
     * @return Address
     * @throws MessagingException
     */
    public Address getMailFrom() throws MessagingException {
        return mimeMessage.getFrom()[0];
    }

    /**
     * 获取邮件发送日期
     * @return String
     * @throws Exception
     */
    public String getSentDateFormat() throws Exception{
        Date sentDate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(sentDate);
    }

    /**
     * 获取邮件发送日期
     * @return Date
     * @throws Exception
     */
    public Date getSentDate() throws Exception{
        return mimeMessage.getSentDate();
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     * 根据MimeType类型的不同执行不同的操作，一步一步的解析
     * @param part
     * @throws Exception
     */
    public void getMailContent(Part part) throws Exception{
        String contentType = part.getContentType();
        int nameIndex = contentType.indexOf("name");
        boolean connName = false;

        if(nameIndex != -1) connName = true;

        System.out.println("--------------- contentType: ---------------");
        System.out.println(contentType);
        //check if the content is plain text
        if(part.isMimeType("text/plain") && !connName) {
            System.out.println("This is plain text");
            System.out.println("---------------------");

            // 将QPDecoderStream转为string类型
            if(part.getContent() instanceof QPDecoderStream) {
                BufferedInputStream bis = new BufferedInputStream((InputStream) part.getContent());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while (true) {
                    int c = bis.read();
                    if(c == -1) break;
                    bos.write(c);
                }
                System.out.println(new String(bos.toByteArray()));
            }
        }
        // check if the content has attachment
        else if(part.isMimeType("multipart/*")) {
            // 解决java.lang.ClassCastException: javax.mail.util.SharedByteArrayInputStream cannot be cast to javax.mail.Multipart的问题
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart multipart = new MimeMultipart(source);
            int counts = multipart.getCount();
            for(int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        }
        //check if the content is a nested message
        else if(part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        }
        else {
            Object o = part.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------");
                System.out.println((String) o);
            } else {
                System.out.println("This is an unknown type");
                System.out.println("--------------------");
                System.out.println(o.toString());
            }
        }
    }

    /**
     * 判断此邮件是否含有附件
     * @param part
     * @return boolean
     * @throws Exception
     */
    public InputStream isContainAttach(Part part) throws Exception{
        InputStream in = null;
        if (part.isMimeType("multipart/*")) {
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart multipart = new MimeMultipart(source);
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disPosition = bodyPart.getDisposition();
                if((disPosition != null) && ((disPosition.equals(Part.ATTACHMENT)) || (disPosition.equals(Part.INLINE))))
                    in =bodyPart.getInputStream();
                else if(bodyPart.isMimeType("multipart/*"))
                    in = isContainAttach((Part) bodyPart);
                else {
                    String cType = bodyPart.getContentType();
                    if (cType.toLowerCase().contains("application"))
                        in = bodyPart.getInputStream();
                    if(cType.toLowerCase().contains("name"))
                        in = bodyPart.getInputStream();
                }
            }
        } else if(part.isMimeType("message/rfc822"))
            in = isContainAttach((Part) part.getContent());
        return in;
    }

    /**
     * 返回附件名
     * @param part
     * @return
     */
    public String getAttachFileName(Part part) {
        String fileName = "";
        try {
            if(part.isMimeType("multipart/*")) {
                DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
                Multipart multipart = new MimeMultipart(source);
                int counts = multipart.getCount();
                for(int i = 0; i < counts; i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    String disPosition = bodyPart.getDisposition();
                    if((disPosition != null) && ((disPosition.equals(Part.ATTACHMENT)) || (disPosition.equals(Part.INLINE)))) {
                        fileName = bodyPart.getFileName();
                        if(fileName.toLowerCase().contains("gb2312"))
                            fileName = MimeUtility.decodeText(fileName);
                        else if(fileName.toLowerCase().contains("gbk"))
                            fileName = MimeUtility.decodeText(fileName);
                        else if(fileName.toLowerCase().contains("gb18030"))
                            fileName = MimeUtility.decodeText(fileName);
                        else if(fileName.toLowerCase().contains("utf-8"))
                            fileName = MimeUtility.decodeText(fileName);
                    }
                    else if (bodyPart.isMimeType("multipart/*"))
                        getAttachFileName(bodyPart);
                    else {
                        fileName = bodyPart.getFileName();
                        if((fileName != null) && (fileName.toLowerCase().contains("GB2312"))) {
                            fileName = MimeUtility.decodeText(fileName);
                        }
                    }
                }
            }
            else if(part.isMimeType("message/rfc222"))
                getAttachFileName((Part) part.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    /**
     * 获取超大附件链接
     * @param part
     * @return
     * @throws Exception
     */
    public String getBigAttachFileLink(Part part) throws Exception {
        String bigAttach = "";

        if(part.isMimeType("multipart/*")) {
            // 解决java.lang.ClassCastException: javax.mail.util.SharedByteArrayInputStream cannot be cast to javax.mail.Multipart的问题
            DataSource source = new ByteArrayDataSource(part.getInputStream(), "multipart/*");
            Multipart multipart = new MimeMultipart(source);
            int counts = multipart.getCount();
            for(int i = 0; i < counts; i++) {
                String temp = getBigAttachFileLink(multipart.getBodyPart(i));
                if(!temp.equals("")) {
                    bigAttach = temp;
                }
            }
        }
        else {
            Object o = part.getContent();
            if (o instanceof String) {
                String html = (String) o;
                Pattern pattern = Pattern.compile("(downloadlink=\")(?<link>.+?)(\")");
                Matcher matcher = pattern.matcher(html);
                if(matcher.find()) {
                     bigAttach = matcher.group("link");
                }

            }
        }
        return bigAttach;
    }

}
