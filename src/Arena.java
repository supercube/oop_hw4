package ntu.csie.oop13spring;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class Arena extends POOArena implements ActionListener{
	public abstract POOCoordinate getSize();
	public abstract ArrayList<Cell> getSight(POOPet pet);
	public abstract int getPetId(POOPet pet);
	public abstract Cell[][] getMap();
}
