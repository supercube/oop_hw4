package ntu.csie.oop13spring;

import java.awt.*;

public abstract class Pet extends POOPet{
	protected static Image[] _imgs;
	protected static int _no_img;
	protected int _img_id;
	protected int _sight_range;
	protected Cell[][] _sight;
	protected int _id;
	protected int _tta; // time to act
	protected int _count_down;
	private boolean _Angry;
	
	protected void setAngry(){
		_Angry = true;
	}
	protected void resetAngry(){
		_Angry = false;
	}
	
	public boolean getAngry(){
		return _Angry;
	}
	
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public int getSightRange(){
		return _sight_range;
	}
	
	public abstract Action Strategy(POOArena arena);
	
	public abstract Action OneTimeStep(POOArena arena);
	
	public void setId(int id){
		_id = id;
	}
}
