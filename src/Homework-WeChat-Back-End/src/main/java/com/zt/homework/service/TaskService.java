package com.zt.homework.service;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.Utils.IOUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dao.CourseMemberDao;
import com.zt.homework.dao.HomeworkDao;
import com.zt.homework.dao.TaskDao;
import com.zt.homework.dao.UserDao;
import com.zt.homework.dto.*;
import com.zt.homework.entity.CourseMember;
import com.zt.homework.entity.Homework;
import com.zt.homework.entity.Task;
import com.zt.homework.entity.User;
import com.zt.homework.enums.ResultEnum;
import com.zt.homework.exception.AuthException;
import com.zt.homework.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private CourseMemberDao courseMemberDao;

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HomeworkService homeworkService;


    /**
     * 创建一个任务
     * @param task
     * @return
     */
    @Transactional
    public Integer createTask(Task task) {
        isCurrentCourseTea(task.getCourseId());
        List<Task> tasks = taskDao.queryTaskByCourseId(task.getCourseId());
        Integer size = tasks == null ? 0 : tasks.size();
        task.setTaskId(size + 1);
        if(task.getAcceptType() == null) {
            task.setAcceptType(7);
        }
        Integer num = taskDao.insertTask(task);
        if(num == null || num < 1) {
            throw new ServerException(ResultEnum.SERVER_ERROR);
        } else {
            // 创建本地文件夹
            IOUtil.createFolder(task.getCourseId() + File.separator + task.getTaskId());
            return task.getTaskId();
        }
    }

    /**
     * 获取用户的任务列表
     * @param userId
     * @return
     */
    public List<TaskListItem> getTaskByUserId(Integer userId) {
        List<TaskListItem> taskListItemList = new ArrayList<>();
        List<Task> taskList = taskDao.queryTaskByUserId(userId);
        for(Task task : taskList) {
//            if(task.getTaskEnd()) {
////                 学生不能看到已经结束的任务
//                if(!courseMemberDao.queryCMByCourseIdUserId(task.getCourseId(), userId).getType().equals("student")) {
//                    continue;
//                }
//            }
            TaskListItem item = new TaskListItem();
            item.setTaskId(task.getTaskId());
            item.setCourseId(task.getCourseId());
            item.setTaskName(task.getTaskName());
            item.setStartTime(DateUtil.timestamp2String(task.getStartTime()));
            item.setEndTime(DateUtil.timestamp2String(task.getEndTime()));
            item.setIsTaskEnd(task.getTaskEnd());

            List<CourseMember> courseMembers = courseMemberDao.queryCMByCourseId(task.getCourseId());
            Integer total = courseMembers == null ? 0 : courseMembers.size() - 1;

            List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskIdCourseId(task.getTaskId(), task.getCourseId());
            Integer receive = homeworkList == null ? 0 : homeworkList.size();

            item.setReceive(receive);
            item.setTotal(total);

            taskListItemList.add(item);
        }
        return taskListItemList;
    }

    /**
     * 获取任务信息
     * @param taskId
     * @param courseId
     * @return
     */
    public TaskDto getTaskDtoByTaskIdCourseId(Integer taskId, Integer courseId) {
        TaskDto taskDto = new TaskDto();
        Integer userId = AppContext.getCurrentUserId();

        CourseMember cm = courseMemberDao.queryCMByCourseIdUserId(courseId, userId);
        if(cm == null){
            throw new AuthException(ResultEnum.PERMISSION_DENY);
        }else if(cm.getType().equals("teacher")) {
            taskDto.setIsTeacher(true);
            taskDto.setIsStudent(false);
        } else {
            taskDto.setIsTeacher(false);
            taskDto.setIsStudent(true);
            User stu = userDao.queryUserByUserId(userId);
            taskDto.setPersonalId(stu.getPersonalId());
            taskDto.setStuName(stu.getUsername());
        }


        Task task = taskDao.queryTaskByTaskIdCourseId(taskId, courseId);

        taskDto.setTaskId(task.getTaskId());
        taskDto.setCourseId(task.getCourseId());
        taskDto.setTaskName(task.getTaskName());
        taskDto.setStartTime(DateUtil.Date2String(task.getStartTime()));
        taskDto.setEndTime(DateUtil.Date2String(task.getEndTime()));
        taskDto.setAcceptType(task.getAcceptType());
        taskDto.setTaskDesc(task.getTaskDesc());



        List<CourseMember> cmList = courseMemberDao.queryCMByCourseId(task.getCourseId());
        Integer total = cmList == null ? 0 : cmList.size() - 1;

        List<HomeworkDto> receive = new ArrayList<>();
        if(total != 0) {
            receive = homeworkService.getTaskHomeworkDtoList(taskId, courseId, userId);
        }

        taskDto.setTotal(total);
        taskDto.setReceive(receive);

        return taskDto;
    }

    public EndTaskDto getEndTaskDto(Integer courseId, Integer taskId) { //图表所需的数据在此获得
        Integer userId = AppContext.getCurrentUserId(); //获取当前用户id
        if(courseMemberDao.queryCMByCourseIdUserId(courseId, userId) == null) {
            throw new AuthException(ResultEnum.PERMISSION_DENY);    //检查当前用户是否参与该课程
        }
        EndTaskDto endTaskDto = new EndTaskDto();
        Task task = taskDao.queryTaskByTaskIdCourseId(taskId, courseId); //检查该课程的作业信息

        endTaskDto.setCourseId(task.getCourseId());
        endTaskDto.setTaskId(task.getTaskId());
        endTaskDto.setTaskName(task.getTaskName());
        endTaskDto.setStartTime(DateUtil.timestamp2String(task.getStartTime()));
        endTaskDto.setEndTime(DateUtil.timestamp2String(task.getEndTime()));

        List<CourseMember> cmList = courseMemberDao.queryCMByCourseId(courseId); //获取该课程的成员列表
        Integer total = cmList == null ? 0 : cmList.size() - 1;
        List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskIdCourseId(task.getTaskId(), task.getCourseId());//获取已提交的作业列表
        Integer receive = homeworkList == null ? 0 : homeworkList.size();

        // todo 这里待优化
        List<HomeworkDto> homeworkDtoList = new ArrayList<>();//所有已提交了作业的成员信息
        if(total != 0) {    //课程成员不为0
            for(CourseMember cm : cmList) {
                if(cm.getType().equals("teacher")) continue;
                HomeworkDto homeworkDto = new HomeworkDto();
                homeworkDto.setUserId(cm.getUserId());
                String username = userDao.queryUserByUserId(cm.getUserId()).getUsername();
                homeworkDto.setUsername(username);
                homeworkDtoList.add(homeworkDto);
            }
        }


        for(HomeworkDto homeworkDto : homeworkDtoList) {    //遍历所有成员
            if(receive != 0) {  //已提交数不为0
                for(Homework homework : homeworkList) {
                    if(homeworkDto.getUserId().equals(homework.getUserId())) {
                        homeworkDto.setSentDate(DateUtil.timestamp2String(homework.getSentDate()));
                        String fileType = homework.getFileName().substring(homework.getFileName().lastIndexOf('.') + 1);
                        homeworkDto.setFileType(fileType);
                    }
                }
            }
        }

        endTaskDto.setHomeworkDtoList(homeworkDtoList);
        endTaskDto.setReceive(receive);
        endTaskDto.setUnReceive(total - receive);
        return endTaskDto;
    }

    public TotalTaskDto getTotalTaskDtoByCourseId(Integer courseId){
        TotalTaskDto totalTaskDto=new TotalTaskDto();
        //获取该课程人数
        List<CourseMember> courseMembers = courseMemberDao.queryCMByCourseId(courseId);
        Integer totalCM = courseMembers == null ? 0 : courseMembers.size() - 1;
        //获取课程数
        List<Task> tasks = taskDao.queryTaskByCourseId(courseId);
        Integer taskCount=tasks.size();
        //获取总课程的应收作业数
        Integer total=totalCM*taskCount;
        //获取已交作业数
        Integer totalRecive=0;
        for(Task ts:tasks){
            List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskIdCourseId(ts.getTaskId(), ts.getCourseId());
            Integer individualReceive = homeworkList == null ? 0 : homeworkList.size();
            totalRecive+=individualReceive;
        }
        totalTaskDto.setTotalCM(totalCM);
        totalTaskDto.setTotal(total);
        totalTaskDto.setRecive(totalRecive);
        return totalTaskDto;
    }

    /**
     * 结束任务
     * @param courseId
     * @param taskId
     */
    public void endTask(Integer courseId, Integer taskId) {
        isCurrentCourseTea(courseId);
        Integer num = taskDao.endTask(taskId, courseId);
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
