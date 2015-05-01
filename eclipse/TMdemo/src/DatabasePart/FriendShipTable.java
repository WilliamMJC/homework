package DatabasePart;

import android.content.ContentValues;



public class FriendShipTable {
	DatabasePart dp;
	
	private String iPositiveU = "PositiveU";
	private String iNegativeU = "NegativeU";
	private String iFSstate = "FSstate";
	

	public FriendShipTable(DatabasePart dp){
		this.dp=dp;
		create();
	}
	
	private void create(){
		String sqlcreate="Create Table IF NOT EXISTS FriendShip( fs_id INTEGER PRIMARY KEY AUTOINCREMENT,"
	                      + iPositiveU + " Varchar(20),"+ iNegativeU + " Varchar(20)," + iFSstate + " Varchar(4))";
		
		dp.execSQL(sqlcreate);
	}
	//�ڽ�ʦ���в���һ�������
	public long insert(FriendShip friendShip){
		ContentValues cv=new ContentValues();
		//cv.put(iUSERID, user.userID);
		cv.put(iPositiveU, friendShip.PositiveU);
		cv.put(iNegativeU, friendShip.NegativeU);
		cv.put(iFSstate, friendShip.FSstate);

		
		return dp.insert("FriendShip", cv);
	}
	
	public long update(Integer id,FriendShip friendShip){
		ContentValues cv=new ContentValues();
		cv.put(iPositiveU, friendShip.PositiveU);
		cv.put(iNegativeU, friendShip.NegativeU);
		cv.put(iFSstate, friendShip.FSstate);
		return dp.updatebyid("FriendShip", cv, id.toString());
	}
	
	public long delete(Integer id){
		return dp.deletebyid("FriendShip", id.toString());
	}
	
	
}
