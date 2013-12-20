package navalwar.client.gui.test;

import static org.junit.Assert.*;
import navalwar.client.gui.NavalWarGUI;
import navalwar.client.gui.WarPanel;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

public class AddEnemyWarPanelTest {
	
	WarPanel panel;
	NavalWarGUI gui;

	@Before
	public void setUp() throws Exception {
		gui = PowerMock.createMock(NavalWarGUI.class);
		panel = new WarPanel(gui, 2, 3);
		
	}

	@Test
	public void testNullValues() {
		panel.addEnemyArmyField(0, null);
		panel.addEnemyArmyField(1, null);
		panel.addEnemyArmyField(2, null);
		assertEquals(3, panel.getEnemyCant());
		
		
	}
	
	@Test
	public void testSameWarID() {
		panel.addEnemyArmyField(0, "Army1");
		panel.addEnemyArmyField(0, "Army2");
		panel.addEnemyArmyField(0, "Army3");
		assertEquals(1, panel.getEnemyCant());
	}
	
	@Test
	public void testSameWarIDNullValues() {
		panel.addEnemyArmyField(0, null);
		panel.addEnemyArmyField(0, null);
		panel.addEnemyArmyField(0, null);
		assertEquals(1, panel.getEnemyCant());
	}

}
