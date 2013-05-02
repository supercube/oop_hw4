package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;

public class Slime extends Pet{
	
	protected int _direction;
	protected Random _rnd;
	public Slime(){
		_no_img = 10;
		_imgs = new Image[_no_img];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Slime.png")).getImage(), new Color(0, 0, 0));	
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/Red_Slime.png")).getImage(), new Color(0, 0, 0));
		_img_id = 0;
		
		_sight_range = 6;
		setHP(10);
		setMP(1);
		setAGI(10);
		_rnd = new Random(); 
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
			case 0:
				if(pos.x + 1 < border.x)
					pos.x += 1;
				break;
			case 1:
				if(pos.y + 1 < border.y)
					pos.y += 1;
				break;
			case 2:
				if(pos.x - 1 >= 0)
					pos.x -= 1;
				break;
			case 3:
				if(pos.y - 1 >= 0)
					pos.y -= 1;
				break;
			default:;
		}
		return pos;
	}
	
	protected Action Strategy(POOArena arena){
		
		_sight = ((Arena)arena).getSight((POOPet)this);
		boolean found = false;
		for(int i = 0; i < 2*_sight_range+1; i++){
			for(int j = 0; j < 2*_sight_range+1; j++){
				if(_sight[i][j] != null && _sight[i][j].getType() != POOConstant.Type.EMPTY && (i!=_sight_range || j!=_sight_range) ){// && _id != _sight[i][j].getId()){
					_img_id = 1;
					if(getMP() > 0 && (i==_sight_range && (j==_sight_range-1 || j==_sight_range+1)) || (j==_sight_range && (i==_sight_range-1 || i==_sight_range+1))){
						setMP(getMP()-1);
						POOCoordinate pos = arena.getPosition(this);
						return new Action (POOConstant.Type.SKILL, new TinyAttackSkill(), null, new Coordinate(i + pos.x - _sight_range, j + pos.y - _sight_range));
					}
					if(i - _sight_range < 0){
						_direction = 2;
					}else if(i - _sight_range > 0){
						_direction = 0;
					}else if(j - _sight_range < 0){
						_direction = 3;
					}else if(j - _sight_range > 0){
						_direction = 1;
					}
					//POOCoordinate pos = arena.getPosition(this);
					//System.out.println("found " + ((Arena)arena).getPetId((Pet)_sight[i][j].getObject()) + " at " + (i+pos.x-_sight_range) + ", " + (j+pos.y-_sight_range));
					found = true;
					break;
				}
			}
		}
		if(!found)
			_direction = _rnd.nextInt(4);
		
		return new Action(POOConstant.Type.MOVE, move(arena));
	}
}