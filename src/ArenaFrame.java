package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;

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
		setSize(_x + 100 , _y + 100);
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		_c = getContentPane();
		Dimension size = new Dimension(_x, _y);
		_c.setPreferredSize(size);
		pack();
		setBackground(background);
		_panel = new ArenaIOPanel(_no_cell_x, _no_cell_y);
		_panel.setOpaque(false);
		_c.add(_panel);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	public void addToArenaIOPanel(Image img, int x, int y){
		addToArenaIOPanel(img, x, y, -1);
	}
	
	public void addToArenaIOPanel(Image img, int x, int y, int id){
		System.out.println(x + "/" + _no_cell_x + " & " + y + "/" + _no_cell_y);
		
		if(x < 0 || x >= _no_cell_x || y < 0 || y >= _no_cell_y)
			return;
		
		_panel.addToCell(img, x, y, id);
		
	}
	
	public void redraw(){
		_panel.repaint();
	}
}