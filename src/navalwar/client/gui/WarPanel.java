package navalwar.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import navalwar.server.gameengine.UnitAndPlace;

public class WarPanel extends JPanel {

	private ArmyPanel ownArmy;
	private Map<Integer, ArmyPanel> enemiesArmies;
	private int numEnemiesArmies;
	private ArmyPanel fields[][];
	
	private int numRows;
	private int numCols;
	private int ownArmyID;

	private NavalWarGUI navalWarGUI;
	
	public WarPanel(NavalWarGUI navalWarGUI, int numRows, int numCols) {
		this.navalWarGUI = navalWarGUI;
		this.numRows = numRows;
		this.numCols = numCols;

		enemiesArmies = new HashMap<Integer, ArmyPanel>();
		numEnemiesArmies = 0;
		fields = new ArmyPanel[numRows][numCols];
						
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				ArmyPanel army;
				if ((i==0) && (j==0)) {
					army = new ArmyPanel(
							this,
							CreateArmyPanel.ARMY_NUM_ROWS,
							CreateArmyPanel.ARMY_NUM_COLS,
							false);
					ownArmy = army;
					army.setClickable(false);
					ownArmy.setVisible(true);
				}
				else {
					army = new ArmyPanel(
							this,
							CreateArmyPanel.ARMY_NUM_ROWS,
							CreateArmyPanel.ARMY_NUM_COLS,
							true);
					army.setClickable(true);
					army.setVisible(true);
				}
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx = j;
				c.gridy = i;
				c.insets = new Insets(10,10,10,10);
				add(army, c);
				fields[i][j] = army;
			}
		}
	}

	public void resetPanel() {
		ownArmy.resetArmy();
		for(int i = 0; i < numRows; i++)
			for(int j = 0; j < numCols; j++) {
				fields[i][j].resetArmy();
				fields[i][j].setVisible(false);
			}
		fields[0][0].setVisible(true);
		enemiesArmies.clear();
	}

	 void addOwnArmy(int ownArmyID, List<UnitAndPlace> unitsAndPlaces) {
		 this.ownArmyID = ownArmyID;
		 ownArmy.setArmyID(ownArmyID);
		 for(UnitAndPlace uap : unitsAndPlaces) {
			 ownArmy.drawUnit(uap.getUnitName(), uap.getRow(), uap.getCol());
		 }
	 }
	 
	 public int addEnemyArmyField(int armyID, String armyName) {
		 if (numEnemiesArmies >= numCols*numRows) return IGUIModule.ERROR_NOT_MORE_SPACE_FOR_ARMIES;
		 numEnemiesArmies++;
		 int i = numEnemiesArmies / numCols;
		 int j = numEnemiesArmies % numCols;
		 ArmyPanel army = fields[i][j];
		 army.setArmyID(armyID);
		 army.setArmyName(armyName);
		 enemiesArmies.put(armyID, army);
		 this.add(army);
		 army.setVisible(true);
		 return IGUIModule.NOTIFICATION_RECEIVED_OK;
	 }
	 
	 public void drawShot(int armyID, int row, int col, boolean inTarget) {
		 if (armyID == ownArmyID) {
			 ownArmy.drawShot(row, col, inTarget);
		 }
		 else {
			 enemiesArmies.get(armyID).drawShot(row, col, inTarget); 
		 }
	 }

	public boolean isArmyInWar(int armyID) {
		return (enemiesArmies.containsKey(armyID) || armyID == ownArmyID);
	}

	public void startWar() {
		
	}

	public void turnArmy() {
		for(ArmyPanel army : enemiesArmies.values()) {
			army.setClickable(true);
		}
	}

	public void turnArmyTimeout() {
		for(ArmyPanel army : enemiesArmies.values()) {
			army.setClickable(false);
		}
	}

	public void endWar() {
	}

	public void notifyClickOnCell(int armyID, int row, int col) {
		navalWarGUI.notifyClickOnCell(armyID, row, col);
	}
	
	public int getEnemyCant() {
		return enemiesArmies.size();
	}

}
