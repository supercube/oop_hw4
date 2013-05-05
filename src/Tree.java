package ntu.csie.oop13spring;

import java.awt.*;
import java.util.Random;

import javax.swing.ImageIcon;

public class Tree extends Obstacle{
	private static Image _imgs[];
	static{
		_imgs = new Image[2];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Tree.png")).getImage(), new Color(0, 0, 0));
	}
	
	public Tree(){
		setHP(15);
		_img_id = 0;
		
	}
	public Image getImage(){
		return _imgs[_img_id];
	}
	
}
