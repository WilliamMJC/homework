package com.zt.homework.service;

import com.zt.homework.config.AppContext;
import com.zt.homework.dao.CourseDao;
import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.SCDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.dto.UserDto;
import com.zt.homework.entity.Course;
import com.zt.homework.entity.CourseMember;
import com.zt.homework.entity.SC;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import com.zt.homework.exception.ResourceNotFoundException;
import com.zt.homework.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseMemberService {
    @Autowired
    private CourseMemberDao courseMemberDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SCDao scDao;

    public void deleteCM(Integer courseId, Integer userId) {
        Integer num = courseMemberDao.deleteCM(courseId, userId);
        if (num == null || num < 0) {
            throw new ServerException(ResultEnum.SERVER_ERROR);
        }
    }

    public void joinCourseByCourseId(Integer courseId) {
        Integer userId = AppContext.getCurrentUserId();
        Course course = courseDao.queryCourseByCourseId(courseId);
        if (course == null) {
            throw new ResourceNotFoundException(ResultEnum.NOT_FOUNDE);
        }
        if (courseMemberDao.hasCM(course.getCourseId(), userId) > 0) {
            throw new AuthException(ResultEnum.OPERATION_FAIL);
        }

        Boolean isPublic = course.getPublic();
        if (!isPublic) {
            SC sc = new SC();
            sc.setCourseId(course.getCourseId());
            sc.setStuId(userDao.queryUserByUserId(userId).getPersonalId());
            if (scDao.hasSC(sc) == null || scDao.hasSC(sc) < 1) {
                throw new AuthException(ResultEnum.PERMISSION_DENY);
            }
        }

        CourseMember cm = new CourseMember();
        cm.setCourseId(course.getCourseId());
        cm.setUserId(userId);
        User user = userDao.queryUserByUserId(userId);
        cm.setPersonalId(user.getPersonalId());
        cm.setType("student");
        cm.setPermission(0);
        courseMemberDao.insertCM(cm);

    }

    public List<UserDto> queryStuCMByPage(Integer courseId, Integer currPage, Integer pageSize) {
        Integer userId = AppContext.getCurrentUserId();
        CourseMember courseMember = courseMemberDao.queryCMByCourseIdUserId(courseId, userId);
        if (courseMember == null) {
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        }

        List<UserDto> userDtos = new ArrayList<>();
        List<CourseMember> courseMemberList = courseMemberDao.queryCMByCourseIdWithPage(courseId, currPage * pageSize, pageSize);
        for(CourseMember cm : courseMemberList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(cm.getUserId());
            User stu = userDao.queryUserByUserId(cm.getUserId());
            userDto.setUsername(stu.getUsername());
            userDto.setPersonalId(cm.getPersonalId());
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
