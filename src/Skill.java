package ntu.csie.oop13spring;

import java.awt.*;

public abstract class Skill extends POOSkill{
	protected Image[] _imgs;
	protected int _img_id;
	protected int _ttl;
	
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public abstract boolean oneTimeStep(POOArena arena);
	
	public boolean vanish(){
		return _ttl <= 0;
	}
}
