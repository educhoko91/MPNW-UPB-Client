package navalwar.client.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import navalwar.client.gui.IGUIModule;
import navalwar.server.gameengine.UnitAndPlace;
import navalwar.server.gameengine.info.IArmyInfo;
import navalwar.server.gameengine.info.IWarInfo;

public class ClientNetworkModule implements IClientNetworkModule {
	
	private static final String LISTMSG = "ListMsg"; 
	private static final String GAMESMSG = "GamesMsg"; 
	private static final String WARIDMSG = "WarIDMsg"; 
	private static final String ARMYIDMSG = "ArmyIDMsg"; 

	IGUIModule gui = null;
	
	private Socket socket = null;
	private DataOutputStream  outToServer = null;
	private BufferedReader inFromServer = null;
	//--------------------------------------------
	// Constructors & singleton pattern
	//--------------------------------------------
    
    private ClientNetworkModule() {
		// TODO complete this constructor
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
			outToServer.writeBytes("DISCONNECT" + '\n');
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 2;
		}
		return 1;
	}

	public int createWar(String name, String desc) {
		try {
			outToServer.writeBytes("CreateWarMsg" + '\n'); 
			outToServer.writeBytes("WarName:" + name + '\n');
			outToServer.writeBytes("WarDesc:" + desc + '\n');
			String line =  inFromServer.readLine();
			System.out.println(line);
			if (line.equals(WARIDMSG)) {
				line = inFromServer.readLine();
				int n = Integer.parseInt(line);
				System.out.println("WarID:" + n);
				return n;
			}else{
				return -1;
			}
		} catch (UnknownHostException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		}
	}

	public int startWar(int warID) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<Integer,String> getWarsList() {
		try {
			outToServer.writeBytes(LISTMSG +"\n");
			System.out.println("List Message Send!!!");
			//return null;
			Map<Integer, String> map = new HashMap<Integer, String>();
			StringTokenizer token;
			String line =  inFromServer.readLine();
			System.out.println(line);
			if (line.equals(GAMESMSG)) {
				System.out.println("In If");
				line = inFromServer.readLine();
				int n = Integer.parseInt(line);
				System.out.println(n);
				for(int i=0;i<n;i++) {
					line = inFromServer.readLine();
					System.out.println(line);
					token = new StringTokenizer(line);
					token.nextToken(":");
					int id = Integer.parseInt(token.nextToken());

					line = inFromServer.readLine();
					System.out.println(line);
					token = new StringTokenizer(line);
					token.nextToken(":");
					String name = token.nextToken();
					map.put(id, name);
				}
			}
			return map;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int regArmy(int warID, String name, List<UnitAndPlace> unit) {
		try {
			outToServer.writeBytes("JOIN" + '\n');
			outToServer.writeBytes("WarID:" + warID + '\n');
			outToServer.writeBytes("WarName:" + name + '\n');
			outToServer.writeBytes("UnitSize:" + unit.size() + '\n');
			for(UnitAndPlace u:unit){
				outToServer.writeBytes("Unit:" + u.getName() + '\n');
				outToServer.writeBytes("X:" + u.getRow() + '\n');
				outToServer.writeBytes("Y:" + u.getCol() + '\n');
			}
			String line =  inFromServer.readLine();
			System.out.println(line);
			if (line.equals(ARMYIDMSG)) {
				line = inFromServer.readLine();
				StringTokenizer token = new StringTokenizer(line);
				token.nextToken(":");
				int id = Integer.parseInt(token.nextToken());
				System.out.println("ArmyID:" + id);
				return 1;
			}
			else{
				return -1;
			}
		} catch (UnknownHostException e) {
			return 2;
		} catch (IOException e) {
			return 3;
		}
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
