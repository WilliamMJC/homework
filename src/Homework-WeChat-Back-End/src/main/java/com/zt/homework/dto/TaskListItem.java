package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskListItem {
    @JsonProperty
    private Integer taskId;         // 任务id

    @JsonProperty
    private Integer courseId;       // 课程id

    @JsonProperty
    private String taskName;        // 任务名

    @JsonProperty
    private String startTime;    // 任务开始时间

    @JsonProperty
    private String endTime;      // 任务结束时间

    @JsonProperty
    private Boolean isTaskEnd;       // 任务是否结束

    @JsonProperty
    private Integer receive;         // 接受的作业数

    @JsonProperty
    private Integer total;           // 学生总数

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

    public Boolean getIsTaskEnd() {
        return isTaskEnd;
    }

    public void setIsTaskEnd(Boolean isTaskEnd) {
        this.isTaskEnd = isTaskEnd;
    }

    public Integer getReceive() {
        return receive;
    }

    public void setReceive(Integer receive) {
        this.receive = receive;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
