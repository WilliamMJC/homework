package com.hzu.homework.sysback.homework.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="homework")
public class HomeworkModel implements Serializable {
	@Id
	private String uuid;
	private String delFlag;
	private String oprTime;
	private String createTime;
	private String title;
	private String teacherUuid;
	private String updateTime;
	private String context;
	private String courseUuid;
	private String submittedNum;
	@Transient
	private String allNum;
	private int times;
	@Transient
	private int likeNum;
	@Transient
	private String courseName;
	@Transient
	private String teacherName;
	@Transient
	private String commentNum;
	/*0未收取 1已收取*/
	private String state;
	private String fileName;
	private String fileSize;
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
	public String getTeacherUuid() {
		return teacherUuid;
	}
	public void setTeacherUuid(String teacherUuid) {
		this.teacherUuid = teacherUuid;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getCourseUuid() {
		return courseUuid;
	}
	public void setCourseUuid(String courseUuid) {
		this.courseUuid = courseUuid;
	}
	public String getSubmittedNum() {
		return submittedNum;
	}
	public void setSubmittedNum(String submittedNum) {
		this.submittedNum = submittedNum;
	}
	public String getAllNum() {
		return allNum;
	}
	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
}
