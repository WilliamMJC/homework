package com.zt.homework.dto;

import java.util.List;

public class TaskDto {
    private Integer taskId;         // 任务id

    private Integer courseId;       // 课程id

    private String courseName;     // 课程名

    private String taskName;        // 任务名

    private String taskDesc;        // 任务描述

    private String startTime;    // 任务开始时间

    private String endTime;      // 任务结束时间

    private Integer acceptType;      // 任务接受的文件类型

    private List<HomeworkDto> receive;  // 收到的作业

    private Integer total;           // 总数

    private Boolean isTeacher;

    private Boolean isStudent;

    private String personalId;

    private String stuName;

    private String teaMail;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(Integer acceptType) {
        this.acceptType = acceptType;
    }

    public List<HomeworkDto> getReceive() {
        return receive;
    }

    public void setReceive(List<HomeworkDto> receive) {
        this.receive = receive;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean IsTeacher) {
        isTeacher = IsTeacher;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean IsStudent) {
        isStudent = IsStudent;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeaMail() {
        return teaMail;
    }

    public void setTeaMail(String teaMail) {
        this.teaMail = teaMail;
    }
}
