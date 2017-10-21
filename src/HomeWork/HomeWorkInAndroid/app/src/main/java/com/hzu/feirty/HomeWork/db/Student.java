package com.hzu.feirty.HomeWork.db;

/**
 * Created by Administrator on 2017-6-24.
 */

public class Student {
    private String number;
    private String name;
    private  Long id;
    public Student(Long id, String number){
        super();
        this.id=id;
        this.number= number;
    }
    public Student(String number){
        super();
        this.number= number;
    }
    public Student(){
        super();
    }
    public String  getNumber(){
        return number;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id= id;
    }
    public String getName(){ return name;}
    public void setName(String name){
        this.name = name;
    }
}
