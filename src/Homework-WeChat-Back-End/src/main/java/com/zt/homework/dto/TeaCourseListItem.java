package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeaCourseListItem {
    @JsonProperty
    private Integer courseId;

    @JsonProperty
    private String courseName;

    @JsonProperty
    private String courseDesc;

    @JsonProperty
    private Boolean isCourseEnd;

    @JsonProperty
    private Integer memberSize;                 // 成员数

    @JsonProperty
    private Integer taskSize;                   // 任务数

//    @JsonProperty
//    private String courseCode;                 // 课程代号

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

    public Boolean getIsCourseEnd() {
        return isCourseEnd;
    }

    public void setIsCourseEnd(Boolean isCourseEnd) {
        this.isCourseEnd = isCourseEnd;
    }

    public Integer getMemberSize() {
        return memberSize;
    }

    public void setMemberSize(Integer memberSize) {
        this.memberSize = memberSize;
    }

    public Integer getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(Integer taskSize) {
        this.taskSize = taskSize;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

//    public String getCourseCode() {
//        return courseCode;
//    }
//
//    public void setCourseCode(String courseCode) {
//        this.courseCode = courseCode;
//    }
}
