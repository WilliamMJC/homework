package com.zt.homework.entity;

import java.sql.Timestamp;

public class Construction {
    private Integer taskId;            // 任务id

    private Integer courseId;          // 课程id

    private String constructionName;   // 附件名

    private Timestamp downloadTime;    // 下载时间

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public void setConstructionName(String constructionName) {
        this.constructionName = constructionName;
    }

    public Timestamp getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Timestamp downloadTime) {
        this.downloadTime = downloadTime;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
