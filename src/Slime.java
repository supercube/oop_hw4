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
		_img_id = 0;
		
		setHP(10);
		setMP(10);
		setAGI(10);
		_rnd = new Random(); 
	}
	
	public POOAction act(POOArena arena){
		POOAction action = new POOAction();
		action.skill = new POOTinyAttackSkill();
		return action;
	}

	public POOCoordinate move(POOArena arena){
		POOCoordinate pos = arena.getPosition(this);
		POOCoordinate border = ((ArenaPark)arena).getSize();
		_direction = _rnd.nextInt(4);
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
}