package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.*;

public class ArenaPark extends Arena{

	private Timer _timer;
	private ArenaFrame _window;
	private int _no_cell_x = 40;
	private int _no_cell_y = 20;
	private Cell[][] _map; 
	private ArrayList<Coordinate> _pet_pos = new ArrayList<Coordinate>(0);
	private Random rnd;
	private int _game_status; /* -1: ?, 0: after init */
	public static final int interval = 1000;
	
	private POOPet[] _parr;
	
	public ArenaPark(){
		try{
			_timer = new Timer(interval, this);
			_map = new Cell[_no_cell_x][];
			for(int i = 0; i < _no_cell_x; i++){
				_map[i] = new Cell[_no_cell_y];
				for(int j = 0; j < _no_cell_y; j++){
					_map[i][j] = new Cell();
				}
			}
			_window = new ArenaFrame("Park", "Images/Park.png", _no_cell_x, _no_cell_y);
			_game_status = -1;
			rnd = new Random();
		}catch(Exception e){
			System.out.print("ArenaPark(): ");
			System.out.println(e);
		}
		
	}
	
	public void actionPerformed(ActionEvent e){
		POOCoordinate prev_pos, new_pos;
		for(int id = 0; id < _parr.length; id++){
			prev_pos = getPosition(_parr[id]);
			new_pos = ((Pet)_parr[id]).Strategy(this);
			if(!prev_pos.equals(new_pos) && _map[new_pos.x][new_pos.y].add(1, id, _parr[id])){
				_map[prev_pos.x][prev_pos.y].setEmpty();
			}
			_window.addToArenaIOPanel(((Pet)_parr[id]).getImage(), new_pos.x, new_pos.y, id);
		}
		_window.redraw();
	}
	
	public void init(){
		try{
			_parr = getAllPets();
			int x, y;
			for(int id = 0; id < _parr.length; id++){
				while(true){
					x = rnd.nextInt(_no_cell_x);
					y = rnd.nextInt(_no_cell_y);
					if(_map[x][y].add(1, id, _parr[id])){
						_pet_pos.add(new Coordinate(x, y));
						((Pet)_parr[id]).setId(id);
						_window.addToArenaIOPanel(((Pet)_parr[id]).getImage(), x, y, id);
						break;
					}
				}
			}
		}catch(Exception e){
			System.out.print("init(): ");
			System.out.println(e);
		}
	}
	
	public boolean fight(){
		while(true){
			switch(_game_status){
				case 0:
					
					break;
				case -1:
					init();
					_game_status = 0;
					_timer.start();
					break;
				default:;
			}
		}
	}
	
	public void show(){
		_window.redraw();
	}
	
	public POOCoordinate getPosition(POOPet p){
		for(int id = 0; id < _parr.length; id++){
			if(p == _parr[id]){
				return _pet_pos.get(id);
			}
		}
		return null;
	}
	
	public POOCoordinate getSize(){
		return (POOCoordinate) new Coordinate(_no_cell_x, _no_cell_y);
	}
	
	public Cell[][] getSight(POOPet pet){
		Cell[][] sight = new Cell[5][5];
		for(int i = 0; i < 5; i++){
			sight[i] = new Cell[5];
			for(int j = 0; j < 5; j++)
				sight[i][j] = null;
		}
		POOCoordinate pos = getPosition(pet);
		int x_lower = 0, y_lower = 0, x_upper = _no_cell_x - 1, y_upper = _no_cell_y - 1;
		if(pos.x - 2 > x_lower)
			x_lower = pos.x - 2;
		if(pos.y - 2 > y_lower)
			y_lower = pos.y - 2;
		if(pos.x + 2 < x_upper)
			x_upper = pos.x + 2;
		if(pos.y + 2 < y_upper)
			y_upper = pos.y + 2;
		System.out.println(pos.x + ", " + pos.y + ": " + x_lower + "-" + x_upper + " , "+ y_lower + "-" + y_upper);
		for(int i = x_lower; i <= x_upper; i++){
			for(int j = y_lower; j <= y_upper; j++){
				sight[i - pos.x + 2][j - pos.y + 2] = new Cell(_map[i][j]);
				System.out.println((i-pos.x+2) + ", " + (j-pos.y+2) + " <- " + i + ", " + j);
				if(i == pos.x && j == pos.y){
					System.out.println(sight[i - pos.x + 2][j - pos.y + 2].getType());
					System.out.println(_map[i][j].getType());
				}
			}
		}
		for(int id = 0; id < _parr.length; id++)
			if(_parr[id] == pet)
				System.out.print("*" + id);
		return sight;
	}
}

