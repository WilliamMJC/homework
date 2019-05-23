package com.zt.homework.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSentTest {
    @Value("${homeDir}")
    private String homeDir;

    @Test
    public void sendMailWithAttach() throws MessagingException, UnsupportedEncodingException {
        String mailFrom = "";   // 发件人邮箱
        String mailTo = "";     //  收件人邮箱
        String mailPwd = "";    //  邮箱授权码
        String subject = "这里是测试";
        String content = "中文测试";
        String attachFilePath = homeDir + "4" + File.separator + "1514080901110-测试-java-任务1.docx";
        String attachFileName = "1514080901110-测试-java-任务1.docx";

        MailSent.sendMailWithAttach(mailTo, mailFrom, mailPwd, subject, content, attachFilePath, attachFileName);
    }
}