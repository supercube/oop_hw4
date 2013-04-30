package ntu.csie.oop13spring;
public class Cell{
	private int _type; /* 0: empty, 1: pet, 2: obstacle */
	private int _id;
	
	public Cell(){
		_type = 0;
		_id = -1;
	}
	
	public int getType(){
		return _type;
	}
	
	public int getId(){
		return _id;
	}
	
	public boolean add(int type, int id){
		if(_type != 0)
			return false;
		
		_type = type;
		_id = id;
		return true;
	}
}