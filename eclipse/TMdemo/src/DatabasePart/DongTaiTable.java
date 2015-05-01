package DatabasePart;

import android.content.ContentValues;

public class DongTaiTable {
	DatabasePart dp;
	//private String iDongTaiID="DongTaiId";
	private String IPUBUSER="pubUser";
	private String iPUBTIME="pubTime";
	private String iDTCONTENT="dtContent";
	private String iDTPIC="dtPic";

	

	public DongTaiTable(DatabasePart dp){
		this.dp=dp;
		create();
	}
	//��������ʦ
	private void create(){
		String sqlcreate="Create Table IF NOT EXISTS DongTai(dt_id INTEGER PRIMARY KEY AUTOINCREMENT,"
	                      //+ iDongTaiID + " Varchar(20)," 
	                      + IPUBUSER + " Varchar(20),"+ iPUBTIME + " Varchar(20),"
	                      + iDTCONTENT + " Varchar(10)," + iDTPIC + " Varchar(20)"
	                      + " )";
		//nVarchar(20))
	                      //+ iFRIENDSID + " text
		dp.execSQL(sqlcreate);
	}
	//�ڽ�ʦ���в���һ�������
	public long insert(DongTai dongtai){
		ContentValues cv=new ContentValues();
		//cv.put(iDongTaiID, DongTai.DongTaiID);
		cv.put(IPUBUSER, dongtai.pubUser);
		cv.put(iPUBTIME, dongtai.pubTime);
		cv.put(iDTCONTENT, dongtai.dtContent);
		cv.put(iDTPIC, dongtai.dtPic);


		
		return dp.insert("DongTai", cv);
	}
	
	public long update(Integer id,DongTai dongtai){
		ContentValues cv=new ContentValues();
		//cv.put(iDongTaiID, DongTai.DongTaiID);
		cv.put(IPUBUSER, dongtai.pubUser);
		cv.put(iPUBTIME, dongtai.pubTime);
		cv.put(iDTCONTENT, dongtai.dtContent);
		cv.put(iDTPIC, dongtai.dtPic);


		return dp.updatebyid("DongTai", cv, id.toString());
	}
	
	public long delete(Integer id){
		return dp.deletebyid("DongTai", id.toString());
	}
	
	
}
