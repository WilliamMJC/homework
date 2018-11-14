package com.zt.homework.dao;

import com.zt.homework.entity.SignIn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignInDao {
    /**
     * 初始化签到记录
     * @param courseId
     * @param signInId
     * @return
     */
    Integer initSignIn(@Param("courseId") Integer courseId, @Param("signInId") Integer signInId);


    /**
     * 更新签到记录
     * @param signIn
     * @return
     */
    Integer updateSignIn(SignIn signIn);

    /**
     * 查询该课程的所有签到记录
     * @param courseId
     * @return
     */
    List<SignIn> querySignInByCourseId(Integer courseId);

    /**
     * 查询课程某次具体的签到记录
     * @param courseId
     * @param signInId
     * @return
     */
    List<SignIn> querySignInByCourseIdSignInId(@Param("courseId") Integer courseId, @Param("signInId") Integer signInId);

    /**
     * 用户查询自己在该课程下的签到记录
     * @param courseId
     * @param userId
     * @return
     */
    List<SignIn> querySignInByCourseIdUserId(@Param("courseId") Integer courseId, @Param("userId") Integer userId);
}
