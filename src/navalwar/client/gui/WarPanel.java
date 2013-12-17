package navalwar.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class WarPanel extends JPanel {

	private ArmyPanel[][] fields;
	private int numRows;
	private int numCols;
	
	public WarPanel(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		fields = new ArmyPanel[numRows][numCols];
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				fields[i][j] = new ArmyPanel(10, 10);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j;
				c.gridy = i;
				c.insets = new Insets(10,10,10,10);
				this.add(fields[i][j], c);
			}
		}
	}

}
