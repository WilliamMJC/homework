package com.zt.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class CourseDto {
    @JsonProperty
    private Integer courseId;

    @JsonProperty
    private String courseName;

    @JsonProperty
    private String courseDesc;

    @JsonProperty
    private Boolean isCourseEnd;

    @JsonProperty
    private Boolean isPublic;


    @JsonProperty
    private List<String> stuIdList;

    @JsonProperty
    private List<T> taskList;

    @JsonProperty
    private List<T> resourceList;

    @JsonProperty
    private UserDto teacher;

    @JsonProperty
    private List<UserDto> stuList;

    @JsonProperty
    private Boolean isTeacher;

    @JsonProperty
    private Boolean isStudent;

    @JsonProperty
    private Integer stuLength;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public Boolean getIsCourseEnd() {
        return isCourseEnd;
    }

    public void setIsCourseEnd(Boolean isCourseEnd) {
        this.isCourseEnd = isCourseEnd;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

//    public String getCourseCode() {
//        return courseCode;
//    }
//
//    public void setCourseCode(String courseCode) {
//        this.courseCode = courseCode;
//    }

    public List<String> getStuIdList() {
        return stuIdList;
    }

    public void setStuIdList(List<String> stuIdList) {
        this.stuIdList = stuIdList;
    }

    public List<T> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<T> taskList) {
        this.taskList = taskList;
    }

    public List<T> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<T> resourceList) {
        this.resourceList = resourceList;
    }

    public List<UserDto> getStuList() {
        return stuList;
    }

    public void setStuList(List<UserDto> stuList) {
        this.stuList = stuList;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public UserDto getTeacher() {
        return teacher;
    }

    public void setTeacher(UserDto teacher) {
        this.teacher = teacher;
    }

    public Integer getStuLength() {
        return stuLength;
    }

    public void setStuLength(Integer stuLength) {
        this.stuLength = stuLength;
    }
}
