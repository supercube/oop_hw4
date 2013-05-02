package ntu.csie.oop13spring;

public class Action {
	protected Skill _skill;
	protected POOCoordinate _dest;
	public Action(Skill skill, POOCoordinate dest){
		_skill = skill;
		_dest = dest;
	}
}
