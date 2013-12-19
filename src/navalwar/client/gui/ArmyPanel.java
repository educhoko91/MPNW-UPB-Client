package navalwar.client.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArmyPanel extends JPanel {
	
	public static ImageIcon IMG_EMPTY_CELL = new ImageIcon("res/empty_cell.jpg");
	public static ImageIcon IMG_SHADOW_CELL = new ImageIcon("res/shadow_cell.jpg");
	public static ImageIcon IMG_BLOCK_CELL = new ImageIcon("res/block.jpg");
	public static ImageIcon IMG_SHOT_CELL = new ImageIcon("res/shot_cell.jpg");
	public static ImageIcon IMG_SHOT_BLOCK_CELL = new ImageIcon("res/shot_block.jpg");
	
	
	private ArmyPanel instance;
	
	private int armyID;
	private String armyName;

	private JCell[][] cells;
	private int numRows;
	private int numCols;
	private WarPanel warPanel;
	
	private boolean isClickable;
	private boolean isShadow;
	
	
	public ArmyPanel(WarPanel warPanel, int numRows, int numCols, boolean isShadow) {
		instance = this;
		this.warPanel = warPanel;
		this.numRows = numRows;
		this.numCols = numCols;
		cells = new JCell[numRows][numCols];
		
		isClickable = false;
		this.isShadow = isShadow;

		this.setLayout(new GridLayout(numRows, numCols));
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				JCell cell;
				if (!isShadow) cell = new JCell(IMG_EMPTY_CELL, i, j);
				else cell = new JCell(IMG_SHADOW_CELL, i, j);
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
						if (isClickable) {
							JCell cell = ((JCell)(e.getSource()));
							instance.warPanel.notifyClickOnCell(armyID, cell.getRow(), cell.getCol());
						}
					}
				});
			}
		}
		
		setPreferredSize(new Dimension(numRows*UnitPanel.CELL_SIZE_X, numCols*UnitPanel.CELL_SIZE_Y));
		setMaximumSize(new Dimension(numRows*UnitPanel.CELL_SIZE_X, numCols*UnitPanel.CELL_SIZE_Y));
		setMinimumSize(new Dimension(numRows*UnitPanel.CELL_SIZE_X, numCols*UnitPanel.CELL_SIZE_Y));
	}
	
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	
	public void setArmyID(int armyID) { this.armyID = armyID; }
	
	public void setClickable(boolean c) {
		isClickable = c;
	}

	public void drawUnit(String unitName, int row, int col) {
		int[][] shape = NavalWarGUI.shapes.get(unitName);
		for(int i = 0; i < shape.length; i++)
			for(int j = 0; j < shape[0].length; j++) {
				if (shape[i][j] == 1)
					
					drawBlock(row+i, col+j);
			}
		
	}

	private void drawBlock(int row, int col) {
		cells[row][col].setIcon(IMG_BLOCK_CELL);
	}

	public void drawShot(int row, int col, boolean inTarget) {
		if (inTarget) cells[row][col].setIcon(IMG_SHOT_BLOCK_CELL);
		else cells[row][col].setIcon(IMG_SHOT_CELL);
	}

	public void resetArmy() {
		armyID = -1;
		for(int i = 0; i < numRows; i++)
			for(int j = 0; j < numCols; j++) {
				if (isShadow) cells[i][j].setIcon(IMG_SHADOW_CELL);
				else cells[i][j].setIcon(IMG_EMPTY_CELL);
			}
	
	}

	public void setArmyName(String armyName) {
		this.armyName = armyName;
	}
	
	public String getArmyName() { return armyName; }

}
