package navalwar.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import navalwar.client.gui.IGUIModule;
import navalwar.client.gui.IGUIModule.ShotImpact;
import navalwar.client.gui.NavalWarGUI;

public class ClientListenerTheard implements Runnable {
	
	private BufferedReader inFromServer;
	private String line;
	private int armyID;
	private IGUIModule gui;
	private StringTokenizer token;
	private String name;

	@Override
	public void run() {
		System.out.println("New Thread Created");
		while(true) {
			try {
				line = inFromServer.readLine();
				System.out.println(line);
				switch (line) {
				case ClientNetworkModule.SHOOTMSG:
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					armyID = Integer.parseInt(token.nextToken());
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					int row = Integer.parseInt(token.nextToken());
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					int col = Integer.parseInt(token.nextToken());
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					ShotImpact code = ShotImpact.valueOf(token.nextToken());
					gui.shotImpact(armyID, row, col, code);
					break;
					
				case ClientNetworkModule.NEXTTURNMSG:
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					armyID = Integer.parseInt(token.nextToken());
					System.out.println("NextTurn:" + armyID);
					if (armyID== gui.getOwnArmyID()) {
						gui.turnArmy();
					}
					break;
					
				case ClientNetworkModule.NEWENEMYMSG:
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					armyID = Integer.parseInt(token.nextToken());
					line = inFromServer.readLine();
					token = new StringTokenizer(line);
					token.nextToken(":");
					name = token.nextToken();
					gui.notifyArmyInWar(armyID, name);
					break;
					
				case ClientNetworkModule.ENEMYLISTMSG:
					int cnd = Integer.parseInt(inFromServer.readLine());
					for (int i=0; i<cnd; i++) {
						line = inFromServer.readLine();
						token = new StringTokenizer(line);
						token.nextToken(":");
						armyID = Integer.parseInt(token.nextToken());
						line = inFromServer.readLine();
						token = new StringTokenizer(line);
						token.nextToken(":");
						name = token.nextToken();
						gui.notifyArmyInWar(armyID, name);
					}
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void bindInFromServer (BufferedReader inFromServer) {
		this.inFromServer = inFromServer;
		
	}
	
	public void bindNavalWarGUI (IGUIModule gui) {
		this.gui = gui;
		
	}

}
