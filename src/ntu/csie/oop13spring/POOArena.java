package ntu.csie.oop13spring;

import java.util.*;

public abstract class POOArena{
    private ArrayList<POOPet> allpets = new ArrayList<POOPet>(0);
    
    public void addPet(POOPet p){
        allpets.add(p);
    }
    
    public final POOPet[] getAllPets(){
        POOPet[] parr = new POOPet[0];
        return allpets.toArray(parr);
    }
    
    public abstract boolean fight();
    public abstract void show();
    public abstract POOCoordinate getPosition(POOPet p);
}
