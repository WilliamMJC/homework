package com.hzu.homework.sysback.message.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="message")
public class MessageModel implements Serializable{
	@Id
	private String uuid;
	private String delFlag;
	private String createTime;
	private String oprTime;
	private String content;
	private String formUuid;
	private String toUuid;
	/*0未读 1 已读*/
	private String state;
	/*消息的显示方*/
	private String belongUuid;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOprTime() {
		return oprTime;
	}
	public void setOprTime(String oprTime) {
		this.oprTime = oprTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBelongUuid() {
		return belongUuid;
	}
	public void setBelongUuid(String belongUuid) {
		this.belongUuid = belongUuid;
	}
}
