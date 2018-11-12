package com.zt.homework.entity;

public class SC {
    private Integer courseId;                // 课程id

    private String stuId;                    // 选择此课程的学生id

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
