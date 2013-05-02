package ntu.csie.oop13spring;

public class Action {
	protected Pet _dest;
	protected Skill _skill;
	protected POOCoordinate _pos;
	protected POOConstant.Type _type;
	
	
	public Action(POOConstant.Type type, Skill skill, Pet dest, POOCoordinate pos){
		_type = type;
		_skill = skill;
		_dest = dest;
		_pos = pos;
	}
	
	public Action(POOConstant.Type type, POOCoordinate pos){
		this(type, null, null, pos);
	}
	
}
