package ntu.csie.oop13spring;
public class Cell{
	private POOConstant.Type _type; /* 0: empty, 1: pet, 2: obstacle */
	private int _id;
	private Object _obj;
	
	
	public Cell(POOConstant.Type type, int id, Object obj){
		_type = type;
		_id = id;
		_obj = obj;
	}
	
	public Cell(Cell cell){
		this(cell._type, cell._id, cell._obj);
	}
	
	public Cell(){
		this(POOConstant.Type.EMPTY, -1, null);
	}
	
	public POOConstant.Type getType(){
		return _type;
	}
	
	public int getId(){
		return _id;
	}
	
	public Object getObject(){
		return _obj;
	}
	
	public boolean add(POOConstant.Type type, int id, Object obj){
		if(_type != POOConstant.Type.EMPTY)
			return false;
		
		_type = type;
		_id = id;
		_obj = obj;
		return true;
	}
	
	public void setEmpty(){
		_type = POOConstant.Type.EMPTY;
		_id = -1;
		_obj = null;
	}
}