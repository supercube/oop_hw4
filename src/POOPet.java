package ntu.csie.oop13spring;

public abstract class POOPet{
    private int HP, MP, AGI;
    private String name;
    
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

    protected final boolean setMP(int MP){
        if (checkMP(MP)){
            this.MP = MP;
            return true;
        }
        else{
            return false;
        }        
    }
    
    protected final boolean setAGI(int AGI){
        if (checkAGI(AGI)){
            this.AGI = AGI;
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
    
    protected final int getAGI(){
        return AGI;
    }

    protected final int getMP(){
        return MP;
    }
    
    protected final int getHP(){
        return HP;
    }
 
    /**
       act defines how the pet would choose a pet
       on the arena (including possibly itself)
       and determine a skill to be used on
       the pet
    */
    protected abstract POOAction act(POOArena arena);

    /**
       move defines how the pet would want to move in an arena;
       note that the range of moving should be related to AGI
     */
    protected abstract POOCoordinate move(POOArena arena);
}
