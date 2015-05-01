package DatabasePart;

import android.content.ContentValues;

public class NewsTable {
	DatabasePart dp;
	
	private String iTime = "time";
	private String iTitle = "title";
	private String iDetail = "detail";
	private String iUserid = "userid";
	private String iNegaUserid = "negauserid";
	private String iGood = "good";
	private String iGoodman = "goodman";
	private String icomment = "comment";
	private String icommentman = "commentman";
	private String icomcontent = "comcontent";
	private String iType = "type";
	
	public NewsTable(DatabasePart dp){
		this.dp = dp;
		create();
	}
	
	private void create(){
		String sqlcreate4news  = "Create Table IF NOT EXISTS News(news_id INTEGER PRIMARY KEY AUTOINCREMENT,"
								+ iTime + " Varchar(30)," + iTitle + " Varchar(30),"
								+ iDetail + " text," + iUserid + " Varchar(20),"
								+ iNegaUserid + " Varchar(20)," + iGood + " Varchar(20),"
								+ iGoodman + " text," + icomment + " Varchar(20),"
								+ icommentman + " text," + icomcontent + " text,"
								+ iType + " Varchar(20)"+" )";
		dp.execSQL(sqlcreate4news);
	}
	public long insert(News news){
		ContentValues cv = new ContentValues();
		cv.put(iTime, news.time);
		cv.put(iTitle, news.title);
		cv.put(iDetail,news.detail);
		cv.put(iUserid, news.userid);
		cv.put(iNegaUserid, news.negauserid);
		cv.put(iGood, news.good);
		cv.put(iGoodman, news.goodman);
		cv.put(icomment, news.comment);
		cv.put(icommentman, news.commentman);
		cv.put(icomcontent, news.comcontent);
		cv.put(iType, news.type);
		return dp.insert("News", cv);
		
	}
	public long update(Integer id, News news){
		ContentValues cv = new ContentValues();
		cv.put(iTime, news.time);
		cv.put(iTitle, news.title);
		cv.put(iDetail,news.detail);
		cv.put(iUserid, news.userid);
		cv.put(iType, news.type);
		cv.put(iNegaUserid, news.negauserid);
		cv.put(iGood, news.good);
		cv.put(iGoodman, news.goodman);
		cv.put(icomment, news.comment);
		cv.put(icommentman, news.commentman);
		cv.put(icomcontent, news.comcontent);
		return dp.updatebyid("News", cv,id.toString());
	}
	public long delete(Integer id){
		return dp.deletebyid("News", id.toString());
	}
	
}
