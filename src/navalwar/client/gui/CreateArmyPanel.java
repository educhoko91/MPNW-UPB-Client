package navalwar.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import navalwar.server.gameengine.UnitAndPlace;

public class CreateArmyPanel extends JPanel {

	private static final int NUM_DISPLAYED_UNITS_PER_ROW = 3;
	private static final int NUM_DISPLAYED_UNITS_PER_COL = 4;
	private static final int SPACE_FOR_UNITS = 90;
	
	private static final int OFFSET_ARMY_X = 60;
	private static final int OFFSET_ARMY_Y = 60;
	
	public static final int ARMY_NUM_ROWS = 10;
	public static final int ARMY_NUM_COLS = 10;

	private static final int OFFSET_UNITS_X = 340;
	private static final int OFFSET_UNITS_Y = 40;


	private JTextField tfArmyName;
	private JLayeredPane layeredPane;
	private ArmyPanel army;
	private UnitPanel[] units;
	private int numUnits;
	
	private int[][] cells;

	public CreateArmyPanel(int numTypeUnits, int[] numUnitsPerType, int[][][] shapeUnits) {

		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		//setLayout(new FlowLayout());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// army name
		
		JPanel armyNamePanel = new JPanel();
		armyNamePanel.setLayout(new BoxLayout(armyNamePanel, BoxLayout.X_AXIS));
		armyNamePanel.add(new JLabel("Army name: "));
		tfArmyName = new JTextField();
		tfArmyName.setBackground(Color.BLACK);
		tfArmyName.setForeground(Color.WHITE);
		armyNamePanel.add(tfArmyName);
		
		
		// battle field and units
		
		cells = new int[ARMY_NUM_ROWS][ARMY_NUM_COLS];
		for(int i = 0; i < ARMY_NUM_ROWS; i++)
			for(int j = 0; j < ARMY_NUM_COLS; j++)
				cells[i][j] = 0;

		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(500, 400));

		army = new ArmyPanel(ARMY_NUM_ROWS, ARMY_NUM_COLS);
		army.setBounds(OFFSET_ARMY_X, OFFSET_ARMY_Y,
				ARMY_NUM_COLS * UnitPanel.CELL_SIZE_X, ARMY_NUM_ROWS * UnitPanel.CELL_SIZE_Y);
		layeredPane.add(army, 0, 0);

		numUnits = 0;
		for(int i = 0; i < numTypeUnits; i++)
			numUnits += numUnitsPerType[i];

		units = new UnitPanel[numUnits];
		int k = -1;
		for(int i = 0; i < numTypeUnits; i++) {
			int[][] shape = shapeUnits[i];
			int shapeRows = shape.length;
			int shapeCols = shape[0].length;

			for(int j = 0; j < numUnitsPerType[i]; j++) {
				k++;
				UnitPanel unit = new UnitPanel(this, army, shapeRows, shapeCols, shape);
				int unitX = OFFSET_UNITS_X + (k % NUM_DISPLAYED_UNITS_PER_ROW) * SPACE_FOR_UNITS;
				int unitY = OFFSET_UNITS_Y + (k / NUM_DISPLAYED_UNITS_PER_ROW) * SPACE_FOR_UNITS;
				unit.setBounds(unitX, unitY, unit.getSizeX(), unit.getSizeY());
				unit.setHome(unitX, unitY);
				layeredPane.add(unit, k+1, 0);
				units[k] = unit;
			}
		}

		add(Box.createRigidArea(new Dimension(0, 10)));
		add(armyNamePanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(layeredPane);
	}
	
	public String getArmyName() { return tfArmyName.getText(); }

	public void resetPanel() {
		tfArmyName.setText("");
		for(int i = 0; i < ARMY_NUM_ROWS; i++)
			for(int j = 0; j < ARMY_NUM_COLS; j++)
				cells[i][j] = 0;
		for(int i = 0; i < numUnits; i++) {
			UnitPanel unit = units[i];
			unit.setBounds(unit.getHomeX(), unit.getHomeY(), unit.getSizeX(), unit.getSizeY());
		}
	}

	public boolean canPlaceUnit(int cellY1, int cellX1, int[][] shape) {
		for(int i = 0; i < shape.length; i++)
			for(int j = 0; j < shape[0].length; j++)
				if ((shape[i][j] == 1) && (cells[cellY1+i][cellX1+j] != 0))
					return false;
		return true;
	}

	public void placeUnit(int cellY1, int cellX1, int[][] shape) {
		for(int i = 0; i < shape.length; i++)
			for(int j = 0; j < shape[0].length; j++)
				cells[cellY1+i][cellX1+j] |= shape[i][j];
	}

	public void takeOffUnit(int row, int col, int[][] shape) {
		for(int i = 0; i < shape.length; i++)
			for(int j = 0; j < shape[0].length; j++)
				if (shape[i][j] == 1) cells[row+i][col+j] = 0; 
		
	}
	
	public boolean areAllUnitsPlaced() {
		for(int i = 0; i < numUnits; i++)
			if (!units[i].isPlaced()) return false;
		return true;
	}
	
	public List<UnitAndPlace> getUnitsAndPlaces() {
		List<UnitAndPlace> list = new ArrayList<UnitAndPlace>();
		for(int i = 0 ; i < numUnits; i++) {
			UnitPanel unit = units[i];
			UnitAndPlace uap = new UnitAndPlace(unit.getName(), unit.getRow(), unit.getCol());
			list.add(uap);
		}
		return list;
	}
	
}
