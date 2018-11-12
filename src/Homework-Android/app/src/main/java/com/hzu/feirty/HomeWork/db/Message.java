package com.hzu.feirty.HomeWork.db;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class Message implements Serializable {
    private String userName;
    private String userUuid;
    private String imgUrl;
    private String createTime;
    private String context;
    /*type  1:用户发送的 2:用户接收的*/
    private String type;
    public  Message(String context,String createTime,String type){
        this.context=context;
        this.createTime=createTime;
        this.type=type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
