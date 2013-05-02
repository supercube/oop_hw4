package ntu.csie.oop13spring;
public class Cell{
	private int _type; /* 0: empty, 1: pet, 2: obstacle */
	private int _id;
	private Object _obj;
	public Cell(){
		_type = 0;
		_id = -1;
		_obj = null;
	}
	
	public Cell(Cell cell){
		_id = cell._id;
		_type = cell._type;
		_obj = cell._obj;
	}
	
	public int getType(){
		return _type;
	}
	
	public int getId(){
		return _id;
	}
	
	public boolean add(int type, int id, Object obj){
		if(_type != 0)
			return false;
		
		_type = type;
		_id = id;
		_obj = obj;
		return true;
	}
	
	public void setEmpty(){
		_type = 0;
		_id = -1;
		_obj = null;
	}
}