package com.zt.homework.service;

import com.zt.homework.Utils.*;
import com.zt.homework.config.AppContext;
import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.HomeworkDao;
import com.zt.homework.dao.TaskDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.dto.HomeworkDto;
import com.zt.homework.entity.CourseMember;
import com.zt.homework.entity.Homework;
import com.zt.homework.entity.Task;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import com.zt.homework.exception.MailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeworkService {
    @Value("${homeDir}")
    private String homeDir;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskDao taskDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeworkService.class);

    public List<HomeworkDto> getTaskHomeworkDtoList(Integer taskId, Integer courseId, Integer userId) {
        CourseMember cm = courseMemberDao.queryCMByCourseIdUserId(courseId, userId);
        List<HomeworkDto> homeworkDtoList = new ArrayList<>();
        if (cm == null) {
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        } else if (cm.getType().equals("teacher")) {
            List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskIdCourseId(taskId, courseId);

            for (Homework homework : homeworkList) {
                HomeworkDto homeworkDto = new HomeworkDto();
                homeworkDto.setUserId(homework.getUserId());
                homeworkDto.setSentDate(DateUtil.timestamp2String(homework.getSentDate()));
                String username = userDao.queryUserByUserId(homework.getUserId()).getUsername();
                homeworkDto.setUsername(username);
                String fileType = homework.getFileName().substring(homework.getFileName().lastIndexOf('.') + 1);
                homeworkDto.setFileType(fileType);
                homeworkDtoList.add(homeworkDto);
            }
        } else if (cm.getType().equals("student")) {
            Homework homework = homeworkDao.queryHomework(taskId, userId, courseId);
            if (homework != null) {
                HomeworkDto homeworkDto = new HomeworkDto();
                homeworkDto.setUserId(homework.getUserId());
                homeworkDto.setSentDate(DateUtil.timestamp2String(homework.getSentDate()));
                String username = userDao.queryUserByUserId(userId).getUsername();
                String fileType = homework.getFileName().substring(homework.getFileName().lastIndexOf('.') + 1);
                homeworkDto.setFileType(fileType);
                homeworkDto.setUsername(username);
                homeworkDtoList.add(homeworkDto);
            }
        }
        return homeworkDtoList;
    }

    public void createEvaluation(Integer courseId, Integer taskId) throws IOException {
        User user = userDao.queryUserByUserId(AppContext.getCurrentUserId());
        Task task = taskDao.queryTaskByTaskIdCourseId(taskId, courseId);
        CourseMember cm = courseMemberDao.queryCMByCourseIdUserId(courseId, AppContext.getCurrentUserId());
        if (cm == null || !cm.getType().equals("teacher")) {
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        }
        List<Homework> homeworkArrayList = homeworkDao.queryHomeworkByTaskIdCourseId(taskId, courseId);
        List<List<String>> data = new ArrayList<>();
        for (Homework homework : homeworkArrayList) {
            List<String> item = new ArrayList<>();
            item.add(homework.getUserId().toString());
            String personalId = courseMemberDao.queryCMByCourseIdUserId(courseId, homework.getUserId()).getPersonalId();
            item.add(personalId);
            item.add(userDao.queryUserByUserId(homework.getUserId()).getUsername());

            data.add(item);
        }

        FileOutputStream out = new FileOutputStream(homeDir + courseId + File.separator + taskId + File.separator + task.getTaskName() + "评价.xlsx");
        ExcelUtil.createExcel(data).write(out);
        out.close();

        String mailTo = user.getPersonalMail();
        String mailFrom = user.getWorkMail();
        String mailPwd = user.getWorkMailPwd();
        String subject = "这是" + task.getTaskName() + "的评价excel";

        String attachFilePath = homeDir + courseId + File.separator + taskId + File.separator + task.getTaskName() + "评价.xlsx";
        String attachFileName = task.getTaskName() + "-评价.xlsx";

        try {
            MailSent.sendMailWithAttach(mailTo, mailFrom, mailPwd, subject, "", attachFilePath, attachFileName);
        } catch (MessagingException e) {
            LOGGER.error("邮件发送失败", e);
            throw new AuthException(ResultEnum.MAIL_SENT_FAIL);
        }
    }

    @Async
    public void completeEvaluation(Integer taskId, Integer courseId) throws Exception {
        InputStream in = null;
        List<List<String>> data = null;

        Task task = taskDao.queryTaskByTaskIdCourseId(taskId, courseId);

        User user = userDao.queryUserByUserId(AppContext.getCurrentUserId());
        String workMail = user.getWorkMail();
        String workMailPwd = user.getWorkMailPwd();
        String host = ConnUtill.getPOP3Host(workMail);

        Store store = ConnUtill.popConnect(host, workMail, workMailPwd);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
        for(int i = messages.length - 1; i >= 0; i--) {
            ParseMimeMessage pmm = new ParseMimeMessage(( MimeMessage) messages[i]);
            if (pmm.getSubject().equals(task.getTaskName() + "-评价")) {
                in = pmm.isContainAttach(messages[i]);
                if (in == null) {
                    throw new MailException(ResultEnum.MAIL_WITHOUT_ATTACH_FILE);
                }
                data = ExcelUtil.readExcel(in);
                break;
            }
        }

        for(int i = 0; i < data.size(); i++) {
            int userId = Integer.parseInt(data.get(i).get(0));
            Homework homework = homeworkDao.queryHomework(taskId, userId, courseId);
            String path = homeDir + courseId + File.separator + taskId + File.separator + homework.getFileName();
            WordUtil.handleWord(path, data.get(i).get(1));
        }
    }
}
