package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class ArenaIOPanel extends JPanel{
	
	private int _no_cell_x;
	private int _no_cell_y;
	private Image _map[][];
	
	public ArenaIOPanel(int no_cell_x, int no_cell_y){
		_no_cell_x = no_cell_x;
		_no_cell_y = no_cell_y;
		_map = new Image[_no_cell_x][];
		for(int i = 0; i < _no_cell_x; i++){
			_map[i] = new Image[_no_cell_y];
			for(int j = 0; j < _no_cell_y; j++){
				_map[i][j] = null;
			}
		}
	}
	
	public void addToCell(Image img, int x, int y){
		_map[x][y] = img;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < _no_cell_x; i++){
			for(int j = 0; j < _no_cell_y; j++){
				if(_map[i][j] != null){
					g.drawImage(_map[i][j], i * POOConstant.CELL_X_SIZE, j * POOConstant.CELL_Y_SIZE, this);
					System.out.println("paint");
				}
			}
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
}