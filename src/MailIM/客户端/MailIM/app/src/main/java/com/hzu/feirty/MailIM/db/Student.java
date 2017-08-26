package com.hzu.feirty.MailIM.db;

/**
 * Created by Administrator on 2017-6-24.
 */

public class Student {
    private String number;
    private  Long id;
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
}
