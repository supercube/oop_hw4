package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.ImageIcon;

public class Grass{
	public final static Image[] _imgs = new Image[10];
	static{
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Grass.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/Grass_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[2] = Filter.filterOutBackground((new ImageIcon("Images/Grass_3.png")).getImage(), new Color(0, 0, 0));
	}
}
