package ntu.csie.oop13spring;

import java.awt.*;
import java.util.ArrayList;

public abstract class Pet extends POOPet{
	
	protected int _img_id;
	protected int _sight_range;
	protected Cell[][] _sight;
	protected int _id;
	protected int _tta; // time to act
	protected int _count_down;
	
	private boolean _angry;
	private boolean _player;
	
	protected ArrayList<Command> _cmds;
	
	
	protected final ArrayList<Command> getCmdListener(){
		return _cmds;
	}
	
	protected final void setAngry(){
		_angry = true;
	}
	protected final void resetAngry(){
		_angry = false;
	}
	
	protected final void setPlayer(){
		_player = true;
		_cmds = new ArrayList<Command>(0);
	}
	
	protected final void resetPlayer(){
		_player = false;
	}
	
	public final boolean isPlayer(){
		return _player;
	}
	
	public final boolean getAngry(){
		return _angry;
	}
	
	public abstract Image getImage();
	
	public final int getSightRange(){
		return _sight_range;
	}
	
	public abstract ArrayList<Action> Strategy(POOArena arena);
	
	public abstract ArrayList<Action> OneTimeStep(POOArena arena);
	
	public abstract ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos);
	
	public final void setId(int id){
		_id = id;
	}
	
	protected POOAction act(POOArena arena){
		POOAction action = new POOAction();
		action.skill = new TinyAttackSkill();
		return action;
	}
}
