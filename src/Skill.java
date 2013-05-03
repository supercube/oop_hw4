package ntu.csie.oop13spring;

import java.awt.*;

public abstract class Skill extends POOSkill{
	
	protected int _img_id;
	protected int _ttl;
	//protected int _id;
	public abstract boolean oneTimeStep(Cell[][] map, POOCoordinate pos);
	public abstract Image getImage();
	
	public boolean vanish(){
		return _ttl <= 0;
	}
	/*
	public int getId(){
		return _id;
	}
	
	public void setId(int id){
		_id = id;
	}*/
}
