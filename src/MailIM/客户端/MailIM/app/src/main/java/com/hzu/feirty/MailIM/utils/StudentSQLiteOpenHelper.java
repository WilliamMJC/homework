package com.hzu.feirty.MailIM.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017-6-24.
 */

public class StudentSQLiteOpenHelper extends SQLiteOpenHelper {
    public StudentSQLiteOpenHelper(Context context) {
        super(context,"student.db",null,2);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table student( id integer primary key autoincrement ,number varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
