package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RockSting extends Skill{
	protected static Image[] _imgs;
	protected static int _no_imgs;
	
	static {
		_no_imgs = 3;
		_imgs = new Image[_no_imgs];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/RockSting.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/RockSting_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/RockSting_3.png")).getImage(), new Color(0, 0, 0));
	}
	
	
	public RockSting(){
		_img_id = 0;
		_ttl = 70;
	}
	
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 4){
            pet.setHP(hp - 4);
        }else{
        	pet.setHP(0);
        }
    }
	
	public boolean oneTimeStep(Cell[][] map, POOCoordinate pos){
		
		_ttl -= 1;
		if(_ttl == 30){
			_img_id++;
			if(map[pos.x][pos.y].getType() == POOConstant.Type.PET){
				act((Pet)map[pos.x][pos.y].getObject());
			}
		}
		if(_ttl == 50){
			_img_id++;
		}
		return vanish();
	}
	
	public static int getCD(){
		return 100;
	}
	
	public static int getMPConsume(){
		return 2;
	}
}
