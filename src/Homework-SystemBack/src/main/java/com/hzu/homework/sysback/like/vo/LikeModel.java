package com.hzu.homework.sysback.like.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="like_rel")
public class LikeModel implements Serializable{
	@Id
	private String uuid;
	private String delFlag;
	private String oprTime;
	private String createTime;
	private String homeworkUuid;
	private String userUuid;
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
	public String getHomeworkUuid() {
		return homeworkUuid;
	}
	public void setHomeworkUuid(String homeworkUuid) {
		this.homeworkUuid = homeworkUuid;
	}
	public String getUserUuid() {
		return userUuid;
	}
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
}
