package ntu.csie.oop13spring;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

public class ArenaIOPanel extends JPanel{
	
	private int _no_cell_x;
	private int _no_cell_y;
	private int _no_fog_x;
	private int _no_fog_y;
	private int _x, _y;
	private ArrayList<Command> _cmds;
	
	private ImageCell[] _imgcs;
	private ImageCell[] _backgroundcs;
	private ImageCell[] _foregroundcs;
	private POOConstant.Fog[][] _fog;
	private Image _unseen, _seen;
	private int _imgcs_len, _bgcs_len, _fgcs_len, _max_imgcs;
	private Pet _player;
	
	private static Image _star = Filter.filterOutBackground((new ImageIcon("Images/Star.png")).getImage(), new Color(0, 0, 0));
	
	public ArenaIOPanel(int no_cell_x, int no_cell_y){
		_no_cell_x = no_cell_x;
		_no_cell_y = no_cell_y;
		_no_fog_x = _no_cell_x * (POOConstant.CELL_X_SIZE / POOConstant.FOG_X_SIZE);
		_no_fog_y = _no_cell_y * (POOConstant.CELL_Y_SIZE / POOConstant.FOG_Y_SIZE);
		_max_imgcs = 3 * _no_cell_x * _no_cell_y;
		_imgcs = new ImageCell[_max_imgcs];
		for(int i = 0; i < _max_imgcs; i++ )
			_imgcs[i] = null;
		_imgcs_len = 0;
		
		_backgroundcs = new ImageCell[_max_imgcs];
		for(int i = 0; i < _max_imgcs; i++ )
			_backgroundcs[i] = null;
		_bgcs_len = 0;
		
		_foregroundcs = new ImageCell[_max_imgcs];
		for(int i = 0; i < _max_imgcs; i++ )
			_foregroundcs[i] = null;
		_fgcs_len = 0;
		
		_x = _no_cell_x * POOConstant.CELL_X_SIZE;
		_y = _no_cell_y * POOConstant.CELL_Y_SIZE;
		
		addKeyListener(new Adapter());
		setFocusable(true);
	}
	
	public int addToCell(Image img, int x, int y, int id, int paddingx, int paddingy){
		if(id >= _max_imgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y, paddingx, paddingy);
		if(id < 0 && _imgcs_len < _max_imgcs - 1){
			_imgcs[_imgcs_len] = imgc;
			id = _imgcs_len;
		}else if(id < _max_imgcs){
			_imgcs[id] = imgc;
		}
		
		if(id >= _imgcs_len){
			_imgcs_len = id + 1;
		}
		return id;
	}
	
	public int addToBackground(Image img, int x, int y, int id){
		if(id >= _max_imgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y, 0, 0);
		if(id < 0 && _bgcs_len < _max_imgcs - 1){
			_backgroundcs[_bgcs_len] = imgc;
			id = _bgcs_len;
		}else if(id < _max_imgcs){
			_backgroundcs[id] = imgc;
		}
		
		if(id >= _bgcs_len){
			_bgcs_len = id + 1;
		}
		return id;
	}
	
	public int addToForeground(Image img, int x, int y, int id){
		if(id >= _max_imgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y, 0, 0);
		if(id < 0 && _fgcs_len < _max_imgcs - 1){
			_foregroundcs[_fgcs_len] = imgc;
			id = _fgcs_len;
		}else if(id < _max_imgcs){
			_foregroundcs[id] = imgc;
		}
		
		if(id >= _fgcs_len){
			_fgcs_len = id + 1;
		}
		return id;
	}
	
	public void addFog(POOConstant.Fog[][] fog){
		_fog = fog;
	}
	
	public void setFog(Image unseen, Image seen){
		_unseen = unseen;
		_seen = seen;
	}
	
	public boolean removeFromCell(int id){
		if(id >= _imgcs_len || id < 0)
			return false;
		
		_imgcs[id] = null;
		if(id == _imgcs_len - 1)
			_imgcs_len--;
		
		return true;
	}
	
	public boolean addCommandListener(ArrayList<Command> cmds){
		_cmds = cmds;
		return true;
	}
	
	public void addPlayer(Pet pet){
		_player = pet;
	}
	
