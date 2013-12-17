package navalwar.client.network;

import java.util.List;
import java.util.Map;

import navalwar.client.gui.IGUIModule;
import navalwar.server.gameengine.UnitAndPlace;
import navalwar.server.gameengine.info.IArmyInfo;
import navalwar.server.gameengine.info.IWarInfo;

/**
 * This interface must be implemented by the network module of the client.
 * It provides methods that enable the GUI module to communicate with the
 * server passing through this module.
 * 
 * @author Alfredo Villalba
 */
public interface IClientNetworkModule {
	
	//-------------------------------
	// Return codes
	//-------------------------------

	public static final int ERROR_WHEN_CREATING_WAR 		= -100;
	public static final int WAR_STARTED_SUCCESSFULLY 		= 100;
	public static final int ERROR_WHEN_STARTING_WAR 		= 101;
	public static final int ARMY_REGISTERED_SUCCESSFULLY	= 102;
	public static final int ERROR_WHEN_REGISTERING_ARMY		= 103;
	public static final int SHOT_IN_TARGET					= 104;
	public static final int SHOT_NOT_IN_TARGET				= 105;
	public static final int ERROR_WHEN_SHOTING				= 106;

	
	//-------------------------------
	// Interface methods
	//-------------------------------

	/**
	 * This method is called after  creating a network module.
	 * It binds the network module with a GUI module so that the network module
	 * can communicate with the GUI module (the GUI module must proceed
	 * in a similar way).
	 * 
	 * @param gui the GUI module
	 */
	void bindGUIModule(IGUIModule gui);

	
    /**
     * Connects to the server having the IP address and
     * port number entered by the user. This method will
     * be called when the user clicks on the "Connect"
     * button.
     * @param ip IP address as a string
     * @param port port number
     * @return 1=sucess, 0=failed
     */
    int connect(String ip, int port);


    /**
     * Disconnects from the server to which the client
     * is currently connected.  This method will
     * be called when the user clicks on the "Disconnect"
     * button.
     * @return 1=succes, 0=failded
     */
    
    int disconnect();
	/**
	 * This method is called by the GUI module when the user wants to create
	 * a war.
	 * @param name name of the war
	 * @param desc description of the war
	 * @return warID or ERROR_WHEN_CREATING_WAR
	 */
	public int createWar(String name, String desc);
	
	/**
	 * This method is called by the GUI module when the user wants to start
	 * a war that the user created (owner).
	 * @param warID the ID of the war
	 * @return WAR_STARTED_SUCCESSFULLY or ERROR_WHEN_STARTING_WAR
	 */
	public int startWar(int warID);
	
	/**
	 * This method is called by the GUI module when the user wants to get
	 * the list of existing wars.
	 * @return list of wars
	 */
	public Map<Integer, String> getWarsList();
	
	/**
	 * This method is called by the GUI module when the user wants to register
	 * an army into a war. 
	 * @param warID the ID of the war
	 * @param name the name of the army
	 * @param units the list of names of the units (Tank, Soldier, Ship, Plane)
	 * @param rows the list of rows coordinates
	 * @param cols the list of cols coordinates
	 * @return ARMY_REGISTERED_SUCCESSFULLY or ERROR_WHEN_REGISTERING_ARMY
	 */
	public int regArmy(int warID, String name, List<UnitAndPlace> unit);
	
	/**
	 * This method is called b the GUI module when the user wants to shot into
	 * an enemy's field.
	 * @param warID the ID of the war
	 * @param attackArmyID the ID of the user army
	 * @param targetArmyID the ID of the target army
	 * @param row the row coordinate of the shot
	 * @param col the col coordinate of the shot
	 * @return SHOT_IN_TARGET or SHOT_NOT_IN_TARGET or ERROR_WHEN_SHOTING
	 */
	public int shot(int warID, int attackArmyID, int targetArmyID, int row, int col);
	
	/**
	 * This method is called by the GUI module when the user wants to get
	 * information about a war. 
	 * @param warID the ID of the war
	 * @return null (when war does not exist or error) or object of type IWarInfo 
	 */
	public IWarInfo getWarInfo(int warID);
	

	/**
	 * This method is called by the GUI module when the user wants to get
	 * information about an army.
	 * @param warID the ID of the war in which the army is fighting
	 * @param armyID the ID of the army
	 * @return null (when army does not exist or error) or object of type IArmyInfo
	 */
	public IArmyInfo getArmyInfo(int warID, int armyID);


	 

}
