package ntu.csie.oop13spring;

import java.awt.*;

public abstract class Skill extends POOSkill{
	
	protected int _img_id;
	protected int _ttl;
	private Pet _pet;
	
	public abstract boolean oneTimeStep(Arena arena, POOCoordinate pos);
	public abstract Image getImage();
	
	public Skill(Pet pet){
		_pet = pet;
	}
	
	public boolean vanish(){
		return _ttl <= 0;
	}
	
	public Pet getPet(){
		return _pet;
	}
	
}
