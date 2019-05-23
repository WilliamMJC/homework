package com.zt.homework.service;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.Utils.IOUtil;
import com.zt.homework.Utils.MailSent;
import com.zt.homework.dao.ConstructionDao;
import com.zt.homework.dao.CourseDao;
import com.zt.homework.dao.HomeworkDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.entity.Construction;
import com.zt.homework.entity.Homework;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
public class ConstructionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Value("${homeDir}")
    private String homeDir;

    @Autowired
    private ConstructionDao constructionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private HomeworkDao homeworkDao;

    @Transactional
    @Async
    public void createConstruction(Integer courseId, Integer taskId, Integer userId) {
        String path = courseId + File.separator + taskId;
        String zipPath = courseId + File.separator + taskId + ".zip";
        if (IOUtil.folder2Zip(path, zipPath)) {
            User user = userDao.queryUserByUserId(userId);

            String mailTo = user.getPersonalMail();
            String mailFrom = user.getWorkMail();
            String mailPwd = user.getWorkMailPwd();
            String subject = courseDao.queryCourseByCourseId(courseId).getCourseName() + "第" + taskId + "次作业";

            String fileLinKString = "";
            List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskIdCourseId(taskId, courseId);
            for(Homework homework: homeworkList) {
                String fileLink = homework.getFileLink();
                if(fileLink != null && !fileLink.equals("")) {
                    String fileName = homework.getFileName();
                    String liString = "<li><a href=" + fileLink + ">" + fileName + "</a></li>";
                    fileLinKString += liString;
                }
            }
            boolean isAnyFileLink = !fileLinKString.equals("");

            String content = "<div style=\" \">" +
                    "<h1>这是" + subject + "的作业文件，请注意查收</h1>" +
                    "<h2 style=\"display:" + (isAnyFileLink ? "block" : "none") + "\">以下是超大附件下载链接，点击即可下载</h2>" +
                    "<ul>" + fileLinKString + "</ul>" +
                    "<div/>";

            String attachFilePath = homeDir + zipPath;
            String attachFileName = subject + ".zip";

            // 如果附件大小超过30m，则转为发送链接
            long zipFileSize = IOUtil.getFileSize(zipPath);
            if(zipFileSize > 20 * 1024 * 1024) {
                String zipUrl = "https://homework.infoaas.com/homework/construction/zip/" + courseId + "/" + taskId;
                content += "<h2>文件大小超过邮箱附件大小限制，请点击链接下载（大小："+ IOUtil.fileSizeFormat(zipFileSize) +"）</h2>" +
                        "<a target=\"_blank\" href=\"" + zipUrl + "\">作业邮件链接</a>";
                attachFilePath = "";
            }

            try {
                MailSent.sendMailWithAttach(mailTo, mailFrom, mailPwd, subject, content, attachFilePath, attachFileName);
                if (constructionDao.hasConstruction(taskId, courseId) > 0) {
                    constructionDao.updateConstruction(DateUtil.Date2Timestamp(new Date()), taskId, courseId);
                } else {
                    Construction construction = new Construction();
                    construction.setTaskId(taskId);
                    construction.setCourseId(courseId);
                    construction.setDownloadTime(DateUtil.Date2Timestamp(new Date()));
                    construction.setConstructionName(attachFileName);
                    constructionDao.insertConstruction(construction);
                }
            } catch (MessagingException | UnsupportedEncodingException e) {
                LOGGER.error("邮件发送失败", e);
                throw new AuthException(ResultEnum.MAIL_SENT_FAIL);
            }
        } else {
            LOGGER.error("邮件发送失败, 压缩文件失败");
            throw new AuthException(ResultEnum.MAIL_SENT_FAIL);
        }
    }
}
