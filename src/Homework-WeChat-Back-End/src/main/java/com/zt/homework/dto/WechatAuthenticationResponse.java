package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WechatAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    @JsonProperty
    private final String accessToken;               // 授权token

    @JsonProperty
    private Boolean setting;                        // 个人设置

    @JsonProperty
    private Boolean workMail;                       // 工作邮箱

    public WechatAuthenticationResponse(String accessToken, Boolean setting, Boolean workMail) {
        this.accessToken = accessToken;
        this.setting = setting;
        this.workMail = workMail;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Boolean getSetting() {
        return setting;
    }

    public void setSetting(Boolean setting) {
        this.setting = setting;
    }

    public Boolean getWorkMail() {
        return workMail;
    }

    public void setWorkMail(Boolean workMail) {
        this.workMail = workMail;
    }
}
