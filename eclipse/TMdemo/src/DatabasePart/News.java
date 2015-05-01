package DatabasePart;
public class News {
	
	public String time;
	public String title;
	public String detail;
	public String userid;
	public String type;
	public String negauserid;
	public String good;
	public String goodman;
	public String comment;
	public String commentman;
	public String comcontent;
	
	public News(){
		
	}
	
	public News(String time,String title , String detail,String userid,String type,String negauserid,String good,String goodman,String comment,String commentman,String comcontent){
		
		this.time = time;
		this.title = title;
		this.detail  = detail;
		this.userid = userid;
		this.type = type;
		this.negauserid = negauserid;
		this.good = good;
		this.goodman = goodman;
		this.comcontent = comcontent;
		this.comment = comment;
		this.commentman = commentman;
		
	}
}
