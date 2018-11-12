package com.hzu.feirty.HomeWork.db;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class User {
    private String uuid;
    private String delFlag;
    private String loginName;
    private String userName;
    private String email;
    private String emailPwd;
    private String loginPwd;
    private String oprTime;
    private String createTime;
    //1学生2教师
    private String userType;
    private String privEmail;
    private String token;
    private String verifyCode;
    private String codeTimes;
    private String lastCodeTime;
    private String note;
    private String profileUrl;
    private String userState;
    //学生学号
    private String studentNo;
    private String schoolUuid;

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailPwd() {
        return emailPwd;
    }

    public void setEmailPwd(String emailPwd) {
        this.emailPwd = emailPwd;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getOprTime() {
        return oprTime;
    }

    public void setOprTime(String oprTime) {
        this.oprTime = oprTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPrivEmail() {
        return privEmail;
    }

    public void setPrivEmail(String privEmail) {
        this.privEmail = privEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCodeTimes() {
        return codeTimes;
    }

    public void setCodeTimes(String codeTimes) {
        this.codeTimes = codeTimes;
    }

    public String getLastCodeTime() {
        return lastCodeTime;
    }

    public void setLastCodeTime(String lastCodeTime) {
        this.lastCodeTime = lastCodeTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSchoolUuid() {
        return schoolUuid;
    }

    public void setSchoolUuid(String schoolUuid) {
        this.schoolUuid = schoolUuid;
    }
}
