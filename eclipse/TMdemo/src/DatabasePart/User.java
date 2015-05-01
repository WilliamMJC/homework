package DatabasePart;

public class User {
	
	//public Integer id;       //IDֵ
    //public String userID;      //����
    public String account;    //�γ���
    public String password;
    public String identity;  //�û���
    public String nickname;   //IP��ַ
    public String school;     //�˿ں�
    public String noclass;       //�Ա�
    public String friendsId;    //�ֻ�1

    public User(){
        
    }
    //Integer id,String userID,
    public User(String account,
    		String password,String identity,String nickname,
    		String school,String noclass,String friendsId){
    	//this.id=id;
    	//this.userID=userID;
    	this.account=account;
    	this.password=password;
    	this.identity=identity;
    	this.nickname=nickname;
    	this.school=school;
    	this.noclass=noclass;
    	this.friendsId=friendsId;

    	
    }
}
