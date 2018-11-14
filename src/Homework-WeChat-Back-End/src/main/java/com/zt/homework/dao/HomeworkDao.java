package com.zt.homework.dao;

import com.zt.homework.entity.Homework;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkDao {
    /**
     * 插入一条作业记录
     * @param homework
     * @return
     */
    Integer insertHomework(Homework homework);

    /**
     * 根据taskId和courseId查询作业记录
     * @param taskId
     * @param courseId
     * @return
     */
    List<Homework> queryHomeworkByTaskIdCourseId(@Param("taskId") Integer taskId, @Param("courseId") Integer courseId);

    /**
     * 根据taskId、userId和courseId查找作业
     * @param taskId
     * @param userId
     * @param courseId
     * @return
     */
    Homework queryHomework(@Param("taskId") Integer taskId, @Param("userId") Integer userId, @Param("courseId") Integer courseId);

    /**
     * 更新作业记录
     * @param homework
     * @return
     */
    Integer updateHomework(Homework homework);

    /**
     * 根据courseId和userId查询作业记录
     * @param courseId
     * @param userId
     * @return
     */
    List<Homework> queryHomeworkByCourseIdUserId(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

}
