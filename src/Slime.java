package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Slime extends Pet{
	
	public Slime(){
		_no_img = 10;
		_imgs = new Image[_no_img];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Slime.png")).getImage(), new Color(0, 0, 0));
		_img_id = 0;
	}
	
	public POOAction act(POOArena arena){
		POOAction action = new POOAction();
		action.skill = new POOTinyAttackSkill();
		return action;
	}

	public POOCoordinate move(POOArena arena){
		return arena.getPosition(this);
	}
}