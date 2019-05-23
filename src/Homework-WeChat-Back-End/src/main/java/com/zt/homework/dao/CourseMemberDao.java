package com.zt.homework.dao;

import com.zt.homework.entity.CourseMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMemberDao {
    /**
     * 创建一条cm记录
     *
     * @param cm
     * @return
     */
    Integer insertCM(CourseMember cm);

    /**
     * 是否有cm记录
     *
     * @param courseId
     * @param userId
     * @return
     */
    Integer hasCM(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    /**
     * 删除cm记录
     *
     * @param courseId
     * @param userId
     * @return
     */
    Integer deleteCM(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    /**
     * 查询用户在此课程上的权限
     *
     * @param courseId
     * @param userId
     * @return
     */
    Integer queryPermissionByCM(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    /**
     * 根据课程id查询cm记录
     *
     * @param courseId
     * @return
     */
    List<CourseMember> queryCMByCourseId(Integer courseId);

    /**
     * 根据课程id查询学生人数
     * @param courseId
     * @return
     */
    Integer queryStuCMByCourseId(Integer courseId);

    /**
     * 根据课程id查询cm记录,分页查询
     *
     * @param courseId
     * @return
     */
    List<CourseMember> queryCMByCourseIdWithPage(@Param("courseId") Integer courseId, @Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    /**
     * 根据课程id查询cm记录,分页查询并排序
     *
     * @param courseId
     * @return
     */
    List<CourseMember> queryCMByCourseIdWithPage2(@Param("courseId") Integer courseId, @Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    /**
     * 根courseId和userId查询cm
     *
     * @param courseId
     * @param userId
     * @return
     */
    CourseMember queryCMByCourseIdUserId(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    /**
     * 查询课程的所有学生学号
     *
     * @param courseId
     * @return
     */
    List<String> queryPersonalIdByCourseId(Integer courseId);

    /**
     * 查询用户userId
     *
     * @param courseId
     * @param personalId
     * @return
     */
    Integer queryUserIdByCourseIdPersonalId(@Param("courseId") Integer courseId, @Param("personalId") String personalId);

    /**
     * 查询课程教师
     * @param courseId
     * @return
     */
    CourseMember queryTeaByCourseId(Integer courseId);
}
