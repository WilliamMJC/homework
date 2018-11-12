package com.zt.homework.dao;

import com.zt.homework.entity.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class userDaoTest {

    @Autowired
    private UserDao UserDao;

    @Test
    public void getAllUser() {
        List<User> userList = UserDao.getAllUser();
        assertEquals(2, userList.size());
    }

    @Test
    @Ignore
    public void insertUser() {
        User user = new User();
        user.setOpenid("2");
        int effctedNum = UserDao.insertUser(user);
        System.out.println(user.getUserId());
        assertEquals(1, effctedNum);
    }

    @Test
    public void queryUserByUserId() {
        User user = UserDao.queryUserByUserId(1);
//        assertEquals("1", user.getOpenid());
        System.out.println(user.getUsername());
    }

    @Test
    public void queryUserByOpenId() {
        User user = UserDao.queryUserByOpenId("2");
        assertSame(user.getUserId(), 7);
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setUserId(1);
        user.setPersonalId("1111");
        user.setPersonalMail("110347@qq.com");
        user.setWorkMail("dfjaoieur@qq.com");
        user.setWorkMailPwd("dfjla;sdfj");
        user.setSchool("惠州学院");

        int num = UserDao.updateUser(user);
        assertEquals(1, num);
    }
}