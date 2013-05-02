package ntu.csie.oop13spring;

import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

public abstract class Pet extends POOPet{
	protected Image[] _imgs;
	protected int _img_id;
	protected int _no_img;
	protected int _sight_range;
	protected Cell[][] _sight;
	protected int _id;
	
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public int getSightRange(){
		return _sight_range;
	}
	
	protected abstract Action Strategy(POOArena aren);
	
	public void setId(int id){
		_id = id;
	}
}
