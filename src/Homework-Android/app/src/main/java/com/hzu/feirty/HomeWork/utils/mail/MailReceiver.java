/**
 * 邮件信息解析类
 * @return 序列化的对象
 */
package com.hzu.feirty.HomeWork.utils.mail;
import com.hzu.feirty.HomeWork.utils.TranCharset;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

public class MailReceiver implements Serializable {
    private static final long serialVersionUID = 1L;
    private MimeMessage mimeMessage = null;
    private StringBuffer mailContent = new StringBuffer();// 邮件内容
    private String dataFormat = "yyyy-MM-dd HH:mm:ss";
    private String charset;
    private boolean html;
    private String saveAttachPath = "/temp";// 附件下载后的存放目录
    private StringBuffer bodytext = new StringBuffer();
    // 存放邮件内容的StringBuffer对象
    private String dateformat = "yy-MM-dd　HH:mm";// 默认的日前显示格式
    private ArrayList<String> attachments = new ArrayList<String>();
    private ArrayList<InputStream> attachmentsInputStreams = new ArrayList<InputStream>();

    public MailReceiver(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
        try {
            charset = parseCharset(mimeMessage.getContentType());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<InputStream> getInputStream(){
        return attachmentsInputStreams;
    }

    /**
     * 获得送信人的姓名和邮件地址
     * 
     * @throws Exception
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String addr = address[0].getAddress();
        String name = address[0].getPersonal();
        if (addr == null) {
            addr = "";
        }
        if (name == null) {
            name = "";
        } else if (charset == null) {
            name = TranCharset.TranEncodeTOGB(name);
        }
        String nameAddr = name + "<" + addr + ">";
        return nameAddr;
    }
    /**
     * 根据类型，获取邮件地址 "TO"--收件人地址 "CC"--抄送人地址 "BCC"--密送人地址
     * 
     * @throws Exception
     */
    public String getMailAddress(String type) throws Exception {
        String mailAddr = "";
        String addType = type.toUpperCase(Locale.CHINA);
        InternetAddress[] address = null;
        if (addType.equals("TO")) {
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
        } else if (addType.equals("CC")) {
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
        } else if (addType.equals("BCC")) {
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
        } else {
            System.out.println("error type!");
            throw new Exception("Error emailaddr type!");
        }
        if (address != null) {
            for (int i = 0; i < address.length; i++) {
                String mailaddress = address[i].getAddress();
                if (mailaddress != null) {
                    mailaddress = MimeUtility.decodeText(mailaddress);
                } else {
                    mailaddress = "";
                }
                String name = address[i].getPersonal();
                if (name != null) {
                    name = MimeUtility.decodeText(name);
                } else {
                    name = "";
                }
                mailAddr = name + "<" + mailaddress + ">";
            }
        }
        return mailAddr;
    }
    /**
     * 取得邮件标题
     * 
     * @return String
     */
    public String getSubject() {
        String subject = "";
        try {
            subject = mimeMessage.getSubject();
            if (subject.indexOf("=?gb18030?") != -1) {
                subject = subject.replace("gb18030", "gb2312");
            }
            subject = MimeUtility.decodeText(subject);
            if (charset == null) {
                subject = TranCharset.TranEncodeTOGB(subject);
                }
        } catch (Exception e) {
        }
        return subject;
    }

    /**
     * 取得邮件日期
     * 
     * @throws MessagingException
     */
    public String getSentData() throws MessagingException {
        Date sentdata = mimeMessage.getSentDate();
        if (sentdata != null) {
            SimpleDateFormat format = new SimpleDateFormat(dataFormat, Locale.CHINA);
            return format.format(sentdata);
        } else {
            return "未知";
        }
    }

    /**
     * 取得邮件内容
     * 
     * @throws Exception
     */
    public String getMailContent() throws Exception {
        compileMailContent((Part) mimeMessage);
        String content = mailContent.toString();
        if (content.indexOf("<html>") != -1) {
            html = true;
        }
        mailContent.setLength(0);
        return content;
    }

    public void setMailContent(StringBuffer mailContent) {
        this.mailContent = mailContent;
    }

    /**
     * 是否有回执
     * 
     * @throws MessagingException
     */
    public boolean getReplySign() throws MessagingException {
        boolean replySign = false;
        String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replySign = true;
        }
        return replySign;
    }

    /**
     * 取得「message-ID」
     * 
     * @throws MessagingException
     */
    public String getMessageID() throws MessagingException {
        return mimeMessage.getMessageID();
    }

    /**
     * 是否新邮件
     * 
     * @throws MessagingException
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isnew = true;
                break;
            }
        }
        return isnew;
    }

    public String getCharset() {
        return charset;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public boolean isHtml() {
        return html;
    }

    public ArrayList<InputStream> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }

    /**
     * 解析邮件内容
     * 
     * @param part
     * @throws Exception
     */
    private void compileMailContent(Part part) throws Exception {
        String contentType = part.getContentType();
        // Log.v("content type", "[" + contentType.replace("\n", "") + "]" + "["
        // + part.getContent() + "]");
        boolean connName = false;
        if (contentType.indexOf("name") != -1) {
            connName = true;
        }
        if (part.isMimeType("text/plain") && !connName) {
            //String content = parseInputStream((InputStream) part.getContent());
            //mailContent.append(content);
            mailContent.append((String)part.getContent());
        } else if (part.isMimeType("text/html") && !connName) {
            html = true;
            //这里修改
            //String content1 = parseInputStream((InputStream) part.getContent());
            //mailContent.append(content1);
            mailContent.append((String)part.getContent());
        } else if (part.isMimeType("multipart/*") || part.isMimeType("message/rfc822")) {
            if (part.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) part.getContent();
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    compileMailContent(multipart.getBodyPart(i));
                }
            } else {
                Multipart multipart = new MimeMultipart(new ByteArrayDataSource(part.getInputStream(), "multipart/*"));
                int counts = multipart.getCount();
                for (int i = 0; i < counts; i++) {
                    compileMailContent(multipart.getBodyPart(i));
                }
            }
        } else if (part.getDisposition() != null && part.getDisposition().equals(Part.ATTACHMENT)) {
            // 获取附件
            String filename = part.getFileName();
            if (filename != null) {
                if (filename.indexOf("=?gb18030?") != -1) {
                    filename = filename.replace("gb18030", "gb2312");
                }
                filename = MimeUtility.decodeText(filename);
                attachments.add(filename);
                //这里修改
                attachmentsInputStreams.add(part.getInputStream());
                //saveFile(filename,part.getInputStream());
                //saveFile(filename,part.getInputStream());
            }
            // Log.e("content", "附件：" + filename);
        }
    }

    /**
     * 解析字符集编码
     * 
     * @param contentType
     * @return
     */
    private String parseCharset(String contentType) {
        if (!contentType.contains("charset")) {
            return null;
        }
        if (contentType.contains("gbk")) {
            return "GBK";
        } else if (contentType.contains("GB2312") || contentType.contains("gb18030")) {
            return "gb2312";
        } else {
            String sub = contentType.substring(contentType.indexOf("charset") + 8).replace("\"", "");
            if (sub.contains(";")) {
                return sub.substring(0, sub.indexOf(";"));
            } else {
                return sub;
            }
        }
    }

    /**
     * 解析流格式
     * 
     * @param
     * @param
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    private String parseInputStream(InputStream is) throws IOException, MessagingException {
        StringBuffer str = new StringBuffer();
        byte[] readByte = new byte[1024];
        int count;
        try {
            while ((count = is.read(readByte)) != -1) {
                if (charset == null) {
                    str.append(new String(readByte, 0, count, "GBK"));
                } else {
                    str.append(new String(readByte, 0, count, charset));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    /**
     *
     * 判断此邮件是否包含附件
     *
     */

    public boolean isContainAttach(Part part) throws Exception {
        boolean attachflag = false;
        String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachflag = true;
                else if (mpart.isMimeType("multipart/*")) {
                    attachflag = isContainAttach((Part) mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1)
                        attachflag = true;
                    if (contype.toLowerCase().indexOf("name") != -1)
                        attachflag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachflag = isContainAttach((Part) part.getContent());
        }
        return attachflag;
    }
  /* //base64解码
    private static String base64Decoder(String s) throws Exception {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] b = decoder.decodeBuffer(s);
        return (new String(b));
    }*/


    /**
    * 保存附件
    *
    */
    /*public String saveAttachMent(Part part) throws Exception {
        String fileName = "";
        if (part.isMimeType("multipart*//*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE)))) {
                    fileName = mpart.getFileName();
                    //String s = fileName.substring(8, fileName.indexOf("?="));
                    //fileName = base64Decoder(s);
                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
*//*                    if(fileName.equals("学号.txt")){
                        String path = new IOUtil().stream2file(mpart.getInputStream(), Environment.getExternalStorageDirectory().toString() + "/temp/" + fileName);
                        if (path != null) {
                            Map<Integer, String> map = new IOUtil().Txt(path);
                            Set set = map.keySet();
                            for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                                String key = (String) iter.next();
                                String value = map.get(key);
                                Student stu =new Student();
                                stu.setNumber(value);
                                new StudentDao().insert(student);
                            }
                        }
                    }else{
                    String path = new IOUtil().stream2file(mpart.getInputStream(), Environment.getExternalStorageDirectory().toString() + "/temp/" + fileName);
                    }*//*
                    //saveFile(fileName, mpart.getInputStream());
                    String path = new IOUtil().stream2file(mpart.getInputStream(), Environment.getExternalStorageDirectory().toString() + "/temp/" + fileName);
                } else if (mpart.isMimeType("multipart*//*")) {
                    saveAttachMent(mpart);
                } else {
                    fileName = mpart.getFileName();
                    if ((fileName != null)
                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        //saveFile(fileName, mpart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }
        return fileName;
    }*/

    /**
     * 【设置附件存放路径】
     */

    public void setAttachPath(String attachpath) {
        this.saveAttachPath = attachpath;
    }

    /**
     *
     * 设置日期显示本格式
     */
    public void setDateFormat(String format) throws Exception {
        this.dateformat = format;
    }

    /**
     * 【获得附件存放路径】
     */

    public String getAttachPath() {
        return saveAttachPath;
    }

    /**
     * 【真正的保存附件到指定目录里】
     */

/*    private void saveFile(String fileName, InputStream in) throws Exception {

        String osName = System.getProperty("os.name");
        String storedir = getAttachPath();
        String separator = "";
        if (osName == null)
            osName = "";
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storedir == null || storedir.equals(""))
                storedir = "/";
        } else {
            separator = "/";
            storedir = Environment.getExternalStorageDirectory().toString() + "/temp/";
        }
        File storefile = new File(storedir + fileName);
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
    }*/

}
