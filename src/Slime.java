package ntu.csie.oop13spring;

public class Slime extends POOPet{
	
	public POOAction act(POOArena arena){
		POOAction action = new POOAction();
		action.skill = new POOTinyAttackSkill();
		return action;
	}

	public POOCoordinate move(POOArena arena){
		return arena.getPosition(this);
	}
}