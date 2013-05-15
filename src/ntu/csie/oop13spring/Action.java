package ntu.csie.oop13spring;

public class Action {
	
	protected POOConstant.Type _type;
	protected Skill _skill;
	protected Item _item;
	protected Pet _dest;
	protected POOCoordinate _pos;
	
	
	
	public Action(POOConstant.Type type, Skill skill, Item item, Pet dest, POOCoordinate pos){
		_type = type;
		_skill = skill;
		_item = item;
		_dest = dest;
		_pos = pos;
		
	}
	
	public Action(POOConstant.Type type, POOCoordinate pos){
		this(type, null, null, null, pos);
	}
	
	public Action(POOConstant.Type type, Skill skill, POOCoordinate pos){
		this(type, skill, null, null, pos);
	}
	
	public Action(POOConstant.Type type, Item item, POOCoordinate pos){
		this(type, null, item, null, pos);
	}
	
	public Action(POOConstant.Type type){
		this(type, null, null, null, null);
	}
}
