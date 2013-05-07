package ntu.csie.oop13spring;

public class GameInfo {
	protected POOConstant.Game _status;
	protected boolean _fog;
	protected int _no_living_target;
	protected POOConstant.Type _player;
	public GameInfo(POOConstant.Game status, boolean fog){
		_status = status;
		_fog = fog;
		_no_living_target = 0;
		_player = POOConstant.Type.PLAYER;
	}
}
