package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class TorLion extends Pet{
	protected static Image[] _imgs;
	protected static final int _no_img;
	protected static Random _rnd;
	protected static POOConstant.Skill[] _skills;
	protected static final int _max_angry_time;
	protected static final int _normal_agi;
	protected static final int _angry_agi;
	
	
	static {
		_no_img = 10;
		_imgs = new Image[_no_img];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/TorLion.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/TorLion_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[2] = Filter.filterOutBackground((new ImageIcon("Images/TorLion_3.png")).getImage(), new Color(0, 0, 0));
		_imgs[3] = Filter.filterOutBackground((new ImageIcon("Images/TorLion_4.png")).getImage(), new Color(0, 0, 0));
		_imgs[4] = Filter.filterOutBackground((new ImageIcon("Images/Angry_TorLion.png")).getImage(), new Color(0, 0, 0));
		_imgs[5] = Filter.filterOutBackground((new ImageIcon("Images/Angry_TorLion_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[6] = Filter.filterOutBackground((new ImageIcon("Images/Angry_TorLion_3.png")).getImage(), new Color(0, 0, 0));
		_imgs[7] = Filter.filterOutBackground((new ImageIcon("Images/Angry_TorLion_4.png")).getImage(), new Color(0, 0, 0));
		_imgs[8] = Filter.filterOutBackground((new ImageIcon("Images/TorLion_dead.png")).getImage(), new Color(0, 0, 0));
		_imgs[9] = Filter.filterOutBackground((new ImageIcon("Images/Angry_TorLion_dead.png")).getImage(), new Color(0, 0, 0));
		_rnd = new Random();
		_skills = new POOConstant.Skill[]{POOConstant.Skill.TinyAttackSkill, POOConstant.Skill.Tornado, POOConstant.Skill.None, POOConstant.Skill.None};
		_max_angry_time = 35;
		_normal_agi = 20;
		_angry_agi = 23;
	}
	
	private ArrayList<Action> _actions;
	private int[] _cds;
	private POOConstant.Dir _pre_direction;
	
	public TorLion(){
		setHP(15);
		setMP(10);
		adjustAGIandTTA(_normal_agi);
		
		_direction = POOConstant.Dir.getRandom();
		_img_id = _rnd.nextInt(4);
		_sight_range = 7;
		_count_down = _tta;
		_cds = new int[]{0,0};
		_angry_count = _rnd.nextInt(_max_angry_time - _max_angry_time/2) + _max_angry_time/2;
	}
	
	public final Image getImage(){
		return _imgs[_img_id];
	}
	
	public int getMaxAnger(){
		return _max_angry_time;
	}
	
	public POOConstant.Skill[] getSkills(){
		return _skills;
	}
	
	protected boolean beAngry(){
		if(isAngry() || _angry_count != _max_angry_time)
			return false;
		
		setAngry();
		_img_id += 4;
		setHP(getHP()*2 + 2);
		setMP(getMP()*2 + 10);
		adjustAGIandTTA(_angry_agi);
		return true;
	}
	
	public ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos, POOConstant.Dir direction) {
		int mp, consume;
		switch(id){
			case TinyAttackSkill:
				mp = getMP();
				consume = TinyAttackSkill.getMPConsume();
				if(_cds[0] == 0 && mp >= consume){
					_cds[0] = TinyAttackSkill.getCD();
					setMP(mp-consume);
					_actions = new ArrayList<Action>(0);
					switch(direction){
						case UP:
							pos.y--;
							break;
						case DOWN:
							pos.y++;
							break;
						case LEFT:
							pos.x--;
							break;
						case RIGHT:
							pos.x++;
							break;
						default:;
					}
					_actions.add(new Action(POOConstant.Type.SKILL, new TinyAttackSkill(this), new Coordinate(pos.x, pos.y)));
					return _actions;
				}
				break;
			case Tornado:
				mp = getMP();
				consume = Tornado.getMPConsume();
				if(_cds[1] == 0 && mp >= consume){
					_cds[1] = Tornado.getCD();
					setMP(mp-consume);
					_actions = new ArrayList<Action>(0);
					switch(direction){
						case UP:
							pos.y--;
							break;
						case DOWN:
							pos.y++;
							break;
						case LEFT:
							pos.x--;
							break;
						case RIGHT:
							pos.x++;
							break;
						default:;
					}
					_actions.add(new Action(POOConstant.Type.SKILL, new Tornado(this, direction), new Coordinate(pos.x, pos.y)));
					return _actions;
				}
				break;
			default:;
		}
		return null;
	}
	
	protected POOCoordinate move(POOArena arena){
		POOCoordinate pos = arena.getPosition(this);
		POOCoordinate border = ((Arena)arena).getSize();
		switch(_direction){
			case RIGHT:
				if(pos.x + 1 < border.x)
					pos.x += 1;
				break;
			case DOWN:
				if(pos.y + 1 < border.y)
					pos.y += 1;
				break;
			case LEFT:
				if(pos.x - 1 >= 0)
					pos.x -= 1;
				break;
			case UP:
				if(pos.y - 1 >= 0)
					pos.y -= 1;
				break;
			default:;
		}
		
		
		if(_direction == POOConstant.Dir.LEFT){
			_img_id = (_img_id + 1)%2;
		}else if(_direction == POOConstant.Dir.RIGHT){
			_img_id = (_img_id + 1)%2 + 2;
		}else if(_pre_direction == POOConstant.Dir.LEFT){
			_img_id = (_img_id + 1)%2;
		}else if(_pre_direction == POOConstant.Dir.RIGHT){
			_img_id = (_img_id + 1)%2 + 2;
		}
		if(isAngry()){
			_img_id+=4;
		}
		return pos;
	}
	
	public ArrayList<Action> Strategy(POOArena arena){
		
		_sight = ((Arena)arena).getSight((POOPet)this);
		boolean found = false;
		_actions = null;
		boolean powerskill = (_rnd.nextInt(16) == 15);
		for(int i = 0; i < 2*_sight_range+1; i++){
			for(int j = 0; j < 2*_sight_range+1; j++){
				if(_sight[i][j] != null && (_sight[i][j].getType() == POOConstant.Type.PET || _sight[i][j].getType() == POOConstant.Type.PLAYER) && (i!=_sight_range || j!=_sight_range) ){
					beAngry();
					
					/* use skill */
					if(i==_sight_range && _sight_range - j <= 4 && _sight_range - j > 0){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						if(getMP() >= 5 && powerskill){
							_actions = useSkill(POOConstant.Skill.Tornado, new Coordinate(pos.x, pos.y), POOConstant.Dir.UP);
						}
						if(_actions == null && _sight_range - j == 1){
							_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.UP);
						}
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(i==_sight_range && j - _sight_range <= 4 && j - _sight_range > 0){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						if(getMP() >= 5 && powerskill){
							System.out.println("tornado");
							_actions = useSkill(POOConstant.Skill.Tornado, new Coordinate(pos.x, pos.y), POOConstant.Dir.DOWN);
						}
						if(_actions == null && j - _sight_range  == 1){
							_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.DOWN);
						}
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(j==_sight_range && _sight_range - i <= 4 && _sight_range - i > 0){
						System.out.println("left");
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						if(getMP() >= 5 && powerskill){
							_actions = useSkill(POOConstant.Skill.Tornado, new Coordinate(pos.x, pos.y), POOConstant.Dir.LEFT);
						}
						if(_actions == null && _sight_range - i == 1){
							_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.LEFT);
						
						}
						if(_actions != null){
							found = true;
							return _actions;
						}
					}else if(j==_sight_range && i==_sight_range+1 && i - _sight_range <= 4 && i - _sight_range > 0){
						System.out.println("right");
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						if(getMP() >= 5 && powerskill){
							_actions = useSkill(POOConstant.Skill.Tornado, new Coordinate(pos.x, pos.y), POOConstant.Dir.RIGHT);
						}
						if(_actions == null && i - _sight_range == 1){
							_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.RIGHT);
						}
						if(_actions != null){
							found = true;
							return _actions;
						}
					}
					
					/* move toward other pet */
					if(getMP() >= 5 && getHP() >= 10){
						if(i - _sight_range < 0 && (_sight[_sight_range-1][_sight_range].getType() == POOConstant.Type.EMPTY || _sight[_sight_range-1][_sight_range].getType() == POOConstant.Type.DEAD)){
							_direction = POOConstant.Dir.LEFT;
							found = true;
						}else if(i - _sight_range > 0 && (_sight[_sight_range+1][_sight_range].getType() == POOConstant.Type.EMPTY || _sight[_sight_range+1][_sight_range].getType() == POOConstant.Type.DEAD)){
							_direction = POOConstant.Dir.RIGHT;
							found = true;
						}else if(j - _sight_range < 0 && (_sight[_sight_range][_sight_range-1].getType() == POOConstant.Type.EMPTY || _sight[_sight_range][_sight_range-1].getType() == POOConstant.Type.DEAD)){
							_direction = POOConstant.Dir.UP;
							found = true;
						}else if(j - _sight_range > 0 && (_sight[_sight_range][_sight_range+1].getType() == POOConstant.Type.EMPTY || _sight[_sight_range][_sight_range+1].getType() == POOConstant.Type.DEAD)){
							_direction = POOConstant.Dir.DOWN;
							found = true;
						}
					}
				}
			}
		}
		if(!found)
			_direction = POOConstant.Dir.getRandom();//_rnd.nextInt(4);
		
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
		for(int i = 0; i < _cds.length; i++)
			if(_cds[i] > 0)
				_cds[i]--;
		
		
		/* action count down */
		if(_count_down <= 0){
			_count_down = _tta + 1;
			
			if(_direction == POOConstant.Dir.LEFT || _direction == POOConstant.Dir.RIGHT)
				_pre_direction = _direction;
			
			if(isAngry()){
				_angry_count--;
				if(_angry_count <= 0){
					resetAngry();
					_img_id -= 4;
					setHP(getHP()/2 + 1);
					setMP(getMP()/2 + 1);
					adjustAGIandTTA(_normal_agi);
				}
			}else if(_angry_count < _max_angry_time){
				_angry_count++;
			}
			if(!isPlayer())
				return Strategy(arena);
			else{
				return Player((Arena)arena);
			}
		}
		_count_down--;
		return null;
	}
}
