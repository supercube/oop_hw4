package ntu.csie.oop13spring;
public class Cell{
	private POOConstant.Type _type; /* 0: empty, 1: pet, 2: obstacle */
	private int _id;
	private POOCoordinate _pos;
	private Object _obj;
	
	
	public Cell(POOConstant.Type type, int id, POOCoordinate pos, Object obj){
		_type = type;
		_id = id;
		_pos = pos;
		_obj = obj;
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
		if(_type != POOConstant.Type.EMPTY)
			return false;
		
		_type = type;
		_id = id;
		_pos = pos;
		_obj = obj;
		return true;
	}
	
	public void setEmpty(){
		_type = POOConstant.Type.EMPTY;
		_id = -1;
		_obj = null;
	}
}