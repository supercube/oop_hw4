package ntu.csie.oop13spring;

import java.awt.*;

import javax.swing.ImageIcon;

public class Tree extends Obstacle{
	private static Image _imgs[];
	static{
		_imgs = new Image[3];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Tree.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/Tree_dead.png")).getImage(), new Color(0, 0, 0));
	}
	public Tree(){
		setHP(4);
		_img_id = 0;
		
	}
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public boolean oneTimeStep(Arena arena, POOCoordinate pos){
		if(getHP() <= 0){
			_img_id = 1;
			return true;
		}
		return false;
	}
}
