package ntu.csie.oop13spring;

import java.awt.Image;

public abstract class Obstacle{
    private int HP;
    private String name;
    
    protected int _img_id;
    
    public abstract Image getImage();
    
    static protected final boolean checkHP(int _HP){
        return (_HP >= 0 && _HP < 1024);
    }

    static protected final boolean checkMP(int _MP){
        return (_MP >= 0 && _MP < 1024);
    }
    
    static protected final boolean checkAGI(int _AGI){
        return (_AGI >= 0 && _AGI < 1024);
    }
    
    protected final boolean setHP(int HP){
        if (checkHP(HP)){
            this.HP = HP;
            return true;
        }
        else{
            return false;
        }        
    }
  
    
    protected final boolean setName(String name){
        if (name != null){
            this.name = name;
            return true;
        }
        else{
            return false;
        }
    }
    
    protected final String getName(){
        return name;
    }
    
    protected final int getHP(){
        return HP;
    }
}
