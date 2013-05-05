package ntu.csie.oop13spring;

import javax.swing.*;

import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.*;

public class ArenaPark extends Arena{

	protected static boolean FOG = true;
	
	
	private Timer _timer;
	private ArenaFrame _window;
	private int _no_cell_x = 40;
	private int _no_cell_y = 20;
	private Cell[][] _map;
	private POOConstant.Fog[][] _fog_of_war;
	
	private boolean _prev_fog = FOG;
	private Random _rnd;
	private int _game_status; /* -1: ?, 0: after init */
	public static final int interval = 10;
	
	private POOPet[] _parr;
	private Coordinate _pet_pos[];
	private ArrayList<Cell> _carr;
	public ArenaPark(){
		try{
			_timer = new Timer(interval, this);
			_rnd = new Random();
			
			_map = new Cell[_no_cell_x][_no_cell_y];
			for(int i = 0; i < _no_cell_x; i++){
				for(int j = 0; j < _no_cell_y; j++){
					_map[i][j] = new Cell(i, j);
				}
			}
			
			_fog_of_war = new POOConstant.Fog[_no_cell_x * 3][_no_cell_y * 3];
			for(int i = 0; i < _no_cell_x * 3; i++){
				for(int j = 0; j < _no_cell_y * 3; j++){
					if(FOG)
						_fog_of_war[i][j] = POOConstant.Fog.UNSEEN;
					else
						_fog_of_war[i][j] = POOConstant.Fog.BRIGHT;
				}
			}
			_window = new ArenaFrame("Park", "Images/Park.png", _no_cell_x, _no_cell_y);
			
			_carr = new ArrayList<Cell>(0);
			
			_game_status = -1;
			
		}catch(Exception e){
			System.out.print("ArenaPark(): ");
			System.out.println(e);
		}
		
	}
	
	public void actionPerformed(ActionEvent e){
		
		/* reset fog of war*/
		if(_prev_fog != FOG){
			for(int i = 0; i < _no_cell_x * 3; i++){
				for(int j = 0; j < _no_cell_y * 3; j++){
					if(FOG)
						_fog_of_war[i][j] = POOConstant.Fog.UNSEEN;
					else
						_fog_of_war[i][j] = POOConstant.Fog.BRIGHT;
				}
			}
			_prev_fog = FOG;
		}
		
		for(int id = 0; id < _carr.size(); id++){
			if(_carr.get(id).getObject() instanceof Skill){
				POOCoordinate pos = _carr.get(id).getPos();
				Skill skill = (Skill)_carr.get(id).getObject();
				if(((Skill)_carr.get(id).getObject()).oneTimeStep(this, pos)){ // should vanish
					_window.removeFromIOPanel(_carr.get(id).getId());
					_map[pos.x][pos.y].removeSkill(skill);
					_carr.remove(id);
					id--;
				}else{
					_window.addToArenaIOPanel(((Skill)_carr.get(id).getObject()).getImage(), _carr.get(id).getPos().x, _carr.get(id).getPos().y, _carr.get(id).getId());
				}
			}
		}
		
		POOCoordinate prev_pos, new_pos;
		ArrayList<Action> actions;
		int tmp;
		boolean dead;
		for(int id = _parr.length - 1; id >= 0; id--){
			if(_parr[id] == null)
				continue;
			
			prev_pos = getPosition(_parr[id]);
			actions = ((Pet)_parr[id]).OneTimeStep(this);
			new_pos = prev_pos;
			dead = false;
			if(actions == null){
			}else{
				Action act;
				while(!actions.isEmpty()){
					act = actions.get(0);
					if(act._type == POOConstant.Type.MOVE){
						new_pos = act._pos;
						if((!prev_pos.equals(new_pos)) && _map[new_pos.x][new_pos.y].add(_map[prev_pos.x][prev_pos.y])){
							_map[prev_pos.x][prev_pos.y].setEmpty();
							_pet_pos[id] = (Coordinate)new_pos;
						}else{
							new_pos = prev_pos;
						}
						
					}else if(act._type == POOConstant.Type.SKILL){
						tmp = _window.addToArenaIOPanel(act._skill.getImage(), act._pos.x, act._pos.y);
						if(tmp != -1){
							_carr.add(new Cell(POOConstant.Type.SKILL, tmp , new Coordinate(act._pos.x, act._pos.y), (Object)(act._skill)));
							_map[act._pos.x][act._pos.y].appendSkill(act._skill);
						}
					}else if(act._type == POOConstant.Type.DEAD){
						_map[prev_pos.x][prev_pos.y].setDead();
						dead = true;
					}
					actions.remove(0);
				}
			}
			
			if(dead){
				_window.removeFromIOPanel(id);
				_window.addToBackground(((Pet)_parr[id]).getImage(), new_pos.x, new_pos.y);
				_parr[id] = null;
			}else{
				_window.addToArenaIOPanel(((Pet)_parr[id]).getImage(), new_pos.x, new_pos.y, id);
			}
			
			/* adjust fog of war */
			if(FOG && id == 0 && prev_pos != new_pos){
				setFog((Pet)_parr[0], prev_pos, POOConstant.Fog.SEEN);
				setFog((Pet)_parr[0], new_pos, POOConstant.Fog.BRIGHT);
			}
		}
		_window.redraw();
	}
	
