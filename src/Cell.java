package ntu.csie.oop13spring;

import java.util.ArrayList;

public class Cell{
	private POOConstant.Type _type; /* 0: empty, 1: pet, 2: obstacle */
	private int _id;
	private POOCoordinate _pos;
	private Object _obj;
	private ArrayList<Skill> _skills;
	
	public Cell(POOConstant.Type type, int id, POOCoordinate pos, Object obj){
		_type = type;
		_id = id;
		_pos = pos;
		_obj = obj;
		_skills = new ArrayList<Skill>(0);
	}
	
	public Cell(Cell cell){
		this(cell._type, cell._id, cell._pos, cell._obj);
	}
	
	public Cell(){
		this(POOConstant.Type.EMPTY, -1, null, null);
	}
	
	public POOConstant.Type getType(){
		return _type;
	}
	
	public int getId(){
		return _id;
	}
	
	public POOCoordinate getPos(){
		return _pos;
	}
	
	public Object getObject(){
		return _obj;
	}
	
	public boolean add(POOConstant.Type type, int id, POOCoordinate pos, Object obj){
		if(_type != POOConstant.Type.EMPTY && _type != POOConstant.Type.DEAD)
			return false;
		
		_type = type;
		_id = id;
		_pos = pos;
		_obj = obj;
		return true;
	}
	
	public boolean appendSkill(Skill skill){
		return _skills.add(skill);
	}
	
	public boolean removeSkill(Skill skill){
		for(int i = 0; i < _skills.size(); i++){
			if(skill == _skills.get(i)){
				_skills.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Skill> getSkill(){
		return _skills;
	} 
	
	public void setEmpty(){
		_type = POOConstant.Type.EMPTY;
		_id = -1;
		_obj = null;
	}
	
	public void setDead(){
		_type = POOConstant.Type.DEAD;
	}
}