package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CourseInit {
    @JsonProperty
    private String courseName;           // 课程名

    @JsonProperty
    private String courseDesc;           // 课程描述

    @JsonProperty
    private Boolean isPublic;            // 课程是否公开

    @JsonProperty
    private List<String> stuIdList;      // 允许加入的学生列表

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

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        isPublic = isPublic;
    }

    public List<String> getStuIdList() {
        return stuIdList;
    }

    public void setStuIdList(List<String> stuIdList) {
        this.stuIdList = stuIdList;
    }
}