	public void init(){
		try{
			/* add Obstacle and foreground image to _map in advance */
			int x, y;
			for(int id = 0; id < 15; id++){
				if(id < 10){
					x = _rnd.nextInt(40);
					y = _rnd.nextInt(20);
				}else{
					x = _rnd.nextInt(10)+30;
					y = _rnd.nextInt(7);
				}
				Coordinate pos = new Coordinate(x, y);
				
				if(_map[x][y].add(POOConstant.Type.OBSTACLE, -1, null)){
					_carr.add(new Cell(POOConstant.Type.OBSTACLE, -1, pos, new Tree()));
				}else{
					id--;
				}
			}
			for(int id = 0; id < 50; id++){
				x = _rnd.nextInt(40);
				y = _rnd.nextInt(20);
				int type = _rnd.nextInt(3);
				_window.addToForeground(Grass._imgs[type], x, y);
			}
			
			/* add Pet */
			_parr = getAllPets();
			_pet_pos = new Coordinate[_parr.length];
			for(int id = 0; id < _parr.length; id++){
				_parr[id].setName(Integer.toString(id));
				if(id == 0){
					((Pet)_parr[id]).setPlayer();
					_window.addCommandListener(((Pet)_parr[id]).getCmdListener());
					_window.addPlayer((Pet)_parr[id]);
				}
				
				while(true){
					x = _rnd.nextInt(_no_cell_x);
					y = _rnd.nextInt(_no_cell_y);
					_pet_pos[id] = new Coordinate(x, y);
					_pet_pos[id].x = x;
					_pet_pos[id].y = y;
					if(_map[x][y].add(POOConstant.Type.PET, id, _parr[id])){
						((Pet)_parr[id]).setId(id);
						_window.addToArenaIOPanel(((Pet)_parr[id]).getImage(), x, y, id);
						if(id == 0){
							setFog((Pet)_parr[0], new Coordinate(x, y), POOConstant.Fog.BRIGHT);
							_map[x][y].setEmpty();
							_map[x][y].add(POOConstant.Type.PLAYER, id, _parr[0]);
						}
						break;
					}
				}
			}
			
			/* add Object */
			for(int id = 0; id < 15; id++){
				_window.addToForeground(((Obstacle)_carr.get(id).getObject()).getImage(), _carr.get(id).getPos().x, _carr.get(id).getPos().y);
			}
				
			
			_window.addFog(_fog_of_war);
			_window.setFog((new ImageIcon("Images/black.png")).getImage(), Filter.filterOutBackground((new ImageIcon("Images/fog.png")).getImage(), new Color(255, 255, 255)));
			System.out.println("End init");
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
				return (POOCoordinate) new Coordinate(_pet_pos[id]);
			}
		}
		return null;
	}
	
	public POOCoordinate getSize(){
		return (POOCoordinate) new Coordinate(_no_cell_x, _no_cell_y);
	}
	
	public int getPetId(POOPet pet){
		for(int id = 0; id < _parr.length; id++)
			if(_parr[id] == pet)
				return id;
		return -1;
	}
	
	public Cell[][] getMap(){
		return _map;
	}
	
	public ArrayList<Cell> getSight(POOPet pet){
		int sight_range = ((Pet)pet).getSightRange();
		ArrayList<Cell> sight = new ArrayList<Cell>(0);
		POOCoordinate pos = getPosition(pet);
		int x_lower = 0, y_lower = 0, x_upper = _no_cell_x - 1, y_upper = _no_cell_y - 1;
		if(pos.x - sight_range > x_lower)
			x_lower = pos.x - sight_range;
		if(pos.y - sight_range > y_lower)
			y_lower = pos.y - sight_range;
		if(pos.x + sight_range < x_upper)
			x_upper = pos.x + sight_range;
		if(pos.y + sight_range < y_upper)
			y_upper = pos.y + sight_range;
		
		
		for(int i = x_lower; i <= x_upper; i++){
			for(int j = y_lower; j <= y_upper; j++){
				if(Math.sqrt((i-pos.x)*(i-pos.x)+(j-pos.y)*(j-pos.y)) <= sight_range ){
					sight.add(new Cell(_map[i][j]));
				}
			}
		}
		return sight;
	}
	
	private void setFog(Pet pet, POOCoordinate pos, POOConstant.Fog fog){
		int sight_range = pet.getSightRange() * 3;
		Coordinate new_pos = new Coordinate(pos.x * 3+1, pos.y * 3 +1);
		int x_lower = 0, y_lower = 0, x_upper = 3 * _no_cell_x - 1, y_upper = 3 * _no_cell_y - 1;
		if(new_pos.x - sight_range > x_lower)
			x_lower = new_pos.x - sight_range;
		if(new_pos.y - sight_range > y_lower)
			y_lower = new_pos.y - sight_range;
		if(new_pos.x + sight_range < x_upper)
			x_upper = new_pos.x + sight_range;
		if(new_pos.y + sight_range < y_upper)
			y_upper = new_pos.y + sight_range;
		for(int i = x_lower; i <= x_upper; i++){
			for(int j = y_lower; j <= y_upper; j++){
				if(Math.sqrt((i-new_pos.x)*(i-new_pos.x)+(j-new_pos.y)*(j-new_pos.y)) <= sight_range ){
					_fog_of_war[i][j] = fog;
				}
			}
		}
	}
	
	
}

