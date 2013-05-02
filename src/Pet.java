package ntu.csie.oop13spring;

import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

public abstract class Pet extends POOPet{
	protected Image[] _imgs;
	protected int _img_id;
	protected int _no_img;
	protected Cell[][] _sight;
	protected int _id;
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	protected abstract POOCoordinate Strategy(POOArena aren);
	
	public void setId(int id){
		_id = id;
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
