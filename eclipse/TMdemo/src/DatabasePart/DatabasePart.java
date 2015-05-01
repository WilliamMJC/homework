package DatabasePart;

import java.io.File;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class DatabasePart {
	SQLiteDatabase sqlDB;
	public UserTable User; 
	public FriendShipTable fsTable;
	public NewsTable newsTable;
	public DongTaiTable dtTable;
	//修改用户表并加消息表，
	
	public DatabasePart(String path,String db){
		String filepath = android.os.Environment.getExternalStorageDirectory()
				+ "/" + path;
		//���·���ļ����Ƿ����
		File file=new File(filepath);
		if(!file.exists()){
		    //�����ļ���path
			file.mkdirs();
		}
		String filename = filepath + "/" + db;
		//ָ����·���´���ݿ�
		sqlDB = SQLiteDatabase.openOrCreateDatabase(filename,null);
		//UserTable的成员函数要传一个参数，是一个dp对象，所以this
		User = new UserTable(this);
		fsTable = new FriendShipTable(this);
		newsTable = new NewsTable(this);
		dtTable = new DongTaiTable(this);
	//我要new一个消息表对象？

	}
	
	public void execSQL(String sql){
		sqlDB.execSQL(sql);
	}
	public Cursor query(String sql,String[] ss){
		
		return sqlDB.rawQuery(sql, ss);
	}
	
	public long insert(String table,ContentValues cv){
		return sqlDB.insert(table, null, cv);
	}
	
	public int updatebyid(String table,ContentValues cv,String id){
		if (table.equals("FriendShip")) {
			return sqlDB.update(table, cv, "fs_id = ?", new String[]{id});
		}else if (table.equals("News")) {
			return 999;
		}else {
			return sqlDB.update(table, cv, "account = ?", new String[]{id} );
		}
		
	}

	public int deletebyid(String table,String id){
		if (table.equals("News")) {
			return sqlDB.delete(table,  "news_id = ?", new String[]{id} );
		}else if (table.equals("FriendShip")) {
			return sqlDB.delete(table,  "account = ?", new String[]{id} );
		}else{
			return sqlDB.delete(table,  "account = ?", new String[]{id} );
		}
	}
	
	public void DataClose(){
		sqlDB.close();
	}
	
	
	
	
	
}