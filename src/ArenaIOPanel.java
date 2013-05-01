package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

public class ArenaIOPanel extends JPanel{
	
	private int _no_cell_x;
	private int _no_cell_y;
	private ImageCell[] _imgcs;
	private int _imgcs_len, _max_imgcs;
	
	public ArenaIOPanel(int no_cell_x, int no_cell_y){
		_no_cell_x = no_cell_x;
		_no_cell_y = no_cell_y;
		_max_imgcs = 2 * _no_cell_x * _no_cell_y;
		_imgcs = new ImageCell[_max_imgcs];
		for(int i = 0; i < _max_imgcs; i++ )
			_imgcs[i] = null;
		_imgcs_len = 0;
	}
	
	public void addToCell(Image img, int x, int y, int id){
		ImageCell imgc = new ImageCell(img, x, y);
		if(id < 0){
			_imgcs[_imgcs_len] = imgc;
			id = _imgcs_len;
		}else if(id < _max_imgcs){
			_imgcs[id] = imgc;
		}
		
		if(id >= _imgcs_len){
			_imgcs_len = id + 1;
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		for(int i = 0; i < _imgcs_len; i++){
			if(_imgcs[i] != null)
				g.drawImage(_imgcs[i]._img, _imgcs[i]._x * POOConstant.CELL_X_SIZE, _imgcs[i]._y * POOConstant.CELL_Y_SIZE, this);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	private class ImageCell{
		public Image _img;
		public int _x, _y;
		
		public ImageCell(Image img, int x, int y){
			_img = img;
			_x = x;
			_y = y;
		}
	}
}