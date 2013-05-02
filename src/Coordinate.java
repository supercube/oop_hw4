package ntu.csie.oop13spring;

public class Coordinate extends POOCoordinate{
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(POOCoordinate cor){
		this((Coordinate)cor);
	}
	
	public Coordinate(Coordinate cor){
		this.x = cor.x;
		this.y = cor.y;
	}
	
	public boolean equals(POOCoordinate other){
		return (x == ((Coordinate)other).x && y == ((Coordinate)other).y);
	}
}