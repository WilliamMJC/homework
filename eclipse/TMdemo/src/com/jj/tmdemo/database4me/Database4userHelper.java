package com.jj.tmdemo.database4me;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
//数据库助手类？
public class Database4userHelper extends SQLiteOpenHelper{
	//数据库版本 1？
	private static final int VERSION = 1;
	//四参构造函数 调用父类
	public Database4userHelper(Context context, String userLabelName, CursorFactory factory,
			int version) {
		super(context, userLabelName, factory, version);
	}
	//二参构造函数调用自身三参构造函数？
	public Database4userHelper(Context context,String userLabelName){
		this(context,userLabelName,VERSION);
	}
	//三参构造函数调用自身四参构造函数？
	public Database4userHelper(Context context,String userLabelName,int version){
		this(context, userLabelName,null,version);
	}
	//新建数据库助手类对象时候调用？建立对象时删除已存在表？
	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("create a Database");
		db.execSQL("drop table user");
	}

	//更新数据库的时候的时候调用？
	//如果表不存在就创建表。
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("update a Database");
		db.execSQL("create table if not exists users(userId int identity(1,1) primary key," +
				"account varchar(20),password varchar(20),identity varchar(10),nickname varchar(20)," +
				"schoolCode varchar(10),classCode varchar(10))");
	}
}
