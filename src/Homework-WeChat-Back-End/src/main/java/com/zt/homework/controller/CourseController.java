package com.zt.homework.controller;

import com.zt.homework.Utils.ResultUtil;
import com.zt.homework.config.AppContext;
import com.zt.homework.dto.*;
import com.zt.homework.entity.Course;
import com.zt.homework.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 创建课程
     * @param courseInit
     * @return
     */
    @PutMapping(value = "/course")
    public ResponseEntity<Result> createCourse(@RequestBody Course courseInit) {
        Integer courseId = courseService.addCourse(courseInit);
        return ResponseEntity.ok(ResultUtil.success(courseId));
    }

    /**
     * 根据课程id更新课程信息
     * @param courseId
     * @return
     */
    @PutMapping(value = "/course/{courseId}")
    public ResponseEntity<Result> endCourse(@PathVariable("courseId") Integer courseId) {
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseEnd(true);
        courseService.updateCourse(course);
        return ResponseEntity.ok(ResultUtil.success());
    }

    /**
     * 获取所有用户教授的课程
     * @return
     */
    @GetMapping(value = "/course/tea")
    public ResponseEntity<Result> teaGetCourseList() {
        Integer userId = AppContext.getCurrentUserId();
        List<TeaCourseListItem> teaCourseListItems = courseService.getTeaCourseListItems(userId);
        return ResponseEntity.ok(ResultUtil.success(teaCourseListItems));
    }

    /**
     * 获取用户加入的课程列表
     * @return
     */
    @GetMapping(value = "/course/stu")
    public ResponseEntity<Result> stuGetCourseList() {
        Integer userId = AppContext.getCurrentUserId();
        List<StuCourseListItem> stuCourseListItems = courseService.getStuCourseListItems(userId);
        return ResponseEntity.ok(ResultUtil.success(stuCourseListItems));
    }

//    /**
//     * 用户加入课程
//     * @return
//     */
//    @PostMapping(value = "/course/{courseCode}")
//    public ResponseEntity<Result> joinCourse(@PathVariable String courseCode) {
//        Integer userId = AppContext.getCurrentUserId();
//        Integer courseId = courseService.userJoinCourse(userId, courseCode);
//        return ResponseEntity.ok(ResultUtil.success(courseId));
//    }

    @GetMapping(value = "/course/{courseId}")
    public ResponseEntity<Result> getCourseInfo(@PathVariable Integer courseId) {
        Integer userId = AppContext.getCurrentUserId();
        CourseDto courseDto = courseService.getCourseDto(userId, courseId);
        return ResponseEntity.ok(ResultUtil.success(courseDto));
    }

//    @GetMapping(value = "/v1/course/{courseId}")
//    public ResponseEntity<Result> getCourseInfoV1(@PathVariable Integer courseId) {
//        Integer userId = AppContext.getCurrentUserId();
//        CourseDto courseDto = courseService.getCourseDto(userId, courseId);
//        return ResponseEntity.ok(ResultUtil.success(courseDto));
//    }

    @DeleteMapping(value = "/course/{courseId}")
    public ResponseEntity<Result> deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourseByCourseId(courseId);
        return ResponseEntity.ok(ResultUtil.success());
    }
}
