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
	
	protected POOConstant.Dir _direction;
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
	
	protected POOCoordinate move(POOArena arena){
		POOCoordinate pos = arena.getPosition(this);
		POOCoordinate border = ((Arena)arena).getSize();
		switch(_direction){
			case RIGHT:
				if(pos.x + 1 < border.x && ((Arena)arena).getMap()[pos.x+1][pos.y].getSkill().isEmpty())
					pos.x += 1;
				break;
			case DOWN:
				if(pos.y + 1 < border.y && ((Arena)arena).getMap()[pos.x][pos.y+1].getSkill().isEmpty())
					pos.y += 1;
				break;
			case LEFT:
				if(pos.x - 1 >= 0 && ((Arena)arena).getMap()[pos.x-1][pos.y].getSkill().isEmpty())
					pos.x -= 1;
				break;
			case UP:
				if(pos.y - 1 >= 0 && ((Arena)arena).getMap()[pos.x][pos.y-1].getSkill().isEmpty())
					pos.y -= 1;
				break;
			default:;
		}
		return pos;
	}
	
}
