package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.*;

public class ArenaPark extends POOArena implements ActionListener{

	private Timer _timer;
	private ArenaFrame _window;
	private int _no_cell_x = 40;
	private int _no_cell_y = 20;
	private Cell[][] _map; 
	private ArrayList<Coordinate> _pet_pos = new ArrayList<Coordinate>(0);
	private Random rnd;
	private int _game_status; /* -1: ?, 0: after init */
	public static final int interval = 300;
	
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
		POOCoordinate pos;
		for(int id = 0; id < _parr.length; id++){
			pos = ((Pet)_parr[id]).move(this);
			_window.addToArenaIOPanel(((Pet)_parr[id]).getImage(), pos.x, pos.y, id);
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
					if(_map[x][y].add(1, id)){
						_pet_pos.add(new Coordinate(x, y));
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
}

