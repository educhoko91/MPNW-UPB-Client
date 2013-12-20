package navalwar.client.gui.test;

import static org.junit.Assert.*;
import navalwar.client.gui.ListWarItem;
import navalwar.client.gui.ListWarsPanel;

import org.junit.Before;
import org.junit.Test;

public class ListWarPanelTest {
	
	ListWarsPanel panel;
	

	@Before
	public void setUp() throws Exception {
		panel = new ListWarsPanel();
		
	}

	@Test
	public void test() {
		ListWarItem item1 = new ListWarItem(12, "War1");
		ListWarItem item2 = new ListWarItem(13, "War2");
		ListWarItem item3 = new ListWarItem(22, "War3");
		ListWarItem item4 = new ListWarItem(23, "War4");
		panel.addWarItem(item1);
		panel.addWarItem(item2);
		panel.addWarItem(item3);
		panel.addWarItem(item4);
		panel.listWars.setSelectedIndex(-1);
		assertEquals(panel.getSelectedWarID(), -1);
		panel.listWars.setSelectedIndex(1);
		assertEquals(panel.getSelectedWarID(), 13);
		
	}

}
