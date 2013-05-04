package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

public class TinyAttackSkill extends Skill{
	
	protected static Image[] _imgs;
	protected static int _no_imgs;
	
	static {
		_no_imgs = 2;
		_imgs = new Image[_no_imgs];
		_imgs[0] = Filter.filterOutBackground((new ImageIcon("Images/TAS.png")).getImage(), new Color(0, 0, 0));
		_imgs[1] = Filter.filterOutBackground((new ImageIcon("Images/TAS_2.png")).getImage(), new Color(0, 0, 0));
	}
	
	
	public TinyAttackSkill(){
		
		_img_id = 0;
		_ttl = 30;
	}
	
	public Image getImage(){
		return _imgs[_img_id];
	}
	
	public void act(POOPet pet){
        int hp = pet.getHP();
        if (hp > 0)
            pet.setHP(hp - 1);
    }
	
	public boolean oneTimeStep(Arena arena, POOCoordinate pos){
		_ttl -= 1;
		
		Cell[][] map = arena.getMap();
		
		if(_ttl == 20){
			if(map[pos.x][pos.y].getType() == POOConstant.Type.PET || map[pos.x][pos.y].getType() == POOConstant.Type.PLAYER){
				act((Pet)map[pos.x][pos.y].getObject());
			}
		}
		if(_ttl <= 15){
			_img_id = 1;
		}
		return vanish();
	}
	
	public static int getCD(){
		return 35;
	}
	
	public static int getMPConsume(){
		return 1;
	}
}
