package navalwar.client.gui.test;

import java.util.HashMap;

import navalwar.client.gui.ListWarsPanel;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class loadListListWarsPanel {
	ListWarsPanel panel;
	HashMap<Integer, String> map;
	

	@Before
	public void setUp() throws Exception {
		panel = new ListWarsPanel();
		map = new HashMap<Integer, String>();
		map.put(580, "War1");
		map.put(280, "War2");
		map.put(380, "War3");
		map.put(480, "War4");
	}

	@Test
	public void test() {
		panel.loadList(map);
		int index280 = panel.listWarIds.indexOf(280);
		panel.listWars.setSelectedIndex(index280);
		assertEquals(panel.getSelectedWarID(), 280);
	}

}
