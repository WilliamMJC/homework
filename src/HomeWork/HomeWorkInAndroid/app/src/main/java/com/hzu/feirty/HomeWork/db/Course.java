package com.hzu.feirty.HomeWork.db;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-10-11.
 */

public class Course implements Serializable {
    private String name;
    private String works;
    private String students;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStudents() {
        return students;
    }
    public void setStudents(String students) {
        this.students = students;
    }
    public String getWorks() {
        return works;
    }
    public void setWorks(String works) {
        this.works = works;
    }
}
