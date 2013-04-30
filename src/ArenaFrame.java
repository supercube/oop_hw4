package ntu.csie.oop13spring;

import java.awt.*;
import javax.swing.*;

public class ArenaFrame extends JFrame{
		
	private Container _c;
	private Holder _holder;
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
		setSize(_x,_y);
		setResizable(false);
		setLocationRelativeTo(null);
		
		_c = getContentPane();
		setBackground(background);
		_holder = new Holder(_no_cell_x, _no_cell_y);
		_holder.setOpaque(false);
		_c.add(_holder);
		
		
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
	
	public void addToHolder(String picture, int x, int y){
		System.out.println(x + "/" + _no_cell_x + " & " + y + "/" + _no_cell_y);
		
		if(x < 0 || x >= _no_cell_x || y < 0 || y >= _no_cell_y)
			return;
		
		_holder.addToCell((new ImageIcon(picture)).getImage(), x, y);
		
	}
	

	
	private class Holder extends JPanel{
		public int _no_cell_x;
		public int _no_cell_y;
		public Image _map[][];
		
		public Holder(int no_cell_x, int no_cell_y){
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
	
	public void redraw(){
		_holder.repaint();
	}
}