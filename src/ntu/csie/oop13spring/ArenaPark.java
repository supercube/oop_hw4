package ntu.csie.oop13spring;

import javax.swing.*;

import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.*;

public class ArenaPark extends Arena{
	
	
	/* constructor-decided variables */
	private Timer _timer;
	private Random _rnd;
	private ArenaFrame _window;
	private int _no_cell_x = 40;
	private int _no_cell_y = 20;
	private int _no_fog_x;
	private int _no_fog_y;
	private int _init_count_down;
	public static final int interval = 10;
	
	/* init-decided variables */
	private Cell[][] _map;						// map of arena
	private POOConstant.Fog[][] _fog_of_war;	// fog matrix
	private boolean _prev_fog;					// variable to determine whether to reset fog matrix
	private POOPet[] _parr;
	private Coordinate _pet_pos[];				// positions of pets
	private ArrayList<Cell> _items; 			// cells that will store _items
	
	
	public ArenaPark(){
		try{
			_timer = new Timer(interval, this);
			_rnd = new Random();
			_init_count_down = 50;
			_map = new Cell[_no_cell_x][_no_cell_y];
			_window = new ArenaFrame("Park", "Images/Park.png", _no_cell_x, _no_cell_y);
			_game = new GameInfo(POOConstant.Game.UNDEFINED, true);
			_no_fog_x = _no_cell_x * (POOConstant.CELL_X_SIZE / POOConstant.FOG_X_SIZE);
			_no_fog_y = _no_cell_y * (POOConstant.CELL_Y_SIZE / POOConstant.FOG_Y_SIZE);
			_fog_of_war = new POOConstant.Fog[_no_fog_x][_no_fog_y];
			
			
		}catch(Exception e){
			System.out.print("Constructor ArenaPark(): ");
			System.out.println(e);
		}
		
	}
	
	public void init(){
		try{
			/* generate map for arena */
			for(int i = 0; i < _no_cell_x; i++){
				for(int j = 0; j < _no_cell_y; j++){
					_map[i][j] = new Cell(i, j);
				}
			}
			_items = new ArrayList<Cell>(0);
			_game.reset(POOConstant.Game.INIT, true);
			_window.reset();
			_window.addGameInfo(_game);
			_prev_fog = _game._fog;
			for(int i = 0; i < _no_fog_x; i++){
				for(int j = 0; j < _no_fog_y; j++){
					if(_game._fog)
						_fog_of_war[i][j] = POOConstant.Fog.UNSEEN;
					else
						_fog_of_war[i][j] = POOConstant.Fog.BRIGHT;
				}
			}
			
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
				Tree tree = new Tree();
				if(_map[x][y].add(POOConstant.Type.OBSTACLE, id, tree)){
					_items.add(new Cell(POOConstant.Type.OBSTACLE, id, pos, tree));
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
			int slime_count = 0;
			_parr = getAllPets();
			_pet_pos = new Coordinate[_parr.length];
			for(int id = 0; id < _parr.length; id++){
				((Pet)_parr[id]).reset();
				_parr[id].setName(Integer.toString(id));
				if(id == 0){
					((Pet)_parr[id]).setPlayer(_game._player_control);
					_window.addCommandListener(((Pet)_parr[id]).getCmdListener());
					_window.addPlayer((Pet)_parr[id]);
					if(_parr[0] instanceof Slime){
						_game._player = POOConstant.Type.SLIME;
					}
				}else if(_parr[id] instanceof Slime){
					slime_count++;
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
							setFog(((Pet)_parr[0]).getSightRange(), new Coordinate(x, y), POOConstant.Fog.BRIGHT);
							_map[x][y].setEmpty();
							_map[x][y].add(POOConstant.Type.PLAYER, id, _parr[0]);
						}
						break;
					}
				}
			}
			_game._no_living_target = _parr.length - 1;
			if(_game._player == POOConstant.Type.SLIME){
				_game._no_living_target -= slime_count;
			}
			/* add Object */
			for(int id = 0; id < 15; id++){
				_window.addToForeground(((Obstacle)_items.get(id).getObject()).getImage(), _items.get(id).getPos().x, _items.get(id).getPos().y, id);
			}
			
			/* set Image */
			_window.addFog(_fog_of_war);
			_window.setFogImage((new ImageIcon("Images/black.png")).getImage(), Filter.filterOutBackground((new ImageIcon("Images/fog.png")).getImage(), new Color(255, 255, 255)));
			_window.setStarImage(Filter.filterOutBackground((new ImageIcon("Images/Star.png")).getImage(), new Color(0, 0, 0)));
		}catch(Exception e){
			System.out.print("init(): ");
			System.out.println(e);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		switch(_game._status){
			case INIT:
				_init_count_down--;
				if(_init_count_down <= 0){
					_game._status = POOConstant.Game.INGAME;
					_init_count_down = 50;
				}
				break;
			case INGAME:
			case GAMEOVER:
			case WIN:
				inGameAction(e);
				break;
			case UNDEFINED:
				_game._status = POOConstant.Game.INIT;
				init();
				_timer.start();
				break;
			
			default:;
		}
	}

