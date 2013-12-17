package navalwar.client.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UnitPanel extends JPanel {

	public static final ImageIcon IMG_EMPTY_CELL = new ImageIcon("res/empty_cell.jpg");
	public static final ImageIcon IMG_BLOCK_CELL = new ImageIcon("res/block.jpg");
	public static final ImageIcon IMG_TRANSPARENT_CELL = new ImageIcon("res/transparent_cell.png");
	public static final int CELL_SIZE_X = IMG_EMPTY_CELL.getIconWidth();
	public static final int CELL_SIZE_Y = IMG_EMPTY_CELL.getIconHeight();

	private CreateArmyPanel createArmyPanel;
	private ArmyPanel armyPanel;
	
	private int[][] shape;
	private JLabel[][] cells;
	private int numRows;
	private int numCols;

	private int sizeX;
	private int sizeY;
	
	private int homeX;
	private int homeY;
	
	private int row;
	private int col;

	private UnitPanel instance = null;
	
	private Point mousePt;

	public UnitPanel(CreateArmyPanel createArmyPanel, ArmyPanel armyPanel, int numRows, int numCols, int[][] shape) {
		instance = this;
		
		this.createArmyPanel = createArmyPanel;
		this.armyPanel = armyPanel;
		
		this.shape = shape;
		this.numRows = numRows;
		this.numCols = numCols;
		cells = new JLabel[numRows][numCols];
		
		sizeX = numCols*CELL_SIZE_X;
		sizeY = numRows*CELL_SIZE_Y;

		row = -1;
		col = -1;
		
		this.setLayout(new GridLayout(numRows, numCols));
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				JLabel cell;
				if (shape[i][j] == 0) {
					cell =  null;
					add(new JLabel(IMG_TRANSPARENT_CELL));
				}
				else if (shape[i][j] == 1)
					cell = new JLabel(IMG_BLOCK_CELL);
				else throw new IllegalArgumentException("shpae matrix must have only 0's and 1's");
				cells[i][j] = cell;

				if (cell != null) {
					this.add(cell);
					cell.addMouseListener(new MouseListener() {
						public void mouseReleased(MouseEvent e) {
							
							int cellX1 = (int) Math.round((double) (instance.getX() - instance.armyPanel.getX()) / CELL_SIZE_X);
							int cellY1 = (int) Math.round((double) (instance.getY() - instance.armyPanel.getY()) / CELL_SIZE_Y);
							int cellX2 = cellX1 + instance.numCols - 1;
							int cellY2 = cellY1 + instance.numRows - 1;
							
							if ((cellX1 >= 0) && (cellX1 < instance.armyPanel.getNumCols()) &&
									(cellY1 >= 0) && (cellY1 < instance.armyPanel.getNumRows()) &&
									(cellX2 >= 0) && (cellX2 < instance.armyPanel.getNumCols()) &&
									(cellY2 >= 0) && (cellY2 < instance.armyPanel.getNumRows()) &&
									(instance.createArmyPanel.canPlaceUnit(cellY1, cellX1, instance.shape))) {
								
								instance.createArmyPanel.placeUnit(cellY1, cellX1, instance.shape);
								
								instance.setBounds(
										instance.armyPanel.getX() + cellX1 * CELL_SIZE_X,
										instance.armyPanel.getY() + cellY1 * CELL_SIZE_Y,
										sizeX, sizeY);
								
								row = cellY1;
								col = cellX1;
							}
							else {
								instance.setBounds(homeX, homeY, sizeX, sizeY);
								row = -1;
								col = -1;
							}
							
						}
						public void mousePressed(MouseEvent e) {
							mousePt = e.getPoint();
							if ((row != -1) && (col != -1)) 
								instance.createArmyPanel.takeOffUnit(row, col, instance.shape);
						}
						public void mouseExited(MouseEvent e) { }
						public void mouseEntered(MouseEvent e) { }
						public void mouseClicked(MouseEvent e) { }
					});
					
					cell.addMouseMotionListener(new MouseMotionListener() {
						public void mouseMoved(MouseEvent e) {
						}
						public void mouseDragged(MouseEvent e) {
							int dx = e.getX() - mousePt.x;
							int dy = e.getY() - mousePt.y;
							instance.setBounds(instance.getX() + dx, instance.getY() + dy, sizeX, sizeY);
						}
					});
				}
			}
		}


		setPreferredSize(new Dimension(sizeX, sizeY));
		setMaximumSize(new Dimension(sizeX, sizeY));
		setMinimumSize(new Dimension(sizeX, sizeY));
		setOpaque(false);
	}


	public void setHome(int x, int y) {
		homeX = x;
		homeY = y;
	}
	
	public int getHomeX() { return homeX; }
	public int getHomeY() { return homeY; }
	
	public int getRow() { return row; }
	public int getCol() { return col; }
	

	int getSizeX() { return sizeX; }
	int getSizeY() { return sizeY; }


	public boolean isPlaced() {
		return ((row >= 0) && (row < armyPanel.getNumRows()) &&
				(col >= 0) && (col < armyPanel.getNumCols()));
	}
	
	
	
}
