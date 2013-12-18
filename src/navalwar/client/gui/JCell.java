package navalwar.client.gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public class JCell extends JLabel {
	
	private int row;
	private int col;
	
	public JCell(Icon image, int row, int col) {
		super(image);
		this.row = row;
		this.col = col;
	}
	
	public int getRow() { return row; }
	public int getCol() { return col; }

}
