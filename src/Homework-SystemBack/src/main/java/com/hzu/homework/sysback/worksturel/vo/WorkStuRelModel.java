package com.hzu.homework.sysback.worksturel.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="homework_student_rel")
public class WorkStuRelModel implements Serializable{
	@Id
	private String uuid;
	private String delFlag;
	private String homeworkUuid;
	private String studentNo;
	private String oprTime;
	private String createTime;
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
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getHomeworkUuid() {
		return homeworkUuid;
	}
	public void setHomeworkUuid(String homeworkUuid) {
		this.homeworkUuid = homeworkUuid;
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
	
}
