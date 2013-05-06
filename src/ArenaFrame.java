package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class ArenaFrame extends JFrame{
		
	private Container _c;
	private ArenaIOPanel _panel;
	private int _x;
	private int _y;
	private int _no_cell_x;
	private int _no_cell_y;
	private Image _map[][];
	
	public ArenaFrame(String title, String background, int no_cell_x, int no_cell_y){
		setTitle(title);
		_no_cell_x = no_cell_x;
		_no_cell_y = no_cell_y;
		_x = _no_cell_x * POOConstant.CELL_X_SIZE;
		_y = _no_cell_y * POOConstant.CELL_Y_SIZE;
		_map = new Image[_no_cell_x][];
		for(int i = 0; i < _no_cell_x; i++){
			_map[i] = new Image[_no_cell_y];
			for(int j = 0; j < _no_cell_y; j++){
				_map[i][j] = null;
			}
		}
		
		
		
		_c = getContentPane();
		_c.setPreferredSize(new Dimension(_x, _y));
		_panel = new ArenaIOPanel(_no_cell_x, _no_cell_y);
		_panel.setOpaque(false);
		_c.add(_panel);
		
		setSize(new Dimension(_x+30, _y+10));
		setBackground(background);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void setBackground(String background){
		System.out.println(background);
		((JPanel)this.getContentPane()).setOpaque(false);
		ImageIcon imgi = new ImageIcon(background);
		JLabel img_label = new JLabel(imgi);
		this.getLayeredPane().add(img_label, new Integer(Integer.MIN_VALUE)); 
		img_label.setBounds(0, 0, _x, _y); 
	}
	
	public int addToArenaIOPanel(Image img, int x, int y){
		return addToArenaIOPanel(img, x, y, -1, 0, 0);
	}
	
	public int addToArenaIOPanel(Image img, int x, int y, int id){
		return addToArenaIOPanel(img, x, y, id, 0, 0);
	}
	
	public int addToArenaIOPanel(Image img, int x, int y, int id, int paddingx, int paddingy){
		if(x < 0 || x >= _no_cell_x || y < 0 || y >= _no_cell_y)
			return -1;
		
		return _panel.addToCell(img, x, y, id, paddingx, paddingy);
		
	}
	
	
	public int addToBackground(Image img, int x, int y, int id){
		if(x < 0 || x >= _no_cell_x || y < 0 || y >= _no_cell_y)
			return -1;
		
		return _panel.addToBackground(img, x, y, id);
	}
	
	public int addToBackground(Image img, int x, int y){
		return addToBackground(img, x, y, -1);
	}
	
	public int addToForeground(Image img, int x, int y, int id){
		if(x < 0 || x >= _no_cell_x || y < 0 || y >= _no_cell_y)
			return -1;
		
		return _panel.addToForeground(img, x, y, id);
	}
	
	public int addToForeground(Image img, int x, int y){
		return addToForeground(img, x, y, -1);
	}
	
	public void addFog(POOConstant.Fog[][] fog){
		_panel.addFog(fog);
	}
	
	public void setFog(Image unseen, Image seen){
		_panel.setFog(unseen, seen);
	}
	
	public boolean removeFromIOPanel(int id){
		return _panel.removeFromCell(id);
	}
	
	public boolean addCommandListener(ArrayList<Command> cmds){
		return _panel.addCommandListener(cmds);
	}
	
	public void addPlayer(Pet pet){
		_panel.addPlayer(pet);
	}
	
	public void redraw(){
		_panel.repaint();
	}
}