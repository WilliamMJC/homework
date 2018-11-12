package com.zt.homework.dao;

import com.zt.homework.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskDao {
    /**
     * 创建一个任务
     * @param task
     * @return
     */
    Integer insertTask(Task task);

    /**
     * 根据任务id查询一个任务
     * @param taskId
     * @param courseId
     * @return
     */
    Task queryTaskByTaskIdCourseId(@Param("taskId") Integer taskId, @Param("courseId") Integer courseId);

    /**
     * 根据课程id查找任务列表
     * @param courseId
     * @return
     */
    List<Task> queryTaskByCourseId(Integer courseId);

    /**
     * 根据用户id查找任务列表
     * @param userId
     * @return
     */
    List<Task> queryTaskByUserId(Integer userId);

    /**
     * 根据用户id查找教师任务列表
     * @param userId
     * @return
     */
    List<Task> queryTeaTaskByUserId(Integer userId);

    /**
     * 根据taskId更新任务最后一次收到邮件的时间
     * @param taskId
     * @param courseId
     * @param lastReceiveTime
     * @return
     */
    Integer updateReceiveTime(@Param("taskId") Integer taskId, @Param("courseId") Integer courseId, @Param("lastReceiveTime")Timestamp lastReceiveTime);

    /**
     * 结束此任务
     * @param taskId
     * @param courseId
     * @return
     */
    Integer endTask(@Param("taskId") Integer taskId, @Param("courseId") Integer courseId);
}
