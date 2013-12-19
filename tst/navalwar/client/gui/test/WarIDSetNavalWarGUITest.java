package navalwar.client.gui.test;

import static org.junit.Assert.*;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import navalwar.client.gui.CreateWarPanel;
import navalwar.client.gui.NavalWarGUI;
import navalwar.client.network.IClientNetworkModule;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class WarIDSetNavalWarGUITest {
	
	IClientNetworkModule net;
	NavalWarGUI gui;
	CreateWarPanel warPanel;

	@Before
	public void setUp() throws Exception {
		net = EasyMock.createMock(IClientNetworkModule.class);
		gui = NavalWarGUI.getInstance();
		gui.bindNetModule(net);
		warPanel = new CreateWarPanel();
	}

	@Test
	public void testCreateWarAction() {
		JTextArea textField = (JTextArea) ((JPanel)warPanel.getComponent(1)).getComponent(2);
		EasyMock.expect(net.createWar("Test War", "")).andReturn(200);
		EasyMock.replay(net);
		textField.setText("Test War");
		warPanel.add(textField, 2);
		gui.setCreateWarPanel(warPanel);
		gui.doAction(200);
		assertEquals(gui.getWarID(), 200);
		
	}

}
