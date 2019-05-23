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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @Value("${homeDir}")
    private String homeDir;

    private Message[] getMessages() throws MessagingException {
        String user = "";      // 测试邮箱账号
        String mailPwd = "";   // 测试邮箱授权码
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
    public void getSubject() throws Exception {
        Message[] messages = getMessages();
        System.out.println(messages.length + "封邮件");
        for (int i = 26000; i < messages.length; i++) {
            pmm = new ParseMimeMessage((MimeMessage) messages[i]);
            System.out.println(pmm.getSubject());
            System.out.println(pmm.getSentDateFormat());
        }
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
            if (in != null) {
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
    public void getMailFromString() throws Exception {
        Message[] messages = getMessages();
        for (int i = messages.length - 1; i > 0; i--) {
            pmm = new ParseMimeMessage((MimeMessage) messages[i]);
            System.out.println("-------------------" + pmm.getSubject() + "----------------------");
            System.out.println(pmm.getSentDateFormat());
            try {
                System.out.println(pmm.getMailFromString());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getMailFrom() {
        List<SC> scList = scDao.querySCByCourseId(48);
        int i = 3;
        for (SC sc : scList) {
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

    // 折半搜索查找离目标时间最近的邮件
    private int findIndex(Message[] messages, Date breakPoint) throws Exception {
        int left = 26000;
        int right = messages.length;
        int middle = (right - left) / 2;
        while(true) {
            pmm = new ParseMimeMessage((MimeMessage) messages[middle]);
            System.out.println(pmm.getSentDateFormat());
            Date sentDate = pmm.getSentDate();
            if(sentDate.getTime() <= breakPoint.getTime() && breakPoint.getTime() - sentDate.getTime() < 2 * 24 * 3600 * 1000) {
                return middle;
            } else if(sentDate.getTime() < breakPoint.getTime() && breakPoint.getTime() - sentDate.getTime() >= 2 * 24 * 3600 * 1000) {
                left  = middle;
                middle = (right - left) / 2 + left;
            } else if(sentDate.getTime() > breakPoint.getTime()) {
                right = middle;
                middle = (right - left) / 2 + left;
            }
        }
    }

    @Test
    // 手动检索作业邮件
    public void test() {
        try {
            Message[] messages = getMessages();

            Date startTime = DateUtil.string2Timestamp("2019-04-21 10:10");
            Date endTime = DateUtil.string2Timestamp("2019-05-07 02:59");

            int index = findIndex(messages, startTime);

            System.out.println("第" + index + "封邮件");

            for(int i = index; i < messages.length; i++) {
                pmm = new ParseMimeMessage((MimeMessage) messages[i]);

                Date sentDate = pmm.getSentDate();
                if(DateUtil.compare(sentDate, endTime)) {
                    if(DateUtil.compare(sentDate, startTime)) {
                        continue;
                    }
                    String subject = pmm.getSubject();

                    Pattern taskPattern = Pattern.compile("(\\d+)(-)(.+?)(-)(" + "Web实验7" + ")(-)(#)(" + 7 + ")");
                    Matcher subjectMatcher = taskPattern.matcher(subject);
                    if(subjectMatcher.find()) {

                        SC sc = new SC();
                        sc.setCourseId(40);
                        sc.setStuId(subjectMatcher.group(1));

                        if(scDao.hasSC(sc) > 0) {
                            System.out.println(subject);
                            System.out.println(pmm.getSentDateFormat());

                            InputStream in = pmm.isContainAttach(messages[i]);
                            if(in != null) {
                                IOUtil.storeFile("homework" + File.separator + "50" + File.separator + "7" + File.separator + pmm.getAttachFileName(messages[i]), in);
                            }
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}