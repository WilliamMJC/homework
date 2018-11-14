package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.Homework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkDaoTest {

    @Autowired
    private HomeworkDao homeworkDao;

    @Test
    public void insertHomework() {
        Homework homework = new Homework();
        homework.setTaskId(2);
        homework.setUserId(1);
        homework.setFileName("test fileName");
        homework.setSentDate(DateUtil.Date2Timestamp(new Date()));

        int insertNum = homeworkDao.insertHomework(homework);
        assertEquals(1, insertNum);
    }

    @Test
    public void queryHomeworkByTaskId() {
//        List<Homework> homeworkList = homeworkDao.queryHomeworkByTaskId(1);
//        assertEquals(1, homeworkList.size());
    }

    @Test
    public void queryHomeworkByTaskIdUserId() {
//        Homework homework = homeworkDao.queryHomeworkByTaskIdUserId(1, 7);
//        assertEquals("test fileName", homework.getFileName());
    }

    @Test
    public void updateHomework() {
        Homework homework = new Homework();
        homework.setTaskId(1);
        homework.setUserId(7);
        homework.setSentDate(DateUtil.Date2Timestamp(new Date()));
        homework.setFileName("test fileName1");
        homework.setScore(100);

        int num = homeworkDao.updateHomework(homework);
        assertEquals(1, num);

    }
}