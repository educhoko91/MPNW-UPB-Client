package navalwar.client.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.JarURLConnection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class CreateArmyPanel extends JPanel {

		private static final int NUM_DISPLAYED_UNITS_PER_ROW = 3;
		private static final int NUM_DISPLAYED_UNITS_PER_COL = 4;
		private static final int SPACE_FOR_UNITS = 90;
		private static final int OFFSET_UNITS_X = 240;
		private static final int OFFSET_UNITS_Y = 20;
		
	
		private JLayeredPane layeredPane;
		private ArmyCreateFieldPanel field;
		private UnitPanel[] units;
		private int numUnits;
		
		public CreateArmyPanel(int numTypeUnits, int[] numUnitsPerType, int[][][] shapeUnits) {

			 //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			 setLayout(new FlowLayout());
			 
			 layeredPane = new JLayeredPane();
		     layeredPane.setPreferredSize(new Dimension(500, 400));
			 
		     field = new ArmyCreateFieldPanel(10,10);
		     field.setBounds(0, 0, 200, 200);
		     layeredPane.add(field, 0, 0);
		     
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
		    		 UnitPanel unit = new UnitPanel(shapeRows, shapeCols, shape);
		    		 int unitX = OFFSET_UNITS_X + (k % NUM_DISPLAYED_UNITS_PER_ROW) * SPACE_FOR_UNITS;
		    		 int unitY = OFFSET_UNITS_Y + (k / NUM_DISPLAYED_UNITS_PER_ROW) * SPACE_FOR_UNITS;
		    		 unit.setBounds(unitX, unitY, unit.getSizeX(), unit.getSizeY());
		    		 unit.setHome(unitX, unitY);
		    		 layeredPane.add(unit, k+1, 0);
		    		 units[k] = unit;
	    		 }
		     }

			 add(Box.createRigidArea(new Dimension(0, 10)));
		     add(layeredPane);
		}
}
