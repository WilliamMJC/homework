package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HomeworkDto {
    @JsonProperty
    private Integer userId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String sentDate;

    @JsonProperty
    private String fileType;

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

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