	public void inGameAction(ActionEvent e){
		/* reset fog of war*/
		if(_prev_fog != _game._fog){
			for(int i = 0; i < _no_fog_x; i++){
				for(int j = 0; j < _no_fog_y; j++){
					if(_game._fog)
						_fog_of_war[i][j] = POOConstant.Fog.UNSEEN;
					else
						_fog_of_war[i][j] = POOConstant.Fog.BRIGHT;
				}
			}
			_prev_fog = _game._fog;
			setFog(((Pet)_parr[0]).getSightRange(), (POOCoordinate)_pet_pos[0], POOConstant.Fog.BRIGHT);
		}
		
		/* oneTimeStep for Skills/Obstacles */
		for(int id = 0; id < _items.size(); id++){
			if(_items.get(id).getObject() instanceof Skill){
				POOCoordinate pos = _items.get(id).getPos();
				Skill skill = (Skill)_items.get(id).getObject();
				if(skill.oneTimeStep(this, pos)){ // should vanish
					_window.removeFromIOPanel(_items.get(id).getId());
					_map[pos.x][pos.y].removeSkill(skill);
					_items.remove(id);
					id--;
				}else{
					_window.addToArenaIOPanel(skill.getImage(), pos.x, pos.y, _items.get(id).getId());
				}
			}else if(_items.get(id).getObject() instanceof Obstacle){
				POOCoordinate pos = _items.get(id).getPos();
				Obstacle ob = (Obstacle)_items.get(id).getObject();
				if(ob.oneTimeStep(this, pos)){ // replace image
					_window.addToForeground(ob.getImage(), pos.x, pos.y, _items.get(id).getId());
					if(ob instanceof Tree){
						_items.remove(id);
						_map[pos.x][pos.y].setEmpty();
					}
				}
				
			}
		}
		
		/* oneTimeStep for Pets */
		POOCoordinate prev_pos, new_pos;
		ArrayList<Action> actions;
		int tmp, prev_sight = 0;
		boolean dead;
		for(int id = _parr.length - 1; id >= 0; id--){
			if(_parr[id] == null || ((Pet)_parr[id]).isDead())
				continue;
			
			prev_pos = getPosition(_parr[id]);
			if(id == 0){
				prev_sight = ((Pet)_parr[0]).getSightRange();
				((Pet)_parr[0]).setPlayer(_game._player_control);
			}
			actions = ((Pet)_parr[id]).oneTimeStep(this);
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
							_items.add(new Cell(POOConstant.Type.SKILL, tmp , new Coordinate(act._pos.x, act._pos.y), (Object)(act._skill)));
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
				((Pet)_parr[id]).confirmDead();
				if(id == 0){
					_game._status = POOConstant.Game.GAMEOVER;
				}else{
					if(!(_parr[id] instanceof Slime) || _game._player != POOConstant.Type.SLIME){
						_game._no_living_target--;
						if(_game._no_living_target == 0){
							_game._status = POOConstant.Game.WIN;
						}
					}
				}
			}else{
				_window.addToArenaIOPanel(((Pet)_parr[id]).getImage(), new_pos.x, new_pos.y, id);
			}
			
			/* adjust fog of war */
			if(_game._fog && id == 0 && (prev_pos != new_pos || prev_sight != ((Pet)_parr[0]).getSightRange())){
				setFog(prev_sight, prev_pos, POOConstant.Fog.SEEN);
				setFog(((Pet)_parr[0]).getSightRange(), new_pos, POOConstant.Fog.BRIGHT);
			}
		}
		_window.redraw();
	}
	
	
	
	/* the entry point */
	public boolean fight(){
			while(true){
				_game._status = POOConstant.Game.INIT;
				init();
				_timer.start();
				try{
					Thread.currentThread().suspend();
					System.out.println("out");
				}catch(Exception e){
					System.out.println("wait err" + e);
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
	
	/* get cells in sight for Pet */
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
				if((i-pos.x)*(i-pos.x)+(j-pos.y)*(j-pos.y) <= sight_range * sight_range){
					sight.add(new Cell(_map[i][j]));
				}
			}
		}
		return sight;
	}
	
	/* adjust fog matrix */
	private void setFog(int sight_range, POOCoordinate pos, POOConstant.Fog fog){
		int x_times = POOConstant.CELL_X_SIZE / POOConstant.FOG_X_SIZE;
		int y_times = POOConstant.CELL_Y_SIZE / POOConstant.FOG_Y_SIZE;
		int x_add = x_times/2, y_add = y_times/2;
		sight_range = sight_range * ((x_times + y_times)/2);
		Coordinate new_pos = new Coordinate(pos.x * x_times + x_add, pos.y * y_times + y_add);
		int x_lower = 0, y_lower = 0, x_upper = _no_fog_x - 1, y_upper = _no_fog_y - 1;
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
				if((i-new_pos.x)*(i-new_pos.x)+(j-new_pos.y)*(j-new_pos.y) <= sight_range * sight_range){
					_fog_of_war[i][j] = fog;
				}
			}
		}
	}
	

	
}

