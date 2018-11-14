package com.zt.homework.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zt.homework.Utils.CustomJsonDateDeserializer;

import java.sql.Timestamp;

public class Task {
    private Integer taskId;         // 任务id

    private Integer courseId;       // 课程Id

    private String taskName;        // 任务名

    private String taskDesc;        // 任务描述

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Timestamp startTime;    // 任务开始时间

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Timestamp endTime;      // 任务结束时间

    private Timestamp lastReceiveTime;     // 最后一封作业邮件发送过来的时间

    private Integer acceptType;      // 任务接受的文件类型

    private Boolean isTaskEnd;       // 任务是否结束

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getLastReceiveTime() {
        return lastReceiveTime;
    }

    public void setLastReceiveTime(Timestamp lastReceiveTime) {
        this.lastReceiveTime = lastReceiveTime;
    }

    public Integer getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(Integer acceptType) {
        this.acceptType = acceptType;
    }

    public Boolean getTaskEnd() {
        return isTaskEnd;
    }

    public void setTaskEnd(Boolean taskEnd) {
        isTaskEnd = taskEnd;
    }
}
