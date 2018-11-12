package com.hzu.homework.sysback.teacourserel.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="teacher_course_rel")
public class TeacherCourseRelModel implements Serializable{
	@Id
	private String uuid;
	private String delFlag;
	private String teacherUuid;
	private String courseUuid;
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
	
	
	public String getTeacherUuid() {
		return teacherUuid;
	}
	public void setTeacherUuid(String teacherUuid) {
		this.teacherUuid = teacherUuid;
	}
	public String getCourseUuid() {
		return courseUuid;
	}
	public void setCourseUuid(String courseUuid) {
		this.courseUuid = courseUuid;
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
