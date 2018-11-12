package com.hzu.homework.sysback.email.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student_email")
public class EmailModel implements Serializable {
	@Id
	private String uuid;
	private String delFlag;
	private String oprTime;
	private String createTime;
	private String title;
	private String context;
	private String attachmentName;
	private String sendTime;
	private String attachmentSize;
	private String studentUuid;
	private String sender;
	private String homeworkUuid;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getAttachmentSize() {
		return attachmentSize;
	}
	public void setAttachmentSize(String attachmentSize) {
		this.attachmentSize = attachmentSize;
	}
	public String getStudentUuid() {
		return studentUuid;
	}
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getHomeworkUuid() {
		return homeworkUuid;
	}
	public void setHomeworkUuid(String homeworkUuid) {
		this.homeworkUuid = homeworkUuid;
	}
	

}
