package com.hzu.feirty.MailIM.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import  com.hzu.feirty.MailIM.db.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-6-24.
 */

public class StudentDao {
    private StudentSQLiteOpenHelper helper;

    public StudentDao(Context context){
        helper =new StudentSQLiteOpenHelper(context);
    }
    public StudentDao(){
        helper =new StudentSQLiteOpenHelper(null);
    }
    public void insert(Student student) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if(!find(student.getNumber())) {
            db.execSQL("insert into student (number) values(?)", new String[]{student.getNumber()});
        }
        db.close();
    }
    public List<Student> queryAll(){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c =db.query("student",null,null,null,null,null,"number DESC");
        List<Student> list = new ArrayList<Student>();
        while(c.moveToNext()){
            long id =c.getLong(c.getColumnIndex("_id"));
            String number = c.getString(1);
            list.add(new Student(id,number));
        }
        c.close();
        db.close();
        return list;
    }

    public boolean find(String number){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("student", new String[]{"id", "number"}, "number=?", new String[]{number}, null, null, "id");
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            if (id!=null) {
                return true;
            }
        }
        return false;
    }

}
