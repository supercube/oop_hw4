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
	protected int _angry_count;
	protected int _kill_count = 0;
	private boolean _angry;
	private boolean _player;
	
	protected ArrayList<Command> _cmds;
	
	protected boolean adjustAGIandTTA(int agi){
		if(agi <= 0 || agi >= 40)
			return false;
		setAGI(agi);
		_tta = POOConstant.SlowTTA - getAGI();
		return true;
	}
	
	public ArrayList<Action> Player(Arena arena){
		/* for player control */
		if(_cmds.isEmpty())
			return null;
		ArrayList<Action> actions;
		Action act = null;
		POOCoordinate pos = arena.getPosition(this);
		POOConstant.Skill[] skills = getSkills();
		switch(_cmds.get(0).get()){
			case UP:
				_direction = POOConstant.Dir.UP;
				act = new Action(POOConstant.Type.MOVE, move(arena));
				break;
			case DOWN:
				_direction = POOConstant.Dir.DOWN;
				act = new Action(POOConstant.Type.MOVE, move(arena));
				break;
			case LEFT:
				_direction = POOConstant.Dir.LEFT;
				act = new Action(POOConstant.Type.MOVE, move(arena));
				break;
			case RIGHT:
				_direction = POOConstant.Dir.RIGHT;
				act = new Action(POOConstant.Type.MOVE, move(arena));
				break;
			case Z:
				actions = useSkill(skills[0], pos, _direction);
				if(actions != null){
					return actions;
				}
				break;
			case X:
				actions = useSkill(skills[1], pos, _direction);
				if(actions != null){
					return actions;
				}
				break;
			case SPACE:
				if(beAngry())
					System.out.println("Angry! HP " + getHP() + ", MP " + getMP());
				break;
			default:;
		}
		
		_cmds.remove(0);
		if(act == null)
			return null;
		
		actions = new ArrayList<Action>(0);
		actions.add(act);
		return actions;
	}
	
	protected final ArrayList<Command> getCmdListener(){
		return _cmds;
	}
	
	protected final void setAngry(){
		_angry = true;
	}
	
	protected final void resetAngry(){
		_angry = false;
	}
	
	protected abstract boolean beAngry();
	
	protected abstract void calmDownOrNot();
	
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
	
	public final boolean isAngry(){
		return _angry;
	}
	
	public int getAnger(){
		return _angry_count;
	}
	
	public abstract int getMaxAnger();
	
	public abstract Image getImage();
	
	public abstract POOConstant.Skill[] getSkills();
	
	public abstract void getKillReward();
	
	public final int getSightRange(){
		return _sight_range;
	}
	
	public abstract ArrayList<Action> Strategy(POOArena arena);
	
	public abstract ArrayList<Action> OneTimeStep(POOArena arena);
	
	public abstract ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos, POOConstant.Dir direction);
	
	public final void setId(int id){
		_id = id;
	}
	
	protected POOAction act(POOArena arena){
		POOAction action = null;
		return action;
	}
	
	protected POOCoordinate move(POOArena arena){
		POOCoordinate pos = arena.getPosition(this);
		POOCoordinate border = ((Arena)arena).getSize();
		switch(_direction){
			case RIGHT:
				if(pos.x + 1 < border.x) // && (isplayer || map[pos.x+1][pos.y].getSkill().isEmpty()))
					pos.x += 1;
				break;
			case DOWN:
				if(pos.y + 1 < border.y) // && (isplayer || map[pos.x][pos.y+1].getSkill().isEmpty()))
					pos.y += 1;
				break;
			case LEFT:
				if(pos.x - 1 >= 0) // && (isplayer || map[pos.x-1][pos.y].getSkill().isEmpty()))
					pos.x -= 1;
				break;
			case UP:
				if(pos.y - 1 >= 0) // && (isplayer || map[pos.x][pos.y-1].getSkill().isEmpty()))
					pos.y -= 1;
				break;
			default:;
		}
		return pos;
	}
	
}
