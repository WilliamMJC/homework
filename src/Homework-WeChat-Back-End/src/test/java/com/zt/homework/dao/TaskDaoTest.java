package com.zt.homework.dao;

import com.zt.homework.Utils.DateUtil;
import com.zt.homework.entity.Task;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskDaoTest {

    @Autowired
    private TaskDao taskDao;

    @Test
    @Ignore
    public void insertTask() {
        Task task = new Task();
        task.setCourseId(4);
        task.setTaskName("test");
        task.setTaskDesc("with description");
        task.setStartTime(DateUtil.string2Timestamp("2018-08-15 11:11"));
        task.setEndTime(DateUtil.string2Timestamp("2018-08-20 11:11"));
        task.setAcceptType(4);

        int affectedNum = taskDao.insertTask(task);
        assertEquals(1, affectedNum);
    }

    @Test
    public void queryTaskByTaskId() {
//        Task task = taskDao.queryTaskByTaskId(1);
//        assertEquals("test", task.getTaskName());
    }

    @Test
    public void queryTaskByCourseId() {
        List<Task> tasks = taskDao.queryTaskByCourseId(1);
        assertEquals(1, tasks.size());
    }

    @Test
    public void queryTaskByUserId() {
        List<Task> tasks = taskDao.queryTaskByUserId(1);
        assertSame(1, tasks.get(0).getTaskId());
    }

    @Test
    public void updateReceiveTimeByTaskId() {
        Timestamp lastReceiveTime = DateUtil.Date2Timestamp(new Date());
//        int affectedNum = taskDao.updateReceiveTimeByTaskId(1, lastReceiveTime);
//        assertEquals(1, affectedNum);
    }

    @Test
    public void endTask() {
//        int affectedNum = taskDao.endTask(2);
//        assertEquals(1, affectedNum);
    }
}