package com.hzu.homework.sysback.matched.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="matched")
public class MatchedModel {
	@Id
	private String uuid;
	private String delFlag;
	private String oprTime;
	private String createTime;
	private String formUuid;
	private String toUuid;
	private String belongUuid;
	@Transient
	private String formName;	
	@Transient
	private String toName;
	@Transient
	private String UnreadNum;
	@Transient
	private String content;
	@Transient
	private String lastTime;
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
	public String getFormUuid() {
		return formUuid;
	}
	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
	}
	public String getToUuid() {
		return toUuid;
	}
	public void setToUuid(String toUuid) {
		this.toUuid = toUuid;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getUnreadNum() {
		return UnreadNum;
	}
	public void setUnreadNum(String unreadNum) {
		UnreadNum = unreadNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getBelongUuid() {
		return belongUuid;
	}
	public void setBelongUuid(String belongUuid) {
		this.belongUuid = belongUuid;
	}
	

}
