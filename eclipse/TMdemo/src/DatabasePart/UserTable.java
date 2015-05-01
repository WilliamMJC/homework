package DatabasePart;

import android.content.ContentValues;



public class UserTable {
	DatabasePart dp;
	//private String iUSERID="userId";
	private String iACCOUNT="account";
	private String iPASSWORD="password";
	private String iIDENTITY="identity";
	private String iNICKNAME="nickname";
	private String iSCHOOL="school";
	private String inoCLASS="noclass";
	

	public UserTable(DatabasePart dp){
		this.dp=dp;
		create();
	}
	//��������ʦ
	private void create(){
		String sqlcreate="Create Table IF NOT EXISTS User(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
	                      //+ iUSERID + " Varchar(20)," 
	                      + iACCOUNT + " Varchar(20),"+ iPASSWORD + " Varchar(20),"
	                      + iIDENTITY + " Varchar(10)," + iNICKNAME + " Varchar(20)," + iSCHOOL + " Varchar(20)," 
	                      + inoCLASS + " Varchar(20)" +" )";
		//nVarchar(20))
	                      //+ iFRIENDSID + " text
		dp.execSQL(sqlcreate);
	}
	//�ڽ�ʦ���в���һ�������
	public long insert(User user){
		ContentValues cv=new ContentValues();
		//cv.put(iUSERID, user.userID);
		cv.put(iACCOUNT, user.account);
		cv.put(iPASSWORD, user.password);
		cv.put(iIDENTITY, user.identity);
		cv.put(iNICKNAME, user.nickname);
		cv.put(iSCHOOL, user.school);
		cv.put(inoCLASS, user.noclass);

		
		return dp.insert("User", cv);
	}
	
	public long update(Integer id,User user){
		ContentValues cv=new ContentValues();
		//cv.put(iUSERID, user.userID);
		cv.put(iACCOUNT, user.account);
		cv.put(iPASSWORD, user.password);
		cv.put(iIDENTITY, user.identity);
		cv.put(iNICKNAME, user.nickname);
		cv.put(iSCHOOL, user.school);
		cv.put(inoCLASS, user.noclass);

		return dp.updatebyid("User", cv, id.toString());
	}
	
	public long delete(Integer id){
		return dp.deletebyid("User", id.toString());
	}
	
	
}
