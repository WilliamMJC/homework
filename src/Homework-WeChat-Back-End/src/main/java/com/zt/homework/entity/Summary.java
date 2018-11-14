package com.zt.homework.entity;

import java.sql.Timestamp;

public class Summary {
    private Integer courseId;          // 课程id

    private String summaryName;        // 总结名

    private Timestamp downloadTime;       // 下载时间

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getSummaryName() {
        return summaryName;
    }

    public void setSummaryName(String summaryName) {
        this.summaryName = summaryName;
    }

    public Timestamp getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Timestamp downloadTime) {
        this.downloadTime = downloadTime;
    }
}
