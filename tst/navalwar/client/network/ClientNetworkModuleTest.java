package navalwar.client.network;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

public class ClientNetworkModuleTest {
	
	private ClientNetworkModule client;
	private Socket socket = null;
	private DataOutputStream  outToServer = null;
	private BufferedReader inFromServer = null;
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void test() throws IOException {
		outToServer.writeBytes("CreateWarMsg" + '\n'); 
		outToServer.writeBytes("WarName:" + "warChava" + '\n');
		outToServer.writeBytes("WarDesc:" + "descChava" + '\n');
	}

}
