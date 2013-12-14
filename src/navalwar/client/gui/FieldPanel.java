package navalwar.client.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FieldPanel extends JPanel {
	
	public static ImageIcon IMG_EMPTY_CELL = new ImageIcon("res/empty_cell.jpg");
	public static ImageIcon IMG_BLOCK_CELL = new ImageIcon("res/block.jpg");
	
	private JLabel[][] cells;
	private int numRows;
	private int numCols;
	
	
	public FieldPanel(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		cells = new JLabel[numRows][numCols];

		this.setLayout(new GridLayout(numRows, numCols));
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				JLabel cell = new JLabel(IMG_EMPTY_CELL);
				cells[i][j] = cell;
				this.add(cell);
				
				cell.addMouseListener(new MouseListener() {
					
					public void mouseReleased(MouseEvent e) {
					}
					
					public void mousePressed(MouseEvent e) {
					}
					
					public void mouseExited(MouseEvent e) {
					}
					
					public void mouseEntered(MouseEvent e) {
//						System.out.println("yes field");
//						((JLabel)(e.getSource())).getParent().dispatchEvent(e);
					}
					
					public void mouseClicked(MouseEvent e) {
						((JLabel)(e.getSource())).setIcon(IMG_BLOCK_CELL);
					}
				});
			}
		}
		
		setPreferredSize(new Dimension(numRows*20, numCols*20));
		setMaximumSize(new Dimension(numRows*20, numCols*20));
		setMinimumSize(new Dimension(numRows*20, numCols*20));
	}
	
	
	
	
	
	
	

}
