package com.zt.homework.dto;

import java.util.List;

public class EndTaskDto {
    private Integer taskId;         // 任务id

    private Integer courseId;       // 课程id

    private String taskName;        // 任务名

    private String startTime;    // 任务开始时间

    private String endTime;      // 任务结束时间

    private List<HomeworkDto> homeworkDtoList;  // 所有作业提交情况

    private Integer receive;      // 提交人数

    private Integer unReceive;    // 未提交人数

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

    public List<HomeworkDto> getHomeworkDtoList() {
        return homeworkDtoList;
    }

    public void setHomeworkDtoList(List<HomeworkDto> homeworkDtoList) {
        this.homeworkDtoList = homeworkDtoList;
    }

    public Integer getReceive() {
        return receive;
    }

    public void setReceive(Integer receive) {
        this.receive = receive;
    }

    public Integer getUnReceive() {
        return unReceive;
    }

    public void setUnReceive(Integer unReceive) {
        this.unReceive = unReceive;
    }
}
