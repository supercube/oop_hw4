package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

public class Pet extends POOPet{
	private Image[] _imgs;
	private int _img_id;
	private static final int _no_img = 10;
	
	public Pet(){
		_imgs = new Image[_no_img];
		Image img;
		Color bgcolor;
		img = (new ImageIcon("../Images/Slime.png")).getImage();
		bgcolor = new Color(0, 0, 0);
		_imgs[0] = Filter.filterOutBackground(img, bgcolor);
		_img_id = 0;
	}
	
	public Image getImage(){
		return _imgs[_img_id];
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

class Filter {
  public static Image filterOutBackground(Image image, Color bgColor) {
    final int filterOutValue = bgColor.getRGB() & 0xFFFFFF;
    RGBImageFilter filter = new RGBImageFilter() {
      public int filterRGB(int x, int y, int rgb) {
        return (rgb & 0xFFFFFF) == filterOutValue? 0 : rgb;
      }
    };
    FilteredImageSource producer = new FilteredImageSource(image.getSource(), filter);
    return Toolkit.getDefaultToolkit().createImage(producer);
  }
}
