package com.zt.homework.Utils;

import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.SCDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.entity.CourseMember;
import com.zt.homework.entity.SC;
import com.zt.homework.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParseMimeMessageTest {
    @Autowired
    private SCDao scDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    private Message[] getMessages() throws MessagingException {
        String user = "2559155740@qq.com";
        String mailPwd = "xclytgbzgyrsdjai";
        String host = ConnUtill.getPOP3Host(user);

        Store store = ConnUtill.popConnect(host, user, mailPwd);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        System.out.println(folder.getMessageCount());
        return folder.getMessages();
    }

    private ParseMimeMessage pmm = null;

    @Test
    public void getMessageId() throws MessagingException {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println(pmm.getMessageId());
        }

        assertEquals(2, messages.length);
    }

    @Test
    public void getSubject() throws MessagingException, UnsupportedEncodingException {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println(pmm.getSubject());
        }
        assertEquals(2, messages.length);
    }

    @Test
    public void getSentDateFormat() throws Exception {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println(pmm.getSentDateFormat());
        }
        assertEquals(2, messages.length);
    }

    @Test
    public void getSentDate() throws Exception {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println(pmm.getSentDate());
        }
        assertEquals(2, messages.length);
    }

    @Test
    public void getMailContent() throws Exception {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println("-------------------" + pmm.getSubject() + "----------------------");
            pmm.getMailContent(message);
        }
//        assertEquals(2, messages.length);
    }

    @Test
    public void isContainAttach() throws Exception {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            InputStream in = pmm.isContainAttach(message);
            if(in != null) {
                IOUtil.storeFile("1514080901110-测试-java-任务1.docx", in);
            }
        }
        assertEquals(2, messages.length);
    }

    @Test
    public void getAttachFileName() throws Exception {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            InputStream in = pmm.isContainAttach(message);
            if (in != null) {
                System.out.println(pmm.getAttachFileName(message));
            }
        }
    }

    @Test
    public void getMailFromString() throws MessagingException {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println(pmm.getMailFromString());
        }
    }

    @Test
    public void getMailFrom() {
        List<SC> scList = scDao.querySCByCourseId(48);
        int i = 3;
        for(SC sc : scList) {
            User user = new User();
            user.setUsername("test" + i++);
            user.setOpenid(sc.getStuId());
            user.setPersonalId(sc.getStuId());
            user.setPersonalMail(user.getUsername() + "@qq.com");

            userDao.insertUser(user);
            userDao.updateUser(user);

            CourseMember cm = new CourseMember();
            cm.setCourseId(48);
            cm.setUserId(user.getUserId());
            cm.setPersonalId(user.getPersonalId());
            cm.setType("student");
            cm.setPermission(0);
            courseMemberDao.insertCM(cm);
        }
    }

    @Test
    public void getBigAttachFileLink() throws Exception {
        Message[] messages = getMessages();
        for (Message message : messages) {
            pmm = new ParseMimeMessage((MimeMessage) message);
            System.out.println(pmm.getBigAttachFileLink(message));
        }
    }
}