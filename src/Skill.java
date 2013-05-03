package ntu.csie.oop13spring;

import java.awt.*;

public abstract class Skill extends POOSkill{
	
	protected int _img_id;
	protected int _ttl;
	
	public abstract boolean oneTimeStep(Cell[][] map, POOCoordinate pos);
	public abstract Image getImage();
	
	public boolean vanish(){
		return _ttl <= 0;
	}
	
}
