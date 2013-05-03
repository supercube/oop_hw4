package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;

public class Slime extends Pet{
	
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
		_rnd = new Random();
		_skills = new POOConstant.Skill[]{POOConstant.Skill.TinyAttackSkill};
	}
	
	private int _count;
	protected POOConstant.Dir _direction;
	protected static Random _rnd;
	private ArrayList<Action> _actions;
	private int[] _cds;
	
	public Slime(){
		_img_id = _rnd.nextInt(4);
		_sight_range = 4;
		_tta = 30;
		_count_down = _tta;
		_count = 0;
		_cds = new int[1];
		_cds[0] = 0;
		setHP(2);
		setMP(10);
		setAGI(5);
		
	}
	
	protected boolean beAngry(){
		if(getAngry())
			return false;
		
		setAngry();
		_img_id = _rnd.nextInt(4) + 4;
		return true;
	}
	
	protected POOAction act(POOArena arena){
		POOAction action = new POOAction();
		action.skill = new TinyAttackSkill();
		return action;
	}
	public ArrayList<Action> useSkill(POOConstant.Skill id, POOCoordinate pos) {
		switch(id){
			case TinyAttackSkill:
				int mp = getMP();
				int consume = TinyAttackSkill.getMPConsume();
				if(_cds[0] == 0 && mp >= consume){
					_cds[0] = TinyAttackSkill.getCD();
					setMP(mp-consume);

					_actions = new ArrayList<Action>(0);
					_actions.add(new Action(POOConstant.Type.SKILL, new TinyAttackSkill(), new Coordinate(pos.x, pos.y)));
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
		return pos;
	}
	
	public ArrayList<Action> Strategy(POOArena arena){
		
		_sight = ((Arena)arena).getSight((POOPet)this);
		boolean found = false;
		for(int i = 0; i < 2*_sight_range+1; i++){
			for(int j = 0; j < 2*_sight_range+1; j++){
				if(_sight[i][j] != null && _sight[i][j].getType() != POOConstant.Type.EMPTY && (i!=_sight_range || j!=_sight_range) ){
					beAngry();
					if(((i==_sight_range && (j==_sight_range-1 || j==_sight_range+1)) || (j==_sight_range && (i==_sight_range-1 || i==_sight_range+1)))){
						POOCoordinate pos = ((Arena)arena).getPosition(this);
						_actions = useSkill(POOConstant.Skill.TinyAttackSkill, new Coordinate(i + pos.x - _sight_range, j + pos.y - _sight_range));
						if(_actions != null)
							return _actions;
					}
					if(i - _sight_range < 0){
						_direction = POOConstant.Dir.LEFT;
					}else if(i - _sight_range > 0){
						_direction = POOConstant.Dir.RIGHT;
					}else if(j - _sight_range < 0){
						_direction = POOConstant.Dir.UP;
					}else if(j - _sight_range > 0){
						_direction = POOConstant.Dir.DOWN;
					}
					//POOCoordinate pos = arena.getPosition(this);
					//System.out.println("found " + ((Arena)arena).getPetId((Pet)_sight[i][j].getObject()) + " at " + (i+pos.x-_sight_range) + ", " + (j+pos.y-_sight_range));
					found = true;
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
			return null;
		}
		
		/* cds count down */
		if(_cds[0] > 0)
			_cds[0]--;
		
		/* action count down */
		if(_count_down <= 0){
			_count_down = _tta + 1;
			
			if(!isPlayer())
				return Strategy(arena);
			
			/* for player control */
			if(_cmds.isEmpty()){
				System.out.println("Empty");
				return null;
			}
			
			Action act = null;
			switch(_cmds.get(0).get()){
				case UP:
					_direction = POOConstant.Dir.UP;
					System.out.println("UP");
					act = new Action(POOConstant.Type.MOVE, move(arena));
					break;
				case DOWN:
					_direction = POOConstant.Dir.DOWN;
					System.out.println("DOWN");
					act = new Action(POOConstant.Type.MOVE, move(arena));
					break;
				case LEFT:
					_direction = POOConstant.Dir.LEFT;
					System.out.println("LEFT");
					act = new Action(POOConstant.Type.MOVE, move(arena));
					break;
				case RIGHT:
					_direction = POOConstant.Dir.RIGHT;
					System.out.println("RIGHT");
					act = new Action(POOConstant.Type.MOVE, move(arena));
					break;
				case Z:
					POOCoordinate pos = arena.getPosition(this);
					switch(_direction){
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
					_actions = useSkill(_skills[0], pos);
					if(_actions != null){
						return _actions;
					}
				default:;
			}
			_cmds.remove(0);
			
			if(act == null)
				return null;
			
			_actions = new ArrayList<Action>(0);
			_actions.add(act);
			return _actions;
			
		}else if(_count == 0){
			if(!getAngry()){
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