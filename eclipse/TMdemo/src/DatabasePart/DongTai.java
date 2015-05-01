package DatabasePart;

public class DongTai {
	
	//public Integer id;       //IDֵ
    //public String userID;      //����
	
//    public String account;    //�γ���
//    public String password;
//    public String identity;  //�û���
//    public String nickname;   //IP��ַ
//    public String school;     //�˿ں�
//    public String noclass;       //�Ա�
//    public String friendsId;    //�ֻ�1
	public String pubUser;
	public String pubTime;
	public String dtContent;
	public String dtPic;
    
    public DongTai(){
        
    }
    //Integer id,String userID,
    public DongTai(String pubUser,
    		String pubTime,String dtContent,String dtPic){
    	//this.id=id;
    	//this.userID=userID;
    	this.pubUser=pubUser;
    	this.pubTime=pubTime;
    	this.dtContent=dtContent;
    	this.dtPic=dtPic;


    	
    }
}
