package com.zt.homework.service;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.Utils.IOUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dao.*;
import com.zt.homework.dto.CourseDto;
import com.zt.homework.dto.StuCourseListItem;
import com.zt.homework.dto.TeaCourseListItem;
import com.zt.homework.dto.UserDto;
import com.zt.homework.entity.*;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import com.zt.homework.exception.ServerException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    @Autowired
    private SCDao scDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private HomeworkDao homeworkDao;

    /**
     * 添加课程
     *
     * @param courseInit
     * @return
     */
    @Transactional
    public Integer addCourse(Course courseInit) {
        Course course = new Course();
        course.setCourseName(courseInit.getCourseName());
        course.setCourseDesc(courseInit.getCourseDesc());
        course.setPublic(courseInit.getPublic());

//        String courseCode = RandomStringUtils.randomNumeric(6);
//        course.setCourseCode(courseCode);

        try {
            Integer num = courseDao.insertCourse(course);
            if (num == 0 || num == null) {
                LOGGER.error("创建课程失败, 数据库错误，无法插入数据");
                throw new ServerException(ResultEnum.SERVER_ERROR);
            }
            Integer courseId = course.getCourseId();

            // 初始化课程老师
            CourseMember cm = new CourseMember();
            User user = userDao.queryUserByUserId(AppContext.getCurrentUserId());
            cm.setCourseId(courseId);
            cm.setUserId(user.getUserId());
            cm.setPersonalId(user.getPersonalId());
            cm.setType("teacher");
            cm.setPermission(4);

            courseMemberDao.insertCM(cm);

            // 创建本地文件夹
            IOUtil.createFolder(String.valueOf(courseId));
            // 创建资源文件夹
            IOUtil.createFolder(String.valueOf(courseId) + File.separator + "resources");

            return course.getCourseId();
        } catch (Exception e) {
            LOGGER.error("添加课程出错", e);
            throw new ServerException(ResultEnum.SERVER_ERROR);
        }
    }


    /**
     * 获取用户教授的课程列表
     * @param userId
     * @return
     */
    public List<TeaCourseListItem> getTeaCourseListItems(Integer userId) {
        List<Course> courseList = courseDao.teaQueryCourseByUserId(userId);
        List<TeaCourseListItem> teaCourseListItemList = new ArrayList<>();
        for(Course course : courseList) {
            TeaCourseListItem item = new TeaCourseListItem();

            item.setCourseId(course.getCourseId());
            item.setCourseName(course.getCourseName());
            item.setCourseDesc(course.getCourseDesc());
            item.setIsCourseEnd(course.getCourseEnd());
//            item.setCourseCode(course.getCourseCode());

            List<CourseMember> courseMembers= courseMemberDao.queryCMByCourseId(course.getCourseId());
            Integer memberSize = courseMembers == null ? 0 : courseMembers.size();

            List<Task> tasks = taskDao.queryTaskByCourseId(course.getCourseId());
            Integer taskSize = tasks == null ? 0 : tasks.size();

            item.setMemberSize(memberSize);
            item.setTaskSize(taskSize);

            teaCourseListItemList.add(item);
        }
        return teaCourseListItemList;
    }

    /**
     * 获取用户加入的课程列表
     * @param userId
     * @return
     */
    public List<StuCourseListItem> getStuCourseListItems(Integer userId) {
        List<Course> courseList = courseDao.stuQueryCoursesByUserId(userId);
        List<StuCourseListItem> stuCourseListItems = new ArrayList<>();

        for(Course course : courseList) {
            StuCourseListItem item = new StuCourseListItem();

            item.setCourseId(course.getCourseId());
            item.setCourseName(course.getCourseName());
            item.setCourseDesc(course.getCourseDesc());
            item.setCourseEnd(course.getCourseEnd());
//            item.setCourseCode(course.getCourseCode());

            List<Task> tasks = taskDao.queryTaskByCourseId(course.getCourseId());
            Integer taskSize = tasks == null ? 0 : tasks.size();

            String teaName = userDao.queryUserNameByCourseId(course.getCourseId());

            item.setTaskSize(taskSize);
            item.setTeaName(teaName);

            stuCourseListItems.add(item);
        }
        return stuCourseListItems;
    }

