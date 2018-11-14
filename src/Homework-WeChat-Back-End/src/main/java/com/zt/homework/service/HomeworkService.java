package com.zt.homework.service;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.HomeworkDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.dto.HomeworkDto;
import com.zt.homework.entity.CourseMember;
import com.zt.homework.entity.Homework;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    @Autowired
    private UserDao userDao;

    public List<HomeworkDto> getTaskHomeworkDtoList(Integer taskId, Integer courseId, Integer userId) {
        CourseMember cm = courseMemberDao.queryCMByCourseIdUserId(courseId, userId);
        List<HomeworkDto> homeworkDtoList = new ArrayList<>();
        if(cm == null) {
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        } else if(cm.getType().equals("teacher")) {
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
        } else if(cm.getType().equals("student")) {
            Homework homework = homeworkDao.queryHomework(taskId, userId, courseId);
            if(homework != null) {
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
}
