package com.zt.homework.dao;

import com.zt.homework.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    public void insertCourse() {
        Course course = new Course();
        course.setCourseName("test1");
//        course.setCourseDesc("this is a test");
//        course.setPublic(false);

        int affectedNum = courseDao.insertCourse(course);
        System.out.println(course.getCourseId());
        assertEquals(1, affectedNum);
    }

    @Test
    public void queryCourseByCourseId() {
        assertEquals("test", courseDao.queryCourseByCourseId(1).getCourseName());
    }

    @Test
    public void deleteCourseByCourseId() {
        int affectedNum = courseDao.deleteCourseByCourseId(3);
        assertEquals(1, affectedNum);
    }

    @Test
    public void updateCourseByCourseId() {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseDesc("this is another test");
        int affectedNum = courseDao.updateCourseByCourseId(course);
        assertEquals(1, affectedNum);
    }

    @Test
    public void teaQueryCourseByUserId() {
    }

    @Test
    public void stuQueryCoursesByUserId() {
    }
}