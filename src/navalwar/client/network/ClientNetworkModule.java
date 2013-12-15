package navalwar.client.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import navalwar.client.gui.IGUIModule;
import navalwar.client.gui.NavalWarGUI;
import navalwar.server.gameengine.War;
import navalwar.server.gameengine.info.IWarInfo;
import navalwar.server.gameengine.info.IArmyInfo;
import navalwar.server.gameengine.info.WarInfo;



public class ClientNetworkModule implements IClientNetworkModule {
	
	private static final String LISTMSG = "ListMsg"; 
	private static final String GAMESMSG = "GamesMsg"; 

	IGUIModule gui = null;
	
	private Socket socket = null;
	private DataOutputStream  outToServer = null;
	private BufferedReader inFromServer = null;
	//--------------------------------------------
	// Constructors & singleton pattern
	//--------------------------------------------
    
	
	
    private ClientNetworkModule() {
		
	}

	private static ClientNetworkModule instance = null;
	public static ClientNetworkModule getInstance() {
		if (instance == null) instance = new ClientNetworkModule();
		return instance;
	}


	//--------------------------------------------
	// IClientNetworkModule methods
	//--------------------------------------------

	public void bindGUIModule(IGUIModule gui) {
		this.gui = gui;
	}

	public int connect(String ip, int port) {
		
		try {
			socket = new Socket(ip,port);
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return 1;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public int disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 2;
		}
		return 1;
	}

	public int createWar(String name, String desc) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int startWar(int warID) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<IWarInfo> getWarsList() {
		StringTokenizer  tokenizer;
		try {
			outToServer.writeBytes(LISTMSG +"\n");
			System.out.println("List Message Send!!!");
			return null;
			/*String line =  inFromServer.readLine();
			if (line.equals(GAMESMSG)) {
				List<IWarInfo> listWars = new ArrayList<IWarInfo>();
				line =inFromServer.readLine();
				while (line != null) {
					tokenizer = new StringTokenizer(line);
					int id = Integer.parseInt(tokenizer.nextToken(":"));
					
				}
			
			}*/
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int regArmy(int warID, String name, String[] units, int[] rows,
			int[] cols) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int shot(int warID, int attackArmyID, int targetArmyID, int row,
			int col) {
		// TODO Auto-generated method stub
		return 0;
	}

	public IWarInfo getWarInfo(int warID) {
		// TODO Auto-generated method stub
		return null;
	}

	public IArmyInfo getArmyInfo(int warID, int armyID) {
		// TODO Auto-generated method stub
		return null;
	}


}
