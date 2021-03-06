package ntu.csie.oop13spring;
public class POOConstant{
	
	public static final int CELL_X_SIZE = 30;
	public static final int CELL_Y_SIZE = 30;
	public static final int FOG_X_SIZE = 10;
	public static final int FOG_Y_SIZE = 10;
	public static final int SlowTTA = 40;
	
	public static enum Type{
		EMPTY, PLAYER, PET, OBSTACLE, MOVE, SKILL, DEAD, SLIME, ROCKARM, TORLION
	}
	
	public static enum Cmd{
		UP, DOWN, LEFT, RIGHT, Z, X, SPACE, F1
	}
	
	public static enum Dir{
		UP, DOWN, LEFT, RIGHT;
		
		public static Dir getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }
	}
	
	public static enum Skill{
		None, TinyAttackSkill, RockSting, Tornado
	}
	
	public static enum Fog{
		UNSEEN, SEEN, BRIGHT
	}
	
	public static enum Game{
		UNDEFINED, INIT, INGAME, GAMEOVER, WIN, END
	}
}