package com.zt.homework.dto;

public class TotalTaskDto {

    private Integer courseId;       // 课程id

    private String taskName;        //课程名称

    private Integer totalCM;        //参与该课程的人数

    private Integer total;           // 总数

    private Integer receive;         //接收到的作业总数

    public Integer getTotalCM() {
        return totalCM;
    }

    public void setTotalCM(Integer totalCM) {
        this.totalCM = totalCM;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getReceive() {
        return receive;
    }

    public void setRecive(Integer receive) {
        this.receive = receive;
    }
}
