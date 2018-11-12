package com.hzu.homework.sysback.comment.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="comment")
public class CommentModel {
	@Id
	private String uuid;
	private String delFlag;
	private String oprTime;
	private String createTime;
	private String homeworkUuid;
	private String formUuid;
	@Transient
	private String formUserName;
	private String toUuid;
	@Transient
	private String toUserName;
	private String content;
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
	public String getFormUuid() {
		return formUuid;
	}
	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
	}
	public String getFormUserName() {
		return formUserName;
	}
	public void setFormUserName(String formUserName) {
		this.formUserName = formUserName;
	}
	public String getToUuid() {
		return toUuid;
	}
	public void setToUuid(String toUuid) {
		this.toUuid = toUuid;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
