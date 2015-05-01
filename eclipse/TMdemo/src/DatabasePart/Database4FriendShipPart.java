//package DatabasePart;
//
//import java.io.File;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//
//
//public class Database4FriendShipPart {
//	SQLiteDatabase sqlDB;
//	public FriendShipTable FriendShip; 
//	
//	
//	public Database4FriendShipPart(String path,String db){
//		String filepath = android.os.Environment.getExternalStorageDirectory()
//				+ "/" + path;
//		//���·���ļ����Ƿ����
//		File file=new File(filepath);
//		if(!file.exists()){
//		    //�����ļ���path
//			file.mkdirs();
//		}
//		String filename = filepath + "/" + db;
//		//ָ����·���´���ݿ�
//		sqlDB = SQLiteDatabase.openOrCreateDatabase(filename,null);
//		FriendShip = new FriendShipTable(this);
//	//我要new一个消息表对象？
//
//	}
//	
//	public void execSQL(String sql){
//		sqlDB.execSQL(sql);
//	}
//	public Cursor query(String sql,String[] ss){	
//		return sqlDB.rawQuery(sql, ss);
//	}
//	public long insert(String table,ContentValues cv){
//		return sqlDB.insert(table, null, cv);
//	}
//	public int updatebyid(String table,ContentValues cv,String id){
//		return sqlDB.update(table, cv, "account = ?", new String[]{id} );
//	}
//	public int deletebyid(String table,String id){
//		return sqlDB.delete(table,  "account = ?", new String[]{id} );
//	}
//	public void DataClose(){
//		sqlDB.close();
//	}
//}