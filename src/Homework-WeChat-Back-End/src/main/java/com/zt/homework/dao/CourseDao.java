package com.zt.homework.dao;

import com.zt.homework.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDao {
    /**
     * 保存课程
     * @param course
     * @return 修改的行数
     */
    int insertCourse(Course course);

    /**
     * 根据课程id查找课程
     * @param courseId
     * @return
     */
    Course queryCourseByCourseId(Integer courseId);


    /**
     * 根据课程id删除课程
     * @param courseId
     * @return
     */
    int deleteCourseByCourseId(Integer courseId);

    /**
     * 根据课程id更新课程
     * @param course
     * @return
     */
    int updateCourseByCourseId(Course course);

    /**
     * 根据用户id查找用户创建的课程
     * @param userId
     * @return
     */
    List<Course> teaQueryCourseByUserId(Integer userId);

    /**
     * 根据用户id查找用户加入的课程
     * @param userId
     * @return
     */
    List<Course> stuQueryCoursesByUserId(Integer userId);

    /**
     * 根据courseCode查找课程
     * @param courseCode
     * @return
     */
    Course queryCourseByCourseCode(String courseCode);
}
