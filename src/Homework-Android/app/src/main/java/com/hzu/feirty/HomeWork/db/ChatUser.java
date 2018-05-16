package com.hzu.feirty.HomeWork.db;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/25 0025.
 */

public class ChatUser implements Serializable {
    private String userUuid;
    private String userName;
    private String lastMsgTime;
    private String lastMsgContext;
    private String imagUrl;
    private String unReadNum;

    public  ChatUser(String userName,String lastMagContext,String lastMsgTime){
        this.userName=userName;
        this.lastMsgContext=lastMagContext;
        this.lastMsgTime=lastMsgTime;
    }
    public  ChatUser(){}

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getLastMsgContext() {
        return lastMsgContext;
    }

    public void setLastMsgContext(String lastMsgContext) {
        this.lastMsgContext = lastMsgContext;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public String getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(String unReadNum) {
        this.unReadNum = unReadNum;
    }
}
