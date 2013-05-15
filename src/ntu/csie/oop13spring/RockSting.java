package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RockSting extends Skill{
	protected static Image[] _imgs;
	protected static int _no_imgs;
	
	static {
		_no_imgs = 4;
		_imgs = new Image[_no_imgs];
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/RockSting_0.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/RockSting.png")).getImage(), new Color(0, 0, 0));
		_imgs[2] = Filter.filterOutBackground((new ImageIcon("Images/RockSting_2.png")).getImage(), new Color(0, 0, 0));
		_imgs[3] = Filter.filterOutBackground((new ImageIcon("Images/RockSting_3.png")).getImage(), new Color(0, 0, 0));
	
	}
	
	
	public RockSting(Pet pet){
		super(pet);
		_img_id = 1;
		_ttl = 60;
	}
	
	public RockSting(Pet pet, boolean immediate){
		super(pet);
		if(immediate){
			_img_id = 1;
			_ttl = 60;
		}else{
			_img_id = 0;
			_ttl = 75;
		}
	}
	
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 2){
            pet.setHP(hp - 2);
        }else{
        	pet.setHP(0);
        	getPet().getKillReward();;
        }
    }
	
	public void act(Obstacle ob){
        int hp = ob.getHP();
        if (hp > 2){
            ob.setHP(hp - 2);
        }else{
        	ob.setHP(0);
        }
    }
	
	public boolean oneTimeStep(Arena arena, POOCoordinate pos){
		_ttl -= 1;

		Cell[][] map = arena.getMap();
		
		if(_ttl == 40){
			_img_id++;
			POOConstant.Type type = map[pos.x][pos.y].getType();
			if(type == POOConstant.Type.PET || type == POOConstant.Type.PLAYER){
				act((Pet)map[pos.x][pos.y].getObject());
			}else if(type == POOConstant.Type.OBSTACLE){
				act((Obstacle)map[pos.x][pos.y].getObject());
			}
		}else if(_ttl == 50){
			_img_id++;
		}else if(_ttl == 65){
			_img_id++;
		}
		return vanish();
	}
	
	public static int getCD(){
		return 85;
	}
	
	public static int getMPConsume(){
		return 2;
	}
}
