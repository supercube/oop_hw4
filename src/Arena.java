package ntu.csie.oop13spring;
import java.awt.event.*;

public abstract class Arena extends POOArena implements ActionListener{
	public abstract POOCoordinate getSize();
	public abstract Cell[][] getSight(POOPet pet);
	public abstract int getPetId(POOPet pet);
	public abstract Cell[][] getMap();
}
