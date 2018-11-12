package com.hzu.homework.util;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;


public class MailSendUtil {
	private String host;
    private String username;
    private String password;
    /**
     * ���췽��
     *
     * @param host ���������
     * @param username �����û�ȫ�������磺test@qq.com
     * @param password �����û�����
     */
    public MailSendUtil(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * �����ʼ�
     * @param hostname 
     * @param mailTo �ռ���email��ַ
     * @param mailSubject �ʼ�����
     * @param mailBody �ʼ�����
     * @throws Exception
     */
    public void send(String hostname,String mailTo, String mailSubject, String mailBody) throws Exception {
    	// Create the attachment
    	EmailAttachment attachment = new EmailAttachment();
    	//������ַ
    	//attachment.setPath(attachments);
    	attachment.setDisposition(EmailAttachment.ATTACHMENT);
    	//����˵��
    	//attachment.setDescription("Picture of John");
    	//��������
    	//������Ҫ�����ļ��ĺ�׺��
        //Integer a = attachments.indexOf("\\");
       // String aftera = attachments.substring(a+5);
    	//attachment.setName(MimeUtility.encodeText(aftera));
    	// Create the email message
    	MultiPartEmail email = new MultiPartEmail();
    	//�ʼ�������
    	email.setHostName(hostname);
    	//�˿ں�
    	email.setSmtpPort(25);
    	//�û���������
    	email.setAuthenticator(new DefaultAuthenticator(username, password));
    	//email.setSSLOnConnect(true);
    	email.setSSL(true);
    	try {
    	//�ռ���
    	email.addTo(mailTo, "��ʦ");
    	//������
    	email.setFrom(username, "�շ���ҵ");
    	//����
    	email.setSubject(mailSubject);
    	//����
    	email.setMsg(mailBody);
    	// add the attachment
    	//email.attach(null);
    	// send the email
    	email.send();
    	} catch (EmailException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    	}
    }

    /**
     * �����ʼ�
     *
     * @param email �ʼ�����
     * @throws Exception
     */
    /*public void send(Email email) throws Exception {
        //send(email.getTo(), email.getSubject(), email.getContent(), email.getAttachments(), null);
    }*/

    /**
     * �����ʼ�
     *
     * @param mailTo �ռ���email��ַ
     * @param mailSubject �ʼ�����
     * @param mailBody �ʼ�����
     * @param attachments ����
     * @param personalName �ʼ���ʾ�ķ���������
     * @throws Exception
     */
    public void send(String mailTo, String mailSubject, String mailBody, String attachments,
                     String personalName) throws Exception {   	
    	// TODO Auto-generated method stub
    	// Create the attachment
    	EmailAttachment attachment = new EmailAttachment();
    	//������ַ
    	attachment.setPath(attachments);
    	attachment.setDisposition(EmailAttachment.ATTACHMENT);
    	//����˵��
    	//attachment.setDescription("Picture of John");
    	//��������
    	//������Ҫ�����ļ��ĺ�׺��
        Integer a = attachments.indexOf("\\");
        String aftera = attachments.substring(a+5);
    	attachment.setName(MimeUtility.encodeText(aftera));
    	// Create the email message
    	MultiPartEmail email = new MultiPartEmail();
    	//�ʼ�������
    	email.setHostName("smtp.qq.com");
    	//�˿ں�
    	email.setSmtpPort(25);
    	//�û���������
    	email.setAuthenticator(new DefaultAuthenticator(username, password));
    	//email.setSSLOnConnect(true);
    	email.setSSL(true);
    	try {
    	//�ռ���
    	email.addTo(mailTo, "��ʦ");
    	//������
    	email.setFrom(username, personalName);
    	//����
    	email.setSubject(mailSubject);
    	//����
    	email.setMsg(mailBody);
    	// add the attachment
    	email.attach(attachment);
    	// send the email
    	email.send();
    	} catch (EmailException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    	}   
    }

}
