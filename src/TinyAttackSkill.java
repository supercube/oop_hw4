package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class TinyAttackSkill extends Skill{
	
	public TinyAttackSkill(){
		_imgs = new Image[10];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/TAS.png")).getImage(), new Color(0, 0, 0));
		_img_id = 0;
		_ttl = 3;
	}
	
	public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 0)
            pet.setHP(hp - 1);
    }
	
	public boolean oneTimeStep(POOArena arena){
		
		_ttl -= 1;
		return vanish();
	}
}
