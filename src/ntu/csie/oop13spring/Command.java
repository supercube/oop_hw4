package ntu.csie.oop13spring;

public class Command {
	private POOConstant.Cmd _cmd;
	
	public Command(POOConstant.Cmd cmd){
		_cmd = cmd;
	}
	
	public POOConstant.Cmd get(){
		return _cmd;
	}
}
