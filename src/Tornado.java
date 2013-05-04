package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Tornado extends Skill{
	
	protected static Image[] _imgs;
	protected static int _no_imgs;
	private POOConstant.Dir _direction;
	static {
		_no_imgs = 1;
		_imgs = new Image[_no_imgs];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/Tornado.png")).getImage(), new Color(0, 0, 0));
	}
	
	
	public Tornado(Pet pet, POOConstant.Dir direction){
		super(pet);
		_img_id = 0;
		_ttl = 100;
		_direction = direction;
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
        	getPet()._kill_count++;
        }
    }
	
	public boolean oneTimeStep(Arena arena, POOCoordinate pos){
		_ttl -= 1;
		
		Cell[][] map = arena.getMap();
		
		if(_ttl == 85 || _ttl == 65 || _ttl == 45 || _ttl == 25 || _ttl == 5 ){
			if(map[pos.x][pos.y].getType() == POOConstant.Type.PET || map[pos.x][pos.y].getType() == POOConstant.Type.PLAYER){
				act((Pet)map[pos.x][pos.y].getObject());
			}
			map[pos.x][pos.y].removeSkill(this);
			POOCoordinate border = arena.getSize();
			switch(_direction){
				case RIGHT:
					if(pos.x + 1 < border.x)
						pos.x += 1;
					break;
				case DOWN:
					if(pos.y + 1 < border.y)
						pos.y += 1;
					break;
				case LEFT:
					if(pos.x - 1 >= 0)
						pos.x -= 1;
					break;
				case UP:
					if(pos.y - 1 >= 0)
						pos.y -= 1;
					break;
				default:;
			}
			map[pos.x][pos.y].appendSkill(this);
		}
		return vanish();
	}
	
	public static int getCD(){
		return 70;
	}
	
	public static int getMPConsume(){
		return 3;
	}
}
