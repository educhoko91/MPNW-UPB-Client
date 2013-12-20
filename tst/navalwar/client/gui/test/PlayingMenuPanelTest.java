package navalwar.client.gui.test;

import static org.junit.Assert.*;
import navalwar.client.gui.NavalWarGUI;
import navalwar.client.gui.NavalWarGUI.StateGUI;
import navalwar.client.gui.WarPanel;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class PlayingMenuPanelTest {
	
	NavalWarGUI gui;
	WarPanel warPanel;

	@Before
	public void setUp() throws Exception {
		gui = NavalWarGUI.getInstance();
		warPanel = EasyMock.createMock(WarPanel.class);
		gui.warPanel = warPanel;
	}

	@Test
	public void testStartWar() {
		gui.state = StateGUI.STATE_WAIT_FOR_START_WAR;
		EasyMock.expect(warPanel.isArmyInWar(10)).andReturn(true);
		warPanel.startWar();
		EasyMock.expectLastCall();
		assertEquals("playing", gui.lbStatus.getText());
	}
	
}
