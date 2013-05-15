package ntu.csie.oop13spring;


public abstract class Skill extends POOSkill implements VaporizableDImage{
	
	protected int _img_id;
	protected int _ttl;
	private Pet _pet;
	
	public Skill(Pet pet){
		_pet = pet;
	}
	
	public boolean vanish(){
		return _ttl <= 0;
	}
	
	public abstract void act(Obstacle ob);
	
	public Pet getPet(){
		return _pet;
	}
	
}
