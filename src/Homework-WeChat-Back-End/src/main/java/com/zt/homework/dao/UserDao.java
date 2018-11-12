package com.zt.homework.dao;

import com.zt.homework.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    /**
     * 查询所有用户
     * @return
     */
    List<User> getAllUser();

    /**
     * 创建用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    User queryUserByUserId(Integer userId);

    /**
     * 根据openid返回用户
     * @param openid
     * @return
     */
    User queryUserByOpenId(String openid);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据课程id查询教师类型的用户名
     * @param courseId
     * @return
     */
    String queryUserNameByCourseId(Integer courseId);


    /**
     * 查询所有教师类型的用户
     * @return
     */
    List<User> queryAllTeaTypeUser();

    /**
     * 根据courseId查询教师类型的用户
     * @param courseId
     * @return
     */
    User queryTeaUserByCourseId(Integer courseId);
}
