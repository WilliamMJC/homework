package com.zt.homework.dao;

import com.zt.homework.entity.CourseMember;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseMemberDaoTest {

    @Autowired
    private CourseMemberDao courseMemberDao;

    @Test
    @Ignore
    public void insertCM() {
        CourseMember cm = new CourseMember();
        cm.setCourseId(4);
        cm.setUserId(7);
        cm.setType("teacher");
        cm.setPermission(4);

        int affectedNum = courseMemberDao.insertCM(cm);
        assertEquals( 1, affectedNum);
    }

    @Test
    public void hasCM() {
        Integer num = courseMemberDao.hasCM(4, 7);
        assertSame(1, num);
    }

    @Test
    @Ignore
    public void deleteCM() {
        int affectedNum = courseMemberDao.deleteCM(4, 1);
        assertEquals(1, affectedNum);
    }

    @Test
    public void queryPermissionByCM() {
        int permission = courseMemberDao.queryPermissionByCM(4, 7);
        assertSame(4, permission);
    }
}