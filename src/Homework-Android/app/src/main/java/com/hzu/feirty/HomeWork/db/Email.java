/**
 *Email()类实现Email属性的封装
 * 包括属性：
 * --messageID--email的ID，--from--发件地址，--to--收件人地址，--cc--抄送地址，---bcc--密送地址
 * --subject--邮件主题，--sentdata--发送的内容，--content--内容
 * --boolean类型：--replysign--     --html---  ---news--
 * --attachments--字符串数组
 */

package com.hzu.feirty.HomeWork.db;
import android.provider.ContactsContract;
import java.io.Serializable;
import java.util.ArrayList;

public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    private String messageID;
    private String homeworkUuid;
    private ContactsContract.Data publishDate;
    private String from;
    private String to;
    private String cc;
    private String submit_state;
    private String work_state;
    private String bcc;
    private String work_number;
    private String subject;
    private String sentdata;
    private String content;
    private String attachment;
    private boolean replysign;
    private boolean html;
    private boolean news;
    private ArrayList<String> attachments;
    private String course;
    private  String update_time;
    private String userUuid;
    private String userName;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ContactsContract.Data getPublishDate(){
        return publishDate;
    }

    public String getWork_number() {
        return work_number;
    }

    public void setWork_number(String work_number) {
        this.work_number = work_number;
    }
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSentdata() {
        return sentdata;
    }

    public void setSentdata(String sentdata) {
        this.sentdata = sentdata;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReplysign() {
        return replysign;
    }

    public void setReplysign(boolean replysign) {
        this.replysign = replysign;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public ArrayList<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<String> attachments) {
        this.attachments = attachments;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public String getWork_state() {
        return work_state;
    }

    public void setWork_state(String work_state) {
        this.work_state = work_state;
    }

    public String getSubmit_state() {

        return submit_state;
    }
    public void setSubmit_state(String submit_state) {
        this.submit_state = submit_state;
    }

    public String getHomeworkUuid() {
        return homeworkUuid;
    }

    public void setHomeworkUuid(String homeworkUuid) {
        this.homeworkUuid = homeworkUuid;
    }
}
