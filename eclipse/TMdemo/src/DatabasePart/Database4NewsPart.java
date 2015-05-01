//package DatabasePart;
//
//import java.io.File;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//public class Database4NewsPart {
//	SQLiteDatabase sqlDB;
//	public NewsTable News;
//	
//	//修改用户表并加消息表，
//	
//	public Database4NewsPart(String path,String db){
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
//		//UserTable的成员函数要传一个参数，是一个dp对象，所以this
//	
//		News = new NewsTable(this);
//		//我要new一个消息表对象？
//
//	}
//	
//	public void execSQL(String sql){
//		sqlDB.execSQL(sql);
//	}//
//	public Cursor query(String sql,String[] ss){
//		
//		return sqlDB.rawQuery(sql, ss);
//	}
//	
//	public long insert(String table,ContentValues cv){
//		return sqlDB.insert(table, null, cv);
//	}
//	public int updatebynewsid(String table,ContentValues cv,String id){
//		return sqlDB.update(table, cv, "iTime = ?", new String[]{id} );
//	}
//	public int deletebynewsid(String table,String id){
//		return sqlDB.delete(table,  "iTime = ?", new String[]{id} );
//	}
//	public void DataClose(){
//		sqlDB.close();
//	}
//	
//	
//	
//	
//	
//}