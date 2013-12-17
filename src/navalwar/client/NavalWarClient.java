package navalwar.client;

import navalwar.client.gui.IGUIModule;
import navalwar.client.gui.NavalWarGUI;
import navalwar.client.network.ClientNetworkModule;
import navalwar.client.network.IClientNetworkModule;

public class NavalWarClient {
	
	public static void main(String[] args) {
		IGUIModule gui = NavalWarGUI.getInstance();
		IClientNetworkModule net = ClientNetworkModule.getInstance();
		
		gui.bindNetModule(net);
		net.bindGUIModule(gui);
		
	}
}
