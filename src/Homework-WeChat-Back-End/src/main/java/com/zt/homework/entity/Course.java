package com.zt.homework.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {
    private Integer courseId;            // 课程id

    private String courseName;           // 课程名

    private String courseDesc;           // 课程描述

    private Boolean isCourseEnd;         // 课程是否结束

    @JsonProperty
    private Boolean isPublic;            // 课程是否公开

//    private String CourseCode;           // 课程代号

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public Boolean getCourseEnd() {
        return isCourseEnd;
    }

    public void setCourseEnd(Boolean courseEnd) {
        isCourseEnd = courseEnd;
    }

    public Boolean getPublic() {
        return isPublic;
    }
//
    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

//    public String getCourseCode() {
//        return CourseCode;
//    }
//
//    public void setCourseCode(String courseCode) {
//        CourseCode = courseCode;
//    }
}
