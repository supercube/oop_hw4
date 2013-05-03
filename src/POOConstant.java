package ntu.csie.oop13spring;
public class POOConstant{
	
	public static final int CELL_X_SIZE = 30;
	public static final int CELL_Y_SIZE = 30;
	public static enum Type{
		EMPTY, PET, OBSTACLE, MOVE, SKILL
	}
	
	public static enum Cmd{
		UP, DOWN, LEFT, RIGHT
	}
	
	public static enum Dir{
		UP, DOWN, LEFT, RIGHT;
		
		public static Dir getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }
	}
}