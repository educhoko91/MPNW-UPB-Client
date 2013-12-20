package navalwar.client.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import navalwar.client.gui.IGUIModule;
import navalwar.client.gui.ListWarItem;
import navalwar.server.gameengine.UnitAndPlace;
import navalwar.server.gameengine.info.IArmyInfo;
import navalwar.server.gameengine.info.IWarInfo;

public class ClientNetworkModule implements IClientNetworkModule {
	
	private ClientListenerTheard listener;
	
	public static final String LISTMSG = "ListMsg"; 
	public static final String GAMESMSG = "GamesMsg"; 
	public static final String WARIDMSG = "WarIDMsg"; 
	public static final String ARMYIDMSG = "ArmyIDMsg";
	public static final String NEXTTURNMSG = "NextTurnMsg";
	public static final String SHOOTMSG = "ShootMsg";
	public static final String SURRENDERMSG = "SurrenderMsg";
	public static final String NEWENEMYMSG = "NewEnemyMsg";
	public static final String ENEMYLISTMSG = "EnemyListMsg";
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

	public int startWar(int warID, int armyID) {
		try {
			outToServer.writeBytes("StartMsg" + '\n'); 
			outToServer.writeBytes("WarID:" + warID + '\n');
			outToServer.writeBytes("armyID:" + armyID + '\n');
			String line =  inFromServer.readLine();
			System.out.println(line);
			listener = new ClientListenerTheard();
			listener.bindInFromServer(inFromServer);
			Thread th = new Thread(listener);
			th.start();
			return 1;  
		} catch (UnknownHostException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		}
	}

	public List<ListWarItem> getWarsList() {
		try {
			outToServer.writeBytes(LISTMSG +"\n");
			System.out.println("List Message Send!!!");
			//return null;
			List<ListWarItem> list = new ArrayList<>();
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
					ListWarItem item = new ListWarItem(id, name);
					list.add(item);
				}
			}
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int regArmy(int warID, String name, List<UnitAndPlace> unit) {
		int armyID=-103;
		try {
			outToServer.writeBytes("JOIN" + '\n');
			outToServer.writeBytes("WarID:" + warID + '\n');
			outToServer.writeBytes("WarName:" + name + '\n');
			outToServer.writeBytes("UnitSize:" + unit.size() + '\n');
			for(UnitAndPlace u:unit){
				outToServer.writeBytes("Unit:" + u.getUnitName() + '\n');
				outToServer.writeBytes("X:" + u.getRow() + '\n');
				outToServer.writeBytes("Y:" + u.getCol() + '\n');
			}
			String line =  inFromServer.readLine();
			System.out.println(line);
			if (line.equals(ARMYIDMSG)) {
				line = inFromServer.readLine();
				StringTokenizer token = new StringTokenizer(line);
				token.nextToken(":");
				armyID = Integer.parseInt(token.nextToken());
				System.out.println("ArmyID:" + armyID);
			}
			return armyID;
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


	@Override
	public int surrender(int warID, int armyID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
