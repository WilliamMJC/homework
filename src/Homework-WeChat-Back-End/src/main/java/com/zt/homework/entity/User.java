package com.zt.homework.entity;

public class User {
    private Integer userId;       // 用户id

    private String openid;    // 用户openid

    private String personalId;     // 用户学号/工号

    private String username;  // 用户名

    private String personalMail;    // 用户个人邮箱

    private String workMail;       // 用户作业邮箱

    private String workMailPwd;    // 用户作业邮箱验证码

    private String school;         // 用户所在学校

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonalMail() {
        return personalMail;
    }

    public void setPersonalMail(String personalMail) {
        this.personalMail = personalMail;
    }

    public String getWorkMail() {
        return workMail;
    }

    public void setWorkMail(String workMail) {
        this.workMail = workMail;
    }

    public String getWorkMailPwd() {
        return workMailPwd;
    }

    public void setWorkMailPwd(String workMailPwd) {
        this.workMailPwd = workMailPwd;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
}