//    /**
//     * 用户加入课程
//     * @param userId
//     * @param courseCode
//     * @return
//     */
//
//    public Integer userJoinCourse(Integer userId, String courseCode) {
//        Course course = courseDao.queryCourseByCourseCode(courseCode);
//        if(course == null) {
//            throw new ResourceNotFoundException(ResultEnum.NOT_FOUNDE);
//        } else {
//            if(courseMemberDao.hasCM(course.getCourseId(), userId) > 0) {
//                throw new AuthException(ResultEnum.OPERATION_FAIL);
//            } else {
//                Boolean isPublic = course.getPublic();
//                if(!isPublic) {
//                    SC sc = new SC();
//                    sc.setCourseId(course.getCourseId());
//                    sc.setStuId(userDao.queryUserByUserId(userId).getPersonalId());
//                    if(scDao.hasSC(sc) == null || scDao.hasSC(sc) < 1) {
//                        throw new AuthException(ResultEnum.PERMISSION_DENY);
//                    }
//                }
//
//                CourseMember cm = new CourseMember();
//                cm.setCourseId(course.getCourseId());
//                cm.setUserId(userId);
//                User user = userDao.queryUserByUserId(userId);
//                cm.setPersonalId(user.getPersonalId());
//                cm.setType("student");
//                cm.setPermission(0);
//                courseMemberDao.insertCM(cm);
//            }
//        }
//        return course.getCourseId();
//    }

    /**
     * 更新课程信息
     *
     * @param course
     */
    public void updateCourse(Course course) {
        isCurrentCourseTea(course.getCourseId());
        try {
            Integer num = courseDao.updateCourseByCourseId(course);
            if (num == 0 || num == null) {
                throw new ServerException(ResultEnum.SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new ServerException(ResultEnum.SERVER_ERROR);
        }
    }

    public CourseDto getCourseDto(Integer userId, Integer courseId) {
        CourseDto courseDto = new CourseDto();

        CourseMember courseMember = courseMemberDao.queryCMByCourseIdUserId(courseId, userId);
        if(courseMember == null) {
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        } else {
            String type = courseMember.getType();
            if(type.equals("teacher")) {
                courseDto.setIsTeacher(true);
                courseDto.setIsStudent(false);
            } else {
                courseDto.setIsTeacher(false);
                courseDto.setIsStudent(true);
            }
        }

        Course course = courseDao.queryCourseByCourseId(courseId);
        courseDto.setCourseId(course.getCourseId());
        courseDto.setCourseName(course.getCourseName());
        courseDto.setCourseDesc(course.getCourseDesc());
        courseDto.setIsCourseEnd(course.getCourseEnd());
        courseDto.setIsPublic(course.getPublic());
//        courseDto.setCourseCode(course.getCourseCode());

        List<String> stuIdList = new ArrayList<>();
        List<SC> scList = scDao.querySCByCourseId(courseId);
        for(SC sc : scList) {
            String stuId = sc.getStuId();
            stuIdList.add(stuId);
        }
        courseDto.setStuIdList(stuIdList);

        JSONArray taskList = new JSONArray();
        List<Task> tasks = taskDao.queryTaskByCourseId(courseId);
        for(Task task : tasks) {
            JSONObject o = new JSONObject();
            o.put("taskId", task.getTaskId());
            o.put("courseId", task.getCourseId());
            o.put("taskName", task.getTaskName());
            o.put("startTime", DateUtil.Date2String(task.getStartTime()));
            o.put("endTime", DateUtil.Date2String(task.getEndTime()));
            o.put("isTaskEnd", task.getTaskEnd());

            List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskIdCourseId(task.getTaskId(), task.getCourseId());
            Integer receive = homeworkList == null ? 0 : homeworkList.size();
            o.put("receive", receive);

            taskList.add(o);
        }
        courseDto.setTaskList(taskList);

        JSONArray resourceList = new JSONArray();
        List<Resource> resources = resourceDao.queryResourceByCourseId(courseId);
        for(Resource resource : resources) {
            JSONObject o = new JSONObject();
            o.put("resourceId", resource.getResourceId());
            o.put("resourceName", resource.getResourceName());
            o.put("resourceSize", resource.getResourceSize());
            o.put("uploadTime", DateUtil.Date2String(resource.getUploadTime()));
            resourceList.add(o);
        }
        courseDto.setResourceList(resourceList);

        UserDto teacher = new UserDto();
        CourseMember tea = courseMemberDao.queryTeaByCourseId(courseId);
        teacher.setUserId(tea.getUserId());
        User teaUser = userDao.queryUserByUserId(tea.getUserId());
        teacher.setUsername(teaUser.getUsername());
        teacher.setPersonalId(teaUser.getPersonalId());
        courseDto.setTeacher(teacher);

        JSONArray stuList = new JSONArray();
        List<CourseMember> courseMembers = courseMemberDao.queryCMByCourseIdWithPage(courseId, 0, 20);
        for(CourseMember cm : courseMembers) {
            UserDto stu = new UserDto();
            stu.setUserId(cm.getUserId());
            User stuUser = userDao.queryUserByUserId(cm.getUserId());
            stu.setUsername(stuUser.getUsername());
            stu.setPersonalId(stuUser.getPersonalId());
            stuList.add(stu);
        }
        courseDto.setStuList(stuList);

        Integer stuLength = courseMemberDao.queryStuCMByCourseId(courseId);
        stuLength = stuLength == null ? 0 : stuLength;
        courseDto.setStuLength(stuLength);

        return courseDto;
    }

    public void deleteCourseByCourseId(Integer courseId) {
        isCurrentCourseTea(courseId);
        Integer num = courseDao.deleteCourseByCourseId(courseId);
        if(num == null || num < 1) {
            throw new ServerException(ResultEnum.SERVER_ERROR);
        }
    }

    /**
     * 当前用户是否对此课程有完整的权限
     *
     * @param courseId
     * @return
     */
    private void isCurrentCourseTea(Integer courseId) {
        Integer userId = AppContext.getCurrentUserId();
        Integer permission = courseMemberDao.queryPermissionByCM(courseId, userId);
        if (permission == null || permission != 4) {
            LOGGER.warn("该用户不能进行此项操作");
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        }
    }

}
