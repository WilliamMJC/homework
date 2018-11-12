package com.zt.homework.entity;

import java.sql.Timestamp;

public class SignIn {
    private Integer signInId;       // 签到id

    private Integer courseId;       // 课程id

    private Integer userId;         // 用户id

    private Boolean isSign;         // 是否签到

    private Timestamp signTime;     // 签到时间

    public Integer getSignInId() {
        return signInId;
    }

    public void setSignInId(Integer signInId) {
        this.signInId = signInId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    public Timestamp getSignTime() {
        return signTime;
    }

    public void setSignTime(Timestamp signTime) {
        this.signTime = signTime;
    }
}
