package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.SignIn;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignInDaoTest {

    @Autowired
    private SignInDao signInDao;

    @Test
    @Ignore
    public void initSignIn() {
        Integer affectedNum = signInDao.initSignIn(1, 1);
        assertSame(1, affectedNum);
    }

    @Test
    public void updateSignIn() {
        SignIn signIn = new SignIn();
        signIn.setCourseId(1);
        signIn.setSignInId(1);
        signIn.setUserId(7);
        signIn.setIsSign(true);
        signIn.setSignTime(DateUtil.Date2Timestamp(new Date()));

        Integer affectedNum = signInDao.updateSignIn(signIn);
        assertSame(1, affectedNum);
    }

    @Test
    public void querySignInByCourseId() {
        List<SignIn> signInList = signInDao.querySignInByCourseId(1);
        assertEquals(1, signInList.size());
    }

    @Test
    public void querySignInByCourseIdSignInId() {
        List<SignIn> signInList = signInDao.querySignInByCourseIdSignInId(1,1);
        assertEquals(1, signInList.size());
    }

    @Test
    public void querySignInByCourseIdUserId() {
        List<SignIn> signInList = signInDao.querySignInByCourseIdUserId(1, 7);
        assertEquals(1, signInList.size());
    }
}