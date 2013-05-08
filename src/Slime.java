package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;

public class Slime extends Pet{
	
	protected static Image[] _imgs;
	protected static final int _no_img;
	protected static POOConstant.Skill[] _skills;
	protected static final int _max_angry_time;
	protected static final int _normal_agi;
	protected static final int _angry_agi;
	
	static {
		_no_img = 10;
		_imgs = new Image[_no_img];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Slime.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/Slime_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[2] = Filter.filterOutBackground((new ImageIcon("Images/Slime_3.png")).getImage(), new Color(0, 0, 0));
		_imgs[3] = Filter.filterOutBackground((new ImageIcon("Images/Slime_4.png")).getImage(), new Color(0, 0, 0));
		_imgs[4] = Filter.filterOutBackground((new ImageIcon("Images/Red_Slime.png")).getImage(), new Color(0, 0, 0));
		_imgs[5] = Filter.filterOutBackground((new ImageIcon("Images/Red_Slime_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[6] = Filter.filterOutBackground((new ImageIcon("Images/Red_Slime_3.png")).getImage(), new Color(0, 0, 0));
		_imgs[7] = Filter.filterOutBackground((new ImageIcon("Images/Red_Slime_4.png")).getImage(), new Color(0, 0, 0));
		_imgs[8] = Filter.filterOutBackground((new ImageIcon("Images/Slime_dead.png")).getImage(), new Color(0, 0, 0));
		_imgs[9] = Filter.filterOutBackground((new ImageIcon("Images/Red_Slime_dead.png")).getImage(), new Color(0, 0, 0));
		_skills = new POOConstant.Skill[]{POOConstant.Skill.TinyAttackSkill, POOConstant.Skill.None, POOConstant.Skill.None, POOConstant.Skill.None};
		_max_angry_time = 11;
		_normal_agi = 12;
		_angry_agi = 17;
	}
	
	private int _count;
	private ArrayList<Action> _actions;
	private int[] _cds;
	private int _grow_agi;
	
	
	public Slime(){
		setHP(3);
		setMP(3);
		adjustAGIandTTA(_normal_agi);
		
		_direction = POOConstant.Dir.getRandom();
		_img_id = _rnd.nextInt(4);
		_sight_range = 3;
		_count_down = _tta;
		_count = 0;
		_cds = new int[1];
		_cds[0] = 0;
		_grow_agi = 0;
		_angry_count = _rnd.nextInt(_max_angry_time);
	}
	
	public final Image getImage(){
		return _imgs[_img_id];
	}
	
	public POOConstant.Skill[] getSkills(){
		return _skills;
	}
	
	public int getMaxAnger(){
		return _max_angry_time;
	}
	
	public void getKillReward(){
		_kill_count++;
		if(isAngry()){
			setHP(getHP() + 4);
			setMP(getMP() + 8);
		}else{
			setHP(getHP() + 2);
			setMP(getMP() + 4);
		}
		_angry_count += 10;
		_grow_agi++;
	}
	
	protected boolean beAngry(){
		if(isAngry() || _angry_count < _max_angry_time)
			return false;
		
		setAngry();
		_img_id += 4;
		setHP(getHP()*2);
		setMP((getMP()+1)*2);
		adjustAGIandTTA(_angry_agi + _grow_agi);
		return true;
	}
	
	protected void calmDownOrNot(){
		if(isAngry()){
			_angry_count--;
			if(_angry_count <= 0){
				resetAngry();
				_img_id -= 4;
				setHP(getHP()/2 + 1);
				setMP(getMP()/2 + 1);
				adjustAGIandTTA(_normal_agi + _grow_agi);
			}
		}else if(_angry_count < _max_angry_time){
			_angry_count++;
		}
	}
	
	public ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos, POOConstant.Dir direction) {
		switch(id){
			case TinyAttackSkill:
				int mp = getMP();
				int consume = TinyAttackSkill.getMPConsume();
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
			default:;
		}
		return null;
	}
	
	public ArrayList<Action> Strategy(POOArena arena){
		
		beAngry(); // always try to be angry
		
		_sight = ((Arena)arena).getSight((POOPet)this);
		_actions = null;
		boolean found = false;
		int x, y;
		Cell tmp;
		POOCoordinate pos = ((Arena)arena).getPosition(this);
		int min_distance = 1000, distance;
		/* check neighbor */
		boolean up = true, down = true, left = true, right = true;
		for(int id = 0; id < _sight.size(); id++){
			tmp = _sight.get(id);
			if(tmp == null)
				continue;
			x = tmp.getPos().x;
			y = tmp.getPos().y;
			if((x == pos.x) && (y == pos.y - 1)){ // up
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD)
					up = false;
			}else if((x == pos.x) && (y == pos.y + 1)){ // down
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD)
					down = false;
			}else if((x == pos.x - 1) && (y == pos.y)){ // left
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD)
					left = false;
			}else if((x == pos.x + 1) && (y == pos.y)){ // right
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD)
					right = false;
			}
		}
		
		/* make decision */
		for(int id = 0; id < _sight.size(); id++){
			tmp = _sight.get(id);
			if(tmp == null)
				continue;
			x = tmp.getPos().x;
			y = tmp.getPos().y;
			if((tmp.getType() == POOConstant.Type.PET || tmp.getType() == POOConstant.Type.PLAYER) && !(tmp.getObject() instanceof Slime) && (x != pos.x || y != pos.y) ){
				if(x == pos.x && y == pos.y - 1){
					_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.UP);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}else if(x == pos.x && y == pos.y + 1){
					_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.DOWN);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}else if(y == pos.y && x == pos.x - 1){
					_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y), POOConstant.Dir.LEFT);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}else if(y == pos.y  && x == pos.x + 1){
					_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(pos.x, pos.y),POOConstant.Dir.RIGHT);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}
				
				distance = Math.abs(x - pos.x) + Math.abs(y - pos.y);
				if(x - pos.x < 0 && left && distance < min_distance){
					_direction = POOConstant.Dir.LEFT;
					min_distance = distance;
					found = true;
				}else if(x - pos.x > 0 && right && distance < min_distance){
					_direction = POOConstant.Dir.RIGHT;
					min_distance = distance;
					found = true;
				}else if(y - pos.y < 0 && up && distance < min_distance){
					_direction = POOConstant.Dir.UP;
					min_distance = distance;
					found = true;
				}else if(y - pos.y > 0 && down && distance < min_distance){
					_direction = POOConstant.Dir.DOWN;
					min_distance = distance;
					found = true;
				}
			}
		}
		if(!found)
			_direction = POOConstant.Dir.getRandom();
		
		_actions = new ArrayList<Action>(0);
		_actions.add(new Action(POOConstant.Type.MOVE, move(arena)));
		return _actions;
	}
	
	public ArrayList<Action> oneTimeStep(POOArena arena){
		
		/* check whether is dead */
		if(getHP() <= 0){
			if(_img_id != 8 && _img_id != 9){
				_img_id = 8 + (_img_id / 4);
			}
			_actions = new ArrayList<Action>(0);
			_actions.add(new Action(POOConstant.Type.DEAD));
			return _actions;
		}
		
		if(!isAngry())
			_sight_range = 3 + getHP()/5;
		
		/* cds count down */
		for(int i = 0; i < _cds.length; i++)
			if(_cds[i] > 0)
				_cds[i]--;
		
		
		/* action count down */
		if(_count_down <= 0){
			_count_down = _tta + 1;
			
			
			calmDownOrNot();
			
			if(!isPlayer()){
				return Strategy(arena);
			}else{
				return Player((Arena)arena);
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