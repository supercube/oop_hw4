package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class RockArm extends Pet{
	
	protected static Image[] _imgs;
	protected static int _no_img;
	protected static Random _rnd;
	protected static POOConstant.Skill[] _skills;
	protected static final int _max_angry_time;
	protected static final int _normal_agi;
	protected static final int _angry_agi;
	
	static {
		_no_img = 10;
		_imgs = new Image[_no_img];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/RockArm.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/RockArm_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[2] = Filter.filterOutBackground((new ImageIcon("Images/RockArm_3.png")).getImage(), new Color(0, 0, 0));
		_imgs[3] = Filter.filterOutBackground((new ImageIcon("Images/RockArm_4.png")).getImage(), new Color(0, 0, 0));
		_imgs[4] = Filter.filterOutBackground((new ImageIcon("Images/Angry_RockArm.png")).getImage(), new Color(0, 0, 0));
		_imgs[5] = Filter.filterOutBackground((new ImageIcon("Images/Angry_RockArm_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[6] = Filter.filterOutBackground((new ImageIcon("Images/Angry_RockArm_3.png")).getImage(), new Color(0, 0, 0));
		_imgs[7] = Filter.filterOutBackground((new ImageIcon("Images/Angry_RockArm_4.png")).getImage(), new Color(0, 0, 0));
		_imgs[8] = Filter.filterOutBackground((new ImageIcon("Images/RockArm_dead.png")).getImage(), new Color(0, 0, 0));
		_imgs[9] = Filter.filterOutBackground((new ImageIcon("Images/Angry_RockArm_dead.png")).getImage(), new Color(0, 0, 0));
		_rnd = new Random();
		_skills = new POOConstant.Skill[]{POOConstant.Skill.RockSting};
		_max_angry_time = 30;
		_normal_agi = 20;
		_angry_agi = 23;
	}
	
	private int _count;
	private ArrayList<Action> _actions;
	private int[] _cds;
	
	public RockArm(){
		setHP(6);
		setMP(10);
		adjustAGIandTTA(_normal_agi);
		
		_direction = POOConstant.Dir.getRandom();
		_img_id = _rnd.nextInt(4);
		_sight_range = 5;
		_count_down = _tta;
		_count = 0;
		_cds = new int[1];
		_cds[0] = 0;
		_angry_count = _rnd.nextInt(_max_angry_time);
	}
	
	public final Image getImage(){
		return _imgs[_img_id];
	}
	
	public int getMaxAnger(){
		return _max_angry_time;
	}
	
	protected boolean beAngry(){
		if(isAngry() || _angry_count != _max_angry_time)
			return false;
		
		setAngry();
		
		setHP(getHP()*2);
		setMP(getMP()*2 + 4);
		adjustAGIandTTA(_angry_agi);
		_img_id += 4;
		return true;
	}
	
	public ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos, POOConstant.Dir direction) {
		switch(id){
			case RockSting:
				int mp = getMP();
				int consume = RockSting.getMPConsume();
				if(_cds[0] == 0 && mp >= consume){
					_cds[0] = RockSting.getCD();
					setMP(mp-consume);
					_actions = new ArrayList<Action>(0);
					Coordinate new_pos = new Coordinate(pos.x, pos.y);
					switch(direction){
						case UP:
							pos.y--;
							new_pos.y -= 2;
							break;
						case DOWN:
							pos.y++;
							new_pos.y += 2;
							break;
						case LEFT:
							pos.x--;
							new_pos.x -= 2;
							break;
						case RIGHT:
							pos.x++;
							new_pos.x += 2;
							break;
						default:;
					}
					_actions.add(new Action(POOConstant.Type.SKILL, new RockSting(), new Coordinate(pos.x, pos.y)));
					_actions.add(new Action(POOConstant.Type.SKILL, new RockSting(), new_pos));
					return _actions;
				}
				break;
			default:;
		}
		return null;
	}
	
	public ArrayList<Action> Strategy(POOArena arena){
		
		_sight = ((Arena)arena).getSight((POOPet)this);
		boolean found = false;
		for(int i = 0; i < 2*_sight_range+1; i++){
			for(int j = 0; j < 2*_sight_range+1; j++){
				if(_sight[i][j] != null && (_sight[i][j].getType() == POOConstant.Type.PET || _sight[i][j].getType() == POOConstant.Type.PLAYER ) && (i!=_sight_range || j!=_sight_range) ){
					beAngry();
					if(i==_sight_range && (j==_sight_range-1 || j==_sight_range-2)){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.UP);
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(i==_sight_range && (j==_sight_range+1 || j==_sight_range+2)){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.DOWN);
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(j==_sight_range && (i==_sight_range-1 || i==_sight_range-2)){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.LEFT);
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(j==_sight_range && (i==_sight_range+1 || i==_sight_range+2)){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.RIGHT);
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(i - _sight_range < 0  && _sight[_sight_range - 1][_sight_range].getSkill().isEmpty()){
						_direction = POOConstant.Dir.LEFT;
						found = true;
					}else if(i - _sight_range > 0  && _sight[_sight_range + 1][_sight_range].getSkill().isEmpty()){
						_direction = POOConstant.Dir.RIGHT;
						found = true;
					}else if(j - _sight_range < 0 && _sight[_sight_range][_sight_range - 1].getSkill().isEmpty()){
						_direction = POOConstant.Dir.UP;
						found = true;
					}else if(j - _sight_range > 0   && _sight[_sight_range][_sight_range + 1].getSkill().isEmpty()){
						_direction = POOConstant.Dir.DOWN;
						found = true;
					}
				}
			}
		}
		if(!found)
			_direction = POOConstant.Dir.getRandom();
		
		_actions = new ArrayList<Action>(0);
		_actions.add(new Action(POOConstant.Type.MOVE, move(arena)));
		return _actions;
	}
	
public ArrayList<Action> OneTimeStep(POOArena arena){
		
		/* check whether is dead */
		if(getHP() <= 0){
			if(_img_id != 8 && _img_id != 9){
				_img_id = 8 + (_img_id / 4);
			}
			_actions = new ArrayList<Action>(0);
			_actions.add(new Action(POOConstant.Type.DEAD));
			return _actions;
		}
		
		/* cds count down */
		if(_cds[0] > 0)
			_cds[0]--;
		
		
		/* action count down */
		if(_count_down <= 0){
			_count_down = _tta + 1;
			
			
			if(isAngry()){
				_angry_count--;
				if(_angry_count <= 0){
					resetAngry();
					_img_id -= 4;
					setHP(getHP()/2 + 1);
					setMP(getMP()/2 + 2);
					adjustAGIandTTA(_normal_agi);
				}
			}else if(_angry_count < _max_angry_time){
				_angry_count++;
			}
			if(!isPlayer())
				return Strategy(arena);
			
			/* for player control */
			if(!_cmds.isEmpty()){
				Action act = null;
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
						POOCoordinate pos = arena.getPosition(this);
						_actions = useSkill(_skills[0], pos, _direction);
						if(_actions != null){
							return _actions;
						}
						break;
					case SPACE:
						if(beAngry())
							System.out.println("Angry! HP " + getHP() + ", MP " + getMP());
						break;
					default:;
				}
				_cmds.remove(0);
				if(act != null){
					_actions = new ArrayList<Action>(0);
					_actions.add(act);
					return _actions;
				}
			}
		}else if(_count == 0){
			if(!isAngry()){
				_img_id = (_img_id+1)%4;
			}else{
				_img_id = (_img_id+1)%4 + 4;
			}
		}
		_count = (_count + 1) % 20;
		_count_down--;
		return null;
	}
	
}
