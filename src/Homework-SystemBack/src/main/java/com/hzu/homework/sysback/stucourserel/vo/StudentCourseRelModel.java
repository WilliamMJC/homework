package com.hzu.homework.sysback.stucourserel.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student_course_rel")
public class StudentCourseRelModel implements Serializable {
	@Id
	private String uuid;
	private String studentUuid;
	private String studentNo;
	private String courseUuid;
	private String delFlag;
	private String oprTime;
	private String createTime;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getStudentUuid() {
		return studentUuid;
	}
	public void setStudentUuid(String studentUuid) {
		this.studentUuid = studentUuid;
	}
	public String getCourseUuid() {
		return courseUuid;
	}
	public void setCourseUuid(String courseUuid) {
		this.courseUuid = courseUuid;
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
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	
	

}
