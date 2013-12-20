package navalwar.client.gui.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

import navalwar.client.gui.NavalWarGUI;
import navalwar.client.network.ClientNetworkModule;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ClientNetworkModule.class})
public class ClientNetworkModuleTest {
	
	ClientNetworkModule net;
	DataOutputStream  outToServer;
	BufferedReader inFromServer;
	NavalWarGUI gui;

	@Before
	public void setUp() throws Exception {
		net = ClientNetworkModule.getInstance();
		outToServer = PowerMock.createMock(DataOutputStream.class);
		inFromServer = PowerMock.createMock(BufferedReader.class);
		gui = PowerMock.createMock(NavalWarGUI.class);
		net.inFromServer = inFromServer;
		net.outToServer = outToServer;
		net.bindGUIModule(gui);
		
	}

	@Test
	public void testCreateWar1() throws IOException {
		String name = "War1";
		String desc = "DescWar1";
		outToServer.writeBytes("CreateWarMsg" + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarName:" + name + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarDesc:" + desc + '\n');
		PowerMock.expectLastCall();
		EasyMock.expect(inFromServer.readLine()).andReturn(net.WARIDMSG);
		EasyMock.expect(inFromServer.readLine()).andReturn("102");
		EasyMock.replay(inFromServer);
		assertEquals(net.createWar("HOla", "Chau"), 102);
		
	}
	
	@Test
	public void testCreateWar2() throws IOException {
		String name = "War1";
		String desc = "DescWar1";
		outToServer.writeBytes("CreateWarMsg" + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarName:" + name + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarDesc:" + desc + '\n');
		PowerMock.expectLastCall();
		EasyMock.expect(inFromServer.readLine()).andReturn("HOLAA");
		EasyMock.replay(inFromServer);
		assertEquals(net.createWar("HOla", "Chau"), -1);
		
	}
	
	@Test
	public void testCreateWar3() throws IOException {
		String name = "War1";
		String desc = "DescWar1";
		
		outToServer.writeBytes("CreateWarMsg" + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarName:" + name + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarDesc:" + desc + '\n');
		PowerMock.expectLastCall();
		EasyMock.expect(inFromServer.readLine()).andThrow(new IOException());
		EasyMock.replay(inFromServer);
		assertEquals(net.createWar("HOla", "Chau"), -1);
		
	}
	
	@Test
	public void testStartWar2() throws IOException {
		int warID = 102;
		int armyID = 103;
		
		outToServer.writeBytes("StartMsg" + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarID:" + warID + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("armyID:" + armyID + '\n');
		PowerMock.expectLastCall();
		EasyMock.expect(inFromServer.readLine()).andThrow(new IOException());
		EasyMock.replay(inFromServer);
		assertEquals(net.startWar(warID, armyID), -1);
		
	}
	
	@Test
	public void testStartWar1() throws IOException {
		int warID = 102;
		int armyID = 103;
		
		outToServer.writeBytes("StartMsg" + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("WarID:" + warID + '\n');
		PowerMock.expectLastCall();
		outToServer.writeBytes("armyID:" + armyID + '\n');
		PowerMock.expectLastCall();
		EasyMock.expect(inFromServer.readLine()).andReturn("105");
		EasyMock.replay(inFromServer);
		assertEquals(net.startWar(warID, armyID), 1);
		
	}

}
