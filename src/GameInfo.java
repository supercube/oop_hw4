package ntu.csie.oop13spring;

public class GameInfo {
	protected POOConstant.Game _status;
	protected boolean _fog;
	
	public GameInfo(POOConstant.Game status, boolean fog){
		_status = status;
		_fog = fog; 
	}
}
