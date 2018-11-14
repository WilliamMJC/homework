package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDto {
    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String personalId;

    @JsonProperty
    private String school;

    @JsonProperty
    private String mail;

    @JsonProperty
    private List<HomeworkDto> homeworkDtoList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<HomeworkDto> getHomeworkDtoList() {
        return homeworkDtoList;
    }

    public void setHomeworkDtoList(List<HomeworkDto> homeworkDtoList) {
        this.homeworkDtoList = homeworkDtoList;
    }
}
