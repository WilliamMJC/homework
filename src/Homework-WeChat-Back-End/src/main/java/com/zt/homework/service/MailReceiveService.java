package com.zt.homework.service;

import com.zt.homework.Utils.ConnUtill;
import com.zt.homework.Utils.DateUtil;
import com.zt.homework.Utils.IOUtil;
import com.zt.homework.Utils.ParseMimeMessage;
import com.zt.homework.dao.*;
import com.zt.homework.entity.Homework;
import com.zt.homework.entity.Task;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.MailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailReceiveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailReceiveService.class);

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    /**
     * 检索邮箱
     *
     * @param messages
     * @param task
     * @throws Exception
     */
    @Transactional
    public void queryMail(Message[] messages, Task task) throws Exception {
        Integer taskId = task.getTaskId();
        Integer courseId = task.getCourseId();
        String taskName = task.getTaskName();
        Integer acceptType = task.getAcceptType();
        List<String> stuIdList = courseMemberDao.queryPersonalIdByCourseId(courseId);

        Date startTime = DateUtil.Date2Timestamp(task.getStartTime());
        Date endTime = DateUtil.Date2Timestamp(task.getEndTime());
        Boolean isTaskEnd = task.getTaskEnd();

        // 本次查询开始时最新的一封邮件的发送时间
        Date latestEmailTime = messages[messages.length - 1].getSentDate();
        // 当前系统时间
        Date current = new Date();
        // 邮件查询断点时间， 如果该任务未进行过查询，则设任务开始时间为断点， 否则设上一次查询时最新的邮件的发送时间为查询断点时间
        Date breakPointTime = (task.getLastReceiveTime() == null) ? startTime : task.getLastReceiveTime();

        // 任务结束不再查询
        if (!isTaskEnd) {
            for (int i = messages.length - 1; i >= 0; i--) {
                ParseMimeMessage pmm = new ParseMimeMessage((MimeMessage) messages[i]);
                Date sentDate = pmm.getSentDate();

                // 将邮件发送时间跟邮件查询断点时间进行比较，如果发送时间在断点时间之后，则对该邮件进行检测，否则将本次查询的最新邮件发送时间保存到数据库，退出本次循环
                if (DateUtil.compare(breakPointTime, sentDate)) {
                    String subject = pmm.getSubject();
                    // 学号-姓名-任务名-#第几次
                    Pattern taskPattern = Pattern.compile("(\\d+)(-)(.+?)(-)(" + taskName + ")(-)(#)(" + taskId + ")");
                    Matcher subjectMatcher = taskPattern.matcher(subject);
                    if (subjectMatcher.find()) {
                        String stuId = subjectMatcher.group(1);
                        // 判断是否有这个学生
                        if (stuIdList.contains(stuId)) {
                            Integer userId = courseMemberDao.queryUserIdByCourseIdPersonalId(courseId, stuId);
                            InputStream in = pmm.isContainAttach(messages[i]);
                            // 是否存在附件
                            if (in != null) {
                                String fileName = pmm.getAttachFileName(messages[i]);
                                // 检查文件类型是否符合要求
                                if (checkIfFileType(acceptType, fileName)) {
                                    Homework homework = new Homework();
                                    homework.setTaskId(taskId);
                                    homework.setUserId(userId);
                                    homework.setCourseId(courseId);
                                    homework.setFileName(fileName);
                                    homework.setSentDate(DateUtil.Date2Timestamp(pmm.getSentDate()));

                                    // 当有重复提交且本邮件为最新时，更新作业记录
                                    Homework oldHomework = homeworkDao.queryHomework(taskId, userId, courseId);
                                    if (oldHomework != null) {
                                        if (DateUtil.compare(oldHomework.getSentDate(), pmm.getSentDate())) {
                                            // 删除本地文件
                                            String path = courseId + File.separator + taskId + File.separator + oldHomework.getFileName();
                                            String newPath = courseId + File.separator + taskId + File.separator + homework.getFileName();
                                            if (IOUtil.deleteFile(path)) {
                                                // 更新数据库并下载文件
                                                homeworkDao.updateHomework(homework);
                                                IOUtil.storeFile(newPath, in);
                                                LOGGER.info("--更新附件：" + homework.getFileName() + "--");
                                            }
                                        }
                                    } else {
                                        String path = courseId + File.separator + taskId + File.separator + homework.getFileName();
                                        homeworkDao.insertHomework(homework);
                                        IOUtil.storeFile(path, in);
                                        LOGGER.info("--下载附件：" + homework.getFileName() + "--");
                                    }
                                }
                            } else {
                                String fileLink = pmm.getBigAttachFileLink(messages[i]);
                                 // 存在超大附件链接
                                if(!fileLink.equals("")) {
                                    String fileName = pmm.getSubject();

                                    Homework homework = new Homework();
                                    homework.setTaskId(taskId);
                                    homework.setUserId(userId);
                                    homework.setCourseId(courseId);
                                    homework.setFileName(fileName);
                                    homework.setSentDate(DateUtil.Date2Timestamp(pmm.getSentDate()));
                                    homework.setFileLink(fileLink);

                                    // 当有重复提交且本邮件为最新时，更新作业记录
                                    Homework oldHomework = homeworkDao.queryHomework(taskId, userId, courseId);
                                    if (oldHomework != null) {
                                        if (DateUtil.compare(oldHomework.getSentDate(), pmm.getSentDate())) {
                                            // 更新数据库
                                            homeworkDao.updateHomework(homework);
                                            LOGGER.info("--更新超大附件：" + homework.getFileName() + "--");
                                        }
                                    } else {
                                        homeworkDao.insertHomework(homework);
                                        LOGGER.info("--超大附件插入数据库：" + homework.getFileName() + "--");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    taskDao.updateReceiveTime(taskId, courseId, DateUtil.Date2Timestamp(latestEmailTime));
                    break;
                }
            }
        }
    }

    public void queryMailByTask(Integer taskId, Integer courseId) {
        LOGGER.info("邮件检索开始");
        User user = userDao.queryTeaUserByCourseId(courseId);
        String workMail = user.getWorkMail();
        String workMailPwd = user.getWorkMailPwd();
        String host = ConnUtill.getPOP3Host(workMail);

        System.out.println("---------" + user.getUsername() + "---------");
        try {
            Store store = ConnUtill.popConnect(host, workMail, workMailPwd);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();

            Task task = taskDao.queryTaskByTaskIdCourseId(taskId, courseId);

            System.out.println("------" + task.getTaskName() + "------");
            queryMail(messages, task);
        } catch (Exception e) {
            LOGGER.error("邮箱检索失败", e);
            throw new MailException(ResultEnum.MAIL_SEARCH_FAIL);
        }

    }

    /**
     * 查询所有邮件
     */
    @Transactional
    public void queryAllMail() {
        List<User> teaUserList = userDao.queryAllTeaTypeUser();
        for (User tea : teaUserList) {
            System.out.println("---------" + tea.getUsername() + "---------");

            String personalMail = tea.getPersonalMail();
            String workMail = tea.getWorkMail();
            String workMailPwd = tea.getWorkMailPwd();
            String host = ConnUtill.getPOP3Host(workMail);

            Store store = null;
            try {
                store = ConnUtill.popConnect(host, workMail, workMailPwd);

                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);
                Message[] messages = folder.getMessages();

                List<Task> taskList = taskDao.queryTeaTaskByUserId(tea.getUserId());

                for (Task task : taskList) {
                    System.out.println("------" + task.getTaskName() + "------");
                    queryMail(messages, task);
                }
            } catch (Exception e) {
                LOGGER.error("邮箱检索失败", e);
                e.printStackTrace();
            }
        }

    }

    /**
     * 检查文件类型
     *
     * @param acceptType
     * @param fileName
     * @return
     */
    private static Boolean checkIfFileType(Integer acceptType, String fileName) {
        String acceptTypeList = "";
        if((acceptType & 1) == 1) {
            acceptTypeList += ".doc$|";
        }
        if((acceptType & 2) == 2) {
            acceptTypeList += ".docx$|";
        }
        if((acceptType & 4) == 4) {
            acceptTypeList += ".pdf$|";
        }

        if(acceptTypeList.endsWith("|")) {
            acceptTypeList = acceptTypeList.substring(0, acceptTypeList.length() - 1);
        }

        System.out.println(acceptTypeList);

        Pattern pattern = Pattern.compile(acceptTypeList);
        Matcher matcher = pattern.matcher(fileName);

        return matcher.find();
    }
}
