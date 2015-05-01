package DatabasePart;
public class FriendShip {
	
	public String PositiveU;
	public String NegativeU;
	public String FSstate;
	
    public FriendShip(){
        
    }
    //Integer id,String userID,
    public FriendShip(String PositiveU,String NegativeU,String FSstate){
    	this.PositiveU = PositiveU;
		this.NegativeU = NegativeU;
		this.FSstate = FSstate;
    }
}