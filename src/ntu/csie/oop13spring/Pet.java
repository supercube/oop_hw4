package ntu.csie.oop13spring;

import java.util.ArrayList;
import java.util.Random;

public abstract class Pet extends POOPet implements DImage{
	
	private boolean _angry;
	private boolean _player;
	private boolean _dead;
	
	/* expect to be inherited */
	protected int _img_id;
	protected int _sight_range;
	protected ArrayList<Cell> _sight;
	protected int _id;
	protected int _tta; 							// time to act
	protected int _count_down;
	protected POOConstant.Dir _direction;
	protected int _angry_count;
	protected int _kill_count;
	protected static Random _rnd = new Random();
	protected ArrayList<Command> _cmds;
	
	/* abstract methods */
	public abstract int getMaxAnger();
	public abstract POOConstant.Skill[] getSkills();
	public abstract void getKillReward();
	public abstract ArrayList<Action> Strategy(POOArena arena);
	public abstract ArrayList<Action> oneTimeStep(POOArena arena);
	public abstract ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos, POOConstant.Dir direction);
	public abstract boolean beAngry();
	public abstract void calmDownOrNot();
	
	public Pet(){
		reset();
	}
	public void reset(){
		_dead = false;
		_kill_count = 0;
		_cmds = new ArrayList<Command>(0);
		resetAngry();
	}
	
	public boolean adjustAGIandTTA(int agi){
		if(agi <= 0 || agi >= 40)
			return false;
		setAGI(agi);
		_tta = POOConstant.SlowTTA - getAGI();
		return true;
	}
	
	/* for player control */
	public ArrayList<Action> Player(Arena arena){
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
				beAngry();
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
	
	public final ArrayList<Command> getCmdListener(){
		return _cmds;
	}
	
	public final void confirmDead(){
		_dead = true;
	}
	
	public final boolean isDead(){
		return _dead;
	}
	
	public final void setAngry(){
		_angry = true;
	}
	
	public final void resetAngry(){
		_angry = false;
	}
	
	
	
	public final void setPlayer(boolean player){
		_player = player;
		
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
	
	
	public final int getSightRange(){
		return _sight_range;
	}
	
	
	
	public final void setId(int id){
		_id = id;
	}
	
	public POOAction act(POOArena arena){
		POOAction action = null;
		return action;
	}
	
	public POOCoordinate move(POOArena arena){
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
