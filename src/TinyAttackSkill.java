package ntu.csie.oop13spring;

import java.awt.Color;

import javax.swing.ImageIcon;

public class TinyAttackSkill extends Skill{
	
	public TinyAttackSkill(){
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/TAS.png")).getImage(), new Color(0, 0, 0));	
		_img_id = 0;
		
	}
	
	public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 0)
            pet.setHP(hp - 1);
    }
}
