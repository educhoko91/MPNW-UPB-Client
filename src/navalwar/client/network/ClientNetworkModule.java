package navalwar.client.network;

import java.util.List;

import navalwar.client.gui.IGUIModule;
import navalwar.client.gui.NavalWarGUI;
import navalwar.server.gameengine.info.IWarInfo;
import navalwar.server.gameengine.info.IArmyInfo;

///////////////////////////////////////////////////////////////////////////

public class ClientNetworkModule implements IClientNetworkModule {

	IGUIModule gui = null;
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	public int disconnect() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
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
