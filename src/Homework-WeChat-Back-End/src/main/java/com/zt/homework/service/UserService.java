package com.zt.homework.service;

import com.zt.homework.Utils.ConnUtill;
import com.zt.homework.Utils.DateUtil;
import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.HomeworkDao;
import com.zt.homework.dao.TaskDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.dto.HomeworkDto;
import com.zt.homework.dto.UserDto;
import com.zt.homework.entity.Homework;
import com.zt.homework.entity.Task;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.MailException;
import com.zt.homework.exception.ResourceNotFoundException;
import com.zt.homework.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private TaskDao taskDao;

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    public User updateUserInfo(User user) {
        int num = userDao.updateUser(user);
        if(num > 0) {
            return  user;
        } else {
            throw new ServerException(ResultEnum.SERVER_ERROR);
        }
    }

    /**
     * 测试邮箱连接
     * @param workMail
     * @param workMailPwd
     */
    public void testMailConnect(String workMail, String workMailPwd) {
        String host = ConnUtill.getPOP3Host(workMail);
        try {
            Store store = ConnUtill.popConnect(host, workMail, workMailPwd);
            if(store == null) {
                throw new MailException(ResultEnum.MAIL_CONNECT_FAIL);
            }
        } catch (MessagingException e) {
            LOGGER.error("邮箱连接失败", e);
            throw new MailException(ResultEnum.MAIL_CONNECT_FAIL);
        }
    }

    public User queryUser(Integer userId) {
        User user = userDao.queryUserByUserId(userId);
        if(user != null) {
            return user;
        } else {
            LOGGER.error("用户不存在");
            throw new ResourceNotFoundException(ResultEnum.NOT_FOUNDE);
        }
    }

    /**
     * 获取用户的基本信息
     * @param userId
     * @param currentUserId
     * @param courseId
     * @return
     */
    public UserDto getUserInfo(Integer userId, Integer currentUserId, Integer courseId) {
        UserDto userDto = new UserDto();
        User user = userDao.queryUserByUserId(userId);
        String type = courseMemberDao.queryCMByCourseIdUserId(courseId, userId).getType();
        String currentUserType = courseMemberDao.queryCMByCourseIdUserId(courseId, currentUserId).getType();
        if("teacher".equals(type) || ("student".equals(currentUserType) && !userId.equals(currentUserId))) {
            userDto.setUserId(user.getUserId());
            userDto.setUsername(user.getUsername());
            userDto.setMail("teacher".equals(type) ? user.getWorkMail() : user.getPersonalMail());
            userDto.setPersonalId(user.getPersonalId());
            userDto.setSchool(user.getSchool());
        } else if("teacher".equals(currentUserType) || userId.equals(currentUserId)) {
            userDto.setUserId(user.getUserId());
            userDto.setUsername(user.getUsername());
            userDto.setMail(user.getPersonalMail());
            userDto.setPersonalId(user.getPersonalId());
            userDto.setSchool(user.getSchool());

            List<Homework> homeworkList = homeworkDao.queryHomeworkByCourseIdUserId(courseId, userId);
            List<Task> taskList = taskDao.queryTaskByCourseId(courseId);
            List<HomeworkDto> homeworkDtoList = new ArrayList<>();
            // todo 这里逻辑混乱
            if(homeworkList == null || homeworkList.size() == 0) {
                for(int i = 0, len = taskList == null ? 0: taskList.size(); i < len; i++) {
                    homeworkDtoList.add(null);
                }
            } else {
                for(Homework homework : homeworkList) {
                    HomeworkDto homeworkDto = new HomeworkDto();
                    homeworkDto.setUserId(homework.getUserId());
                    homeworkDto.setUsername(user.getUsername());
                    homeworkDto.setSentDate(DateUtil.timestamp2String(homework.getSentDate()));
                    String fileType = homework.getFileName().substring(homework.getFileName().lastIndexOf('.') + 1);

                    homeworkDto.setFileType(fileType);
                    Integer taskId = homework.getTaskId();
                    while(homeworkDtoList.size() < taskId - 1) {
                        homeworkDtoList.add(null);
                    }
                    homeworkDtoList.add(taskId - 1, homeworkDto);
                }
            }
            userDto.setHomeworkDtoList(homeworkDtoList);
        }
        return userDto;
    }
}
