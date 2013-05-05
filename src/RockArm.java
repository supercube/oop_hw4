package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class RockArm extends Pet{
	
	protected static Image[] _imgs;
	protected static int _no_img;
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
		_skills = new POOConstant.Skill[]{POOConstant.Skill.RockSting, POOConstant.Skill.None, POOConstant.Skill.None, POOConstant.Skill.None};
		_max_angry_time = 30;
		_normal_agi = 15;
		_angry_agi = 20;
	}
	
	private int _count;
	private ArrayList<Action> _actions;
	private int[] _cds;
	
	public RockArm(){
		setHP(7);
		setMP(7);
		adjustAGIandTTA(_normal_agi);
		
		_direction = POOConstant.Dir.getRandom();
		_img_id = _rnd.nextInt(4);
		_sight_range = 6;
		_count_down = _tta;
		_count = 0;
		_cds = new int[1];
		_cds[0] = 0;
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
	}
	
	protected boolean beAngry(){
		if(isAngry() || _angry_count < _max_angry_time)
			return false;
		
		setAngry();
		
		setHP(getHP()*2);
		setMP(getMP()*2 + 4);
		adjustAGIandTTA(_angry_agi);
		_img_id += 4;
		return true;
	}
	
	protected void calmDownOrNot(){
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
					_actions.add(new Action(POOConstant.Type.SKILL, new RockSting(this), new Coordinate(pos.x, pos.y)));
					_actions.add(new Action(POOConstant.Type.SKILL, new RockSting(this), new_pos));
					return _actions;
				}
				break;
			default:;
		}
		return null;
	}
	
	public ArrayList<Action> Strategy(POOArena arena){
		
		_sight = ((Arena)arena).getSight((POOPet)this);
		_actions = null;
		boolean found = false;
		boolean powerskill = (_rnd.nextInt(12) == 0);
		int x, y;
		Cell tmp;
		POOCoordinate pos = ((Arena)arena).getPosition(this);
		/* check neighbor */
		boolean up = true, down = true, left = true, right = true;
		for(int id = 0; id < _sight.size(); id++){
			tmp = _sight.get(id);
			if(tmp == null)
				continue;
			x = tmp.getPos().x;
			y = tmp.getPos().y;
			if((x == pos.x) && (y == pos.y - 1)){ // up
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD || !tmp.getSkill().isEmpty())
					up = false;
			}else if((x == pos.x) && (y == pos.y + 1)){ // down
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD || !tmp.getSkill().isEmpty())
					down = false;
			}else if((x == pos.x - 1) && (y == pos.y)){ // left
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD || !tmp.getSkill().isEmpty())
					left = false;
			}else if((x == pos.x + 1) && (y == pos.y)){ // right
				if(tmp.getType() != POOConstant.Type.EMPTY && tmp.getType() != POOConstant.Type.DEAD || !tmp.getSkill().isEmpty())
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
			if((tmp.getType() == POOConstant.Type.PET || tmp.getType() == POOConstant.Type.PLAYER) && (x != pos.x || y != pos.y) ){
				beAngry();
				
				/* use skill */
				if(x == pos.x && (y == pos.y - 1 || y == pos.y - 2)){
					_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.UP);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}else if(x == pos.x && (y == pos.y + 1 || y == pos.y + 2)){
					_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.DOWN);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}else if(y == pos.y && (x == pos.x - 1 || x == pos.x - 2)){
					_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.LEFT);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}else if(y == pos.y && (x == pos.x + 1 || x == pos.x + 2)){
					_actions = useSkill(POOConstant.Skill.RockSting, new Coordinate(pos.x, pos.y), POOConstant.Dir.RIGHT);
					if(_actions != null){
						found = true;
						return _actions;
					}
				}
				
				if(getHP() >= 3 && getMP() >= 4){
					if(x - pos.x < 0 && left){
						_direction = POOConstant.Dir.LEFT;
						found = true;
					}else if(x - pos.x > 0 && right){
						_direction = POOConstant.Dir.RIGHT;
						found = true;
					}else if(y - pos.y < 0 && up){
						_direction = POOConstant.Dir.UP;
						found = true;
					}else if(y - pos.y > 0 && down){
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
