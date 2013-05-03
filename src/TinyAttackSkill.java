package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class TinyAttackSkill extends Skill{
	
	static {
		_no_imgs = 10;
		_imgs = new Image[_no_imgs];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/TAS.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/TAS_2.png")).getImage(), new Color(0, 0, 0));
	}
	
	public TinyAttackSkill(){
		
		_img_id = 0;
		_ttl = 50;
	}
	
	public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 0)
            pet.setHP(hp - 1);
    }
	
	public boolean oneTimeStep(Cell[][] map, POOCoordinate pos){
		
		_ttl -= 1;
		if(_ttl == 25){
			if(map[pos.x][pos.y].getType() == POOConstant.Type.PET){
				act((Pet)map[pos.x][pos.y].getObject());
			}
		}
		if(_ttl <= 20){
			_img_id = 1;
		}
		return vanish();
	}
}
