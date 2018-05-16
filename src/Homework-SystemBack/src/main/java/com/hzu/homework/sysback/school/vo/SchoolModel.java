package com.hzu.homework.sysback.school.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="school")
public class SchoolModel implements Serializable {
	@Id
	private String uuid;
	private String schoolName;
	private String note;
	private String createTime;
	private String delFlag;
	private String schoolState;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getSchoolState() {
		return schoolState;
	}
	public void setSchoolState(String schoolState) {
		this.schoolState = schoolState;
	}
	
	
}
