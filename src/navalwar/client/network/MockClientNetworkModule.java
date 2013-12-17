package navalwar.client.network;

import java.util.List;
import java.util.Map;

import navalwar.client.gui.IGUIModule;
import navalwar.server.gameengine.UnitAndPlace;
import navalwar.server.gameengine.info.IArmyInfo;
import navalwar.server.gameengine.info.IWarInfo;

public class MockClientNetworkModule implements IClientNetworkModule {

	IGUIModule gui = null;
	
	//--------------------------------------------
	// Constructors & singleton pattern
	//--------------------------------------------
    
    private MockClientNetworkModule() {
		// TODO complete this constructor
	}

	private static MockClientNetworkModule instance = null;
	public static MockClientNetworkModule getInstance() {
		if (instance == null) instance = new MockClientNetworkModule();
		return instance;
	}


	//--------------------------------------------
	// IClientNetworkModule methods
	//--------------------------------------------

	public void bindGUIModule(IGUIModule gui) {
		this.gui = gui;
	}

	public int connect(String ip, int port) {
		return 1;
	}

	public int disconnect() {
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

	public Map<Integer, String> getWarsList() {
		// TODO Auto-generated method stub
		return null;
	}

	public int regArmy(int warID, String name, List<UnitAndPlace> unitsAndPlaces) {
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


	@Override
	public int startWar(int warID, int armyID) {
		// TODO Auto-generated method stub
		return 0;
	}


}