	public void paint(Graphics g) {
		
		int x_times = POOConstant.CELL_X_SIZE / POOConstant.FOG_X_SIZE;
		int y_times = POOConstant.CELL_Y_SIZE / POOConstant.FOG_Y_SIZE;
		int x_add = x_times/2, y_add = y_times/2;
		
		/* draw background units (dead body)*/
		for(int i = 0; i < _bgcs_len; i++){
			if(_fog[_backgroundcs[i]._x * x_times + x_add][_backgroundcs[i]._y * y_times + y_add] == POOConstant.Fog.BRIGHT){
				_backgroundcs[i]._seen = true;
			}
			if(_backgroundcs[i] != null && _backgroundcs[i]._seen){
				g.drawImage(_backgroundcs[i]._img, _backgroundcs[i]._x * POOConstant.CELL_X_SIZE + _backgroundcs[i]._paddingx, _backgroundcs[i]._y * POOConstant.CELL_Y_SIZE + _backgroundcs[i]._paddingy, this);
			}
		}
		
		/* draw units */
		for(int i = 0; i < _imgcs_len; i++){
			if(_imgcs[i] != null && _fog[_imgcs[i]._x * x_times + x_add][_imgcs[i]._y * y_times + y_add] == POOConstant.Fog.BRIGHT){
				g.drawImage(_imgcs[i]._img, _imgcs[i]._x * POOConstant.CELL_X_SIZE + _imgcs[i]._paddingx, _imgcs[i]._y * POOConstant.CELL_Y_SIZE + _imgcs[i]._paddingy, this);
			}
		}
		
		/* draw foreground units */
		for(int i = 0; i < _fgcs_len; i++){
			if(_fog[_foregroundcs[i]._x * x_times + x_add][_foregroundcs[i]._y * y_times + y_add] == POOConstant.Fog.BRIGHT){
				_foregroundcs[i]._seen = true;
			}
			if(_foregroundcs[i] != null && _foregroundcs[i]._seen){
				g.drawImage(_foregroundcs[i]._img, _foregroundcs[i]._x * POOConstant.CELL_X_SIZE + _foregroundcs[i]._paddingx, _foregroundcs[i]._y * POOConstant.CELL_Y_SIZE + _foregroundcs[i]._paddingy, this);
			}
		}
		
		/* draw fog of war */
		for(int i = 0; i < _no_fog_x; i++){
			for(int j = 0; j < _no_fog_y; j++){
				switch(_fog[i][j]){
					case UNSEEN:
						g.drawImage(_unseen, i * (POOConstant.FOG_X_SIZE), j * (POOConstant.FOG_Y_SIZE), this);
						break;
					case SEEN:
						g.drawImage(_seen, i * (POOConstant.FOG_X_SIZE), j * (POOConstant.FOG_Y_SIZE), this);
						break;
					default:;
				}
			}
		}
		
		/* draw player info */
		//"  MP: " + _player.getMP() + "  Anger: " + _player.getAnger() + "/" + _player.getMaxAnger() + "  kills: " + _player._kill_count;
		int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
		int fontSize = (int)Math.round(10.0 * screenRes / 72.0);
	    Font font = new Font("Arial", Font.BOLD, fontSize);
	    g.setFont(font);
		g.setColor(Color.red);
        g.drawString("HP: ", 5, 15);
        g.fillRect(40, 7, _player.getHP()*4, 7);
        g.setColor(Color.blue);
        g.drawString("MP: ", 5, 30);
        g.fillRect(40, 22, _player.getMP()*4, 7);
        if(_player.getAnger() >= _player.getMaxAnger() || _player.isAngry()){
            g.setColor(new Color(255, 110, 0));
        }else{
        	g.setColor(Color.yellow);
        }
        g.drawString("Ang: ", 5, 45);
        g.fillRect(40, 37, _player.getAnger()*4, 7);
        for(int i = 0; i < _player._kill_count; i++){
        	g.drawImage(_star, 5 + i * 10, 50, this);
        }
        
	}
	
	private class ImageCell{
		public Image _img;
		public int _x, _y, _paddingx, _paddingy;
		public boolean _seen;
		public ImageCell(Image img, int x, int y, int paddingx, int paddingy){
			_img = img;
			_x = x;
			_y = y;
			_paddingx = paddingx;
			_paddingy = paddingy;
			_seen = false;
		}
	}
	
	private class Adapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			
				
            int key = e.getKeyCode();
            /* functional key */
            switch(key){
            	case KeyEvent.VK_F1:
            		ArenaPark.FOG = !(ArenaPark.FOG);
            		break;
            }
            
            /* player key */
            switch(key){
            	case KeyEvent.VK_UP:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.UP));
            		break;
            	case KeyEvent.VK_DOWN:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.DOWN));
            		break;
            	case KeyEvent.VK_LEFT:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.LEFT));
            		break;
            	case KeyEvent.VK_RIGHT:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.RIGHT));
            		break;
            	case KeyEvent.VK_Z:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.Z));
            		break;
            	case KeyEvent.VK_X:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.X));
            		break;
            	case KeyEvent.VK_SPACE:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.SPACE));
            		break;
            	default:;
            }
        }
	}
	
}