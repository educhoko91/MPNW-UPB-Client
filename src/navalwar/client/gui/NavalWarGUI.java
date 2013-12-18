package navalwar.client.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.*;

import navalwar.client.network.IClientNetworkModule;
import navalwar.server.gameengine.GameEngineModule;
import navalwar.server.gameengine.War;
import navalwar.server.gameengine.info.IWarInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the graphical user interface for
 * the MemoryGame. The classes which want to use this graphical
 * interface have to implement the MainGUIListener interface
 * in order to capture the events generated by this class.
 * This class also offers public methods to get some values
 * entered by the user such as the IP address server, the port
 * number, the name of the player, the size and the level of
 * the game.
 * 
 * @author alfredo.villalba@cui.unige.ch
 */
public class NavalWarGUI extends JFrame implements IGUIModule {

	/**
	 * Listener of this graphical interface
	 */

	private IClientNetworkModule net;

	/**
	 * Graphical components
	 */

	private JTextField ip;
	private JTextField port;
	private JButton btConnect;
	private JButton btDisconnect;


	//-----------------------------------
	// Panels
	//-----------------------------------

	private JPanel gamePanel;
	private JPanel cmdsPanel;
	private Component welcomePanel;
	private CreateWarPanel createWarPanel;
	private ListWarsPanel listWarsPanel;
	private CreateArmyPanel createArmyPanel;
	private WarPanel warPanel;


	//-----------------------------------
	// MAIN MENU
	//-----------------------------------

	private JPanel mainMenuPanel;
	private JButton btCreateWarMenu;
	private JButton btListWarsMenu;
	private JButton btExit;



	//-----------------------------------
	// CREATE WAR MENU
	//-----------------------------------

	private JPanel createWarMenuPanel;
	private JButton btCreateWar;
	private JButton btToMainMenuFromCreateWarMenu;



	//-----------------------------------
	// LIST WARS MENU
	//-----------------------------------

	private JPanel listWarsMenuPanel;
	private JButton btJoinWar;
	private JButton btToMainMenuFromListWarsMenu;


	//-----------------------------------
	// REGISTER ARMY MENU
	//-----------------------------------

	private JPanel registerArmyMenuPanel;
	private JLabel warName;
	private JButton btRegArmy;
	private JButton btToMainMenuFroRegArmyMenu;


	//-----------------------------------
	// PLAYING MENU
	//-----------------------------------

	private JPanel playingMenuPanel;
	private JLabel lbWarNameInPlayingMenu;
	private JLabel lbStatus;
	private JLabel lbTotalNumShots;
	private JLabel lbMyNumShots;
	private JLabel lbNumShotsReceived;
	private JLabel lbMyNumShotsInTarget;
	private JButton btStartWar;
	private JButton btSurrender;



	/**
	 * State attribuites
	 */

	private enum StateGUI {
		STATE_WELCOME,
		STATE_CREATE_WAR,
		STATE_REGISTER_ARMY,
		STATE_LIST_WARS,
		STATE_WAIT_FOR_START_WAR,
		STATE_IN_WAR_AND_WAIT_FOR_TURN,
		STATE_IN_WAR_AND_IN_TURN,
		STATE_WAR_ENDED
	}
	private StateGUI state; 
	private int warID;
	private int ownArmyID;



	//------------------------------
	// shapes
	//------------------------------

	public static Map<String, int[][]> shapes = new HashMap<String, int[][]>();

	static {
		int[][][] s = 
			{
				{
					{1}
				}, // shape 1

				{
					{1,1,1}
				}, // shape 2

				{
					{0,1,0},
					{1,1,1}
				}, // shape 3

				{
					{0,1,0},
					{1,1,1},
					{0,1,0}
				}, // shape 4
			};

		shapes.put("Soldier", s[0]);
		shapes.put("Tank",s[1]);
		shapes.put("Ship", s[2]);
		shapes.put("Plane", s[3]);
	};

	public static ArrayList<Object> schema = new ArrayList<Object>();

	static {
		schema.add("Soldier");
		schema.add(2);
		schema.add("Tank");
		schema.add(2);
		schema.add("Ship");
		schema.add(1);
		schema.add("Plane");
		schema.add(1);
	}



	/**
	 * Actions identifiers
	 */

	private final static int CONNECT = 3;
	private final static int DISCONNECT = 4;

	protected static final int ACTION_CREATE_WAR_MENU_SELECTED = 100;
	protected static final int ACTION_LIST_WARS_MENU_SELECTED = 101;
	protected static final int ACTION_EXIT_SELECTED = 102;

	protected static final int ACTION_CREATE_WAR_SELECTED = 200;
	protected static final int ACTION_TO_MAIN_MENU_FROM_CREATE_WAR_MENU = 201;

	protected static final int ACTION_REGISTER_ARMY_SELECTED = 300;
	protected static final int ACTION_TO_MAIN_MENU_FROM_REGISTER_ARMY_MENU = 301;

	protected static final int ACTION_SURRENDER = 400;
	protected static final int ACTION_START_WAR_SELECTED = 401;

	protected static final int ACTION_JOIN_WAR_SELECTED = 500;
	protected static final int ACTION_TO_MAIN_MENU_FROM_LIST_WARS_MENU = 501;


	//--------------------------------------------
	// Constructors & singleton pattern
	//--------------------------------------------

	/**
	 * Constructs a graphical user interface that will be binded
	 * to a class implementing the MinesGuiListernet interface.
	 * This binded class will listen to the events of the gui and
	 * process them and then returning results such as the number
	 * of image, whether the game is over or not, etc.
	 * 
	 * @param game binded class implementing MainGUIListener interface
	 */
	private NavalWarGUI() {
		super("Multi-user naval war");
		buildMainPanel();
		setResizable(false);
		setVisible(true);
	}

	private static NavalWarGUI instance = null;
	public static NavalWarGUI getInstance() {
		if (instance == null) instance = new NavalWarGUI();
		return instance;
	}

	//--------------------------------------------
	// Methods
	//--------------------------------------------

	/**
	 * Gets the IP address of the server entered by the user.
	 * This address must be used as the address to which the
	 * client will try connect.
	 * @return IP address as a String
	 */
	public String getServerIP() {
		return ip.getText();
	}

	/**
	 * Gets the port number of the server entered by the user.
	 * This port must be used as the port to which the
	 * client will try connect.
	 * @return port number
	 */
	public int getServerPort() {
		return Integer.parseInt(port.getText());
	}


	/**
	 * Builds the main panel of the interface graphique.
	 * This panel is composed of the game panel and
	 * the control panel.
	 */
	private void buildMainPanel() {

		// Build game panel

		gamePanel = new JPanel();
		gamePanel.setBorder(BorderFactory.createTitledBorder(""));
		gamePanel.setLayout(new CardLayout());
		//gamePanel.add(Box.createVerticalGlue());
		//gamePanel.add(fieldsPanel);
		//gamePanel.add(Box.createVerticalGlue());




		welcomePanel = new WelcomePanel();
		gamePanel.add(welcomePanel, "welcomePanel");

		createWarPanel = new CreateWarPanel();
		gamePanel.add(createWarPanel, "createWarPanel");

		listWarsPanel = new ListWarsPanel();
		gamePanel.add(listWarsPanel, "listWarsPanel");
		listWarsPanel.updateTest();

		createArmyPanel = new CreateArmyPanel(schema, shapes);
		gamePanel.add(createArmyPanel, "createArmyPanel");

		warPanel = new WarPanel(this, 2, 3);
		gamePanel.add(warPanel, "warPanel");



		// Build commands panel

		cmdsPanel = new JPanel();
		cmdsPanel.setBorder(BorderFactory.createTitledBorder("Commands"));
		cmdsPanel.setLayout(new CardLayout());
		populateMenuPanels();
		cmdsPanel.add(mainMenuPanel, "mainMenuPanel");
		cmdsPanel.add(createWarMenuPanel, "createWarMenuPanel");
		cmdsPanel.add(listWarsMenuPanel, "listWarsMenuPanel");
		cmdsPanel.add(registerArmyMenuPanel, "registerArmyMenuPanel");
		cmdsPanel.add(playingMenuPanel, "playingMenuPanel");


		// Build server panel

		JPanel serverPanel = new JPanel();
		serverPanel.setBorder(BorderFactory.createTitledBorder("Server"));
		serverPanel.setLayout(new GridLayout(3,2));
		serverPanel.add(new JLabel("IP: "));
		ip = new JTextField("127.0.0.1");
		ip.setHorizontalAlignment(JTextField.RIGHT);
		serverPanel.add(ip);
		serverPanel.add(new JLabel("Port: "));
		port = new JTextField("7777");
		port.setHorizontalAlignment(JTextField.RIGHT);
		serverPanel.add(port);
		btConnect = new JButton("Connect");
		btDisconnect = new JButton("Disconnect");
		serverPanel.add(btConnect);
		serverPanel.add(btDisconnect);
		btConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				doAction(CONNECT);
			}
		});
		btDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				doAction(DISCONNECT);
			}
		});


		// Build logo panel

		JPanel logoPanel = new JPanel();
		JLabel logo = new JLabel(new ImageIcon("images/logo100.jpg"));
		logo.setBorder(BorderFactory.createTitledBorder(""));
		logoPanel.add(logo);

		// Build control panel



		JPanel ctrlPanel = new JPanel();
		ctrlPanel.setLayout(new BoxLayout(ctrlPanel,BoxLayout.Y_AXIS));
		ctrlPanel.add(cmdsPanel);
		//ctrlPanel.add(scorePanel);
		//ctrlPanel.add(cmdsPanel);
		ctrlPanel.add(serverPanel);
		//ctrlPanel.add(optsPanel);
		//ctrlPanel.add(logoPanel);
		ctrlPanel.add(Box.createVerticalGlue());
		ctrlPanel.add(Box.createRigidArea(new Dimension(10,60)));

		// Initial state of components

		state = StateGUI.STATE_WELCOME;
		btConnect.setEnabled(true);
		btDisconnect.setEnabled(false);
		btCreateWarMenu.setEnabled(false);
		btListWarsMenu.setEnabled(false);
		btExit.setEnabled(true);

		// Build main panel

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
		this.getContentPane().add(mainPanel);
		mainPanel.add(gamePanel);
		mainPanel.add(ctrlPanel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}


	private void populateMenuPanels() {

		// Main menu panel

		mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(new GridLayout(3,1));
		btCreateWarMenu = new JButton("Create war");
		btListWarsMenu = new JButton("List was");
		btExit = new JButton("Exit");
		mainMenuPanel.add(btCreateWarMenu);
		mainMenuPanel.add(btListWarsMenu);
		mainMenuPanel.add(btExit);

		btCreateWarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_CREATE_WAR_MENU_SELECTED);
			}
		});

		btListWarsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_LIST_WARS_MENU_SELECTED);
			}
		});

		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_EXIT_SELECTED);
			}
		});


		// Create war menu panel

		createWarMenuPanel = new JPanel();
		createWarMenuPanel.setLayout(new GridLayout(2,1));
		btCreateWar = new JButton("Create war");
		btToMainMenuFromCreateWarMenu = new JButton("Cancel");
		createWarMenuPanel.add(btCreateWar);
		createWarMenuPanel.add(btToMainMenuFromCreateWarMenu);

		btCreateWar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_CREATE_WAR_SELECTED);
			}
		});

		btToMainMenuFromCreateWarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_TO_MAIN_MENU_FROM_CREATE_WAR_MENU);
			}
		});


		// List wars menu panel

		listWarsMenuPanel = new JPanel();
		listWarsMenuPanel.setLayout(new GridLayout(2,1));


		btJoinWar = new JButton("Join war");
		btToMainMenuFromListWarsMenu = new JButton("Main menu");
		listWarsMenuPanel.add(btJoinWar);
		listWarsMenuPanel.add(btToMainMenuFromListWarsMenu);

		btJoinWar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_JOIN_WAR_SELECTED);
			}
		});

		btToMainMenuFromListWarsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_TO_MAIN_MENU_FROM_LIST_WARS_MENU);
			}
		});


		// Register army menu panel

		registerArmyMenuPanel = new JPanel();
		registerArmyMenuPanel.setLayout(new GridLayout(3,2));     
		registerArmyMenuPanel.add(new JLabel("War name:"));
		registerArmyMenuPanel.add(new JTextField("WAR NAME"));
		btRegArmy = new JButton("Register army");
		registerArmyMenuPanel.add(btRegArmy);
		registerArmyMenuPanel.add(Box.createGlue());
		btToMainMenuFroRegArmyMenu = new JButton("Cancel");
		registerArmyMenuPanel.add(btToMainMenuFroRegArmyMenu);
		registerArmyMenuPanel.add(Box.createGlue());

		btRegArmy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_REGISTER_ARMY_SELECTED);
			}
		});

		btToMainMenuFroRegArmyMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_TO_MAIN_MENU_FROM_REGISTER_ARMY_MENU);
			}
		});

		// Playing menu panel

		playingMenuPanel = new JPanel();
		playingMenuPanel.setLayout(new GridLayout(7,2));
		playingMenuPanel.add(new JLabel("War name:"));
		playingMenuPanel.add(new JLabel("WAR NAME"));
		playingMenuPanel.add(new JLabel("Total shots:"));
		lbTotalNumShots = new JLabel("0");
		playingMenuPanel.add(lbTotalNumShots);
		playingMenuPanel.add(new JLabel("My shots:"));
		lbMyNumShots = new JLabel("0");
		playingMenuPanel.add(lbMyNumShots);
		playingMenuPanel.add(new JLabel("My shots in target:"));
		lbMyNumShotsInTarget = new JLabel("0");
		playingMenuPanel.add(lbMyNumShotsInTarget);
		playingMenuPanel.add(new JLabel("Attacked:"));
		lbNumShotsReceived = new JLabel("0");
		playingMenuPanel.add(lbNumShotsReceived);
		playingMenuPanel.add(new JLabel("Status:"));
		lbStatus = new JLabel("waiting");
		playingMenuPanel.add(lbStatus);
		btStartWar = new JButton("Start War");
		playingMenuPanel.add(btStartWar);
		btSurrender = new JButton("Surrender");
		playingMenuPanel.add(btSurrender);
		//playingMenuPanel.add(Box.createGlue());

		btStartWar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_START_WAR_SELECTED);
			}
		});

		btSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAction(ACTION_SURRENDER);
			}
		});

	}

	private void initShapes() {




	}



	private void showPanel(String panelName) {
		CardLayout cards = (CardLayout) gamePanel.getLayout();
		cards.show(gamePanel, panelName);
	}


	private void showMenu(String menuName) {
		CardLayout cards = (CardLayout) cmdsPanel.getLayout();
		cards.show(cmdsPanel, menuName);
	}



	/**
	 * This method shows a "win message". It should be called
	 * by the class implementing the MainGUIListener when
	 * the user win the game. This method should be called just
	 * at the end of the game.
	 **/
	public void showWin() {
		JOptionPane.showMessageDialog(
				this, "You've found all the pairs !!!", "You win !!!",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon("images/happy.png"));
		//btStop.setEnabled(false);
		//btStart.setEnabled(true);
		//score.setEnabled(false);
		//moves.setEnabled(false);
		//name.setEnabled(true);
		//size.setEnabled(true);
		//level.setEnabled(true);
	}


	/**
	 * This method shows a "lost message". It should be called
	 * by the class implementing the MainGUIListener when
	 * the user loss the game. This method should be called just
	 * at the end of the game.
	 **/
	public void showLost() {
		JOptionPane.showMessageDialog(
				this, "You don't have any more moves !!!", "You lost !!!",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon("images/sad.png"));
		//btStop.setEnabled(false);
		//btStart.setEnabled(true);
		//score.setEnabled(false);
		//moves.setEnabled(false);
		//name.setEnabled(true);
		//size.setEnabled(true);
		//level.setEnabled(true);
	}


	/**
	 * This method shows an "alert message". It should be called
	 * by the class implementing the MainGUIListener when
	 * the user loss the game. This method should be called just
	 * at the end of the game.
	 **/
	public void showAlert(String msg) {
		JOptionPane.showMessageDialog(
				this, msg, "title?",
				JOptionPane.WARNING_MESSAGE,
				new ImageIcon("images/sad.png"));
		//btStop.setEnabled(false);
		//btStart.setEnabled(true);
		//score.setEnabled(false);
		//moves.setEnabled(false);
		//name.setEnabled(true);
		//size.setEnabled(true);
		//level.setEnabled(true);
	}


	/**
	 * This method is called when the user clicked on an action
	 * such as Start, Stop, Connect, etc. These actions will
	 * be processed by calling the respective method of the
	 * class implementing the MainGUIListener interface from which
	 * the feedback will be used to update the gui.
	 * 
	 * @param action
	 */
	public void doAction(int action) {
		int res;
		switch (action) {

		case ACTION_CREATE_WAR_MENU_SELECTED:
			createWarPanel.resetPanel();
			showPanel("createWarPanel");
			showMenu("createWarMenuPanel");
			state = StateGUI.STATE_CREATE_WAR;
			break;

		case ACTION_CREATE_WAR_SELECTED: 
			if (createWarPanel.getWarName().compareToIgnoreCase("") == 0) {
				showAlert("You must provide a war name !!!");
				break;
			}
			int warID = net.createWar(createWarPanel.getWarName(), createWarPanel.getWarDesc());
			if (warID == IClientNetworkModule.ERROR_WHEN_CREATING_WAR) {
				showAlert("It was not possible to create the war !!!");
				break;
			}
			else {
				this.warID = warID;
				showAlert("The war was successfully created. Now you must define and register your army !!!");
			}
			createArmyPanel.resetPanel();
			showPanel("createArmyPanel");
			showMenu("registerArmyMenuPanel");
			state = StateGUI.STATE_REGISTER_ARMY;
			break;

		case ACTION_TO_MAIN_MENU_FROM_CREATE_WAR_MENU:
			showPanel("welcomePanel");
			showMenu("mainMenuPanel");
			state = StateGUI.STATE_WELCOME;
			break;

		case ACTION_LIST_WARS_MENU_SELECTED:
			listWarsPanel.resetPanel();
			List<ListWarItem> warsList = net.getWarsList();
			for(ListWarItem wi : warsList) {
				listWarsPanel.addWarItem(wi);
			}
			showPanel("listWarsPanel");
			showMenu("listWarsMenuPanel");
			state = StateGUI.STATE_LIST_WARS;
			break;

		case ACTION_JOIN_WAR_SELECTED:
			this.warID = listWarsPanel.getSelectWarID();
			if (this.warID == -1) {
				showAlert("You must select a war or go back to main menu");
				break;
			}
			createArmyPanel.resetPanel();
			showPanel("createArmyPanel");
			showMenu("registerArmyMenuPanel");
			state = StateGUI.STATE_REGISTER_ARMY;
			break;

		case ACTION_TO_MAIN_MENU_FROM_LIST_WARS_MENU:
			showPanel("welcomePanel");
			showMenu("mainMenuPanel");
			state = StateGUI.STATE_WELCOME;
			break;


		case ACTION_REGISTER_ARMY_SELECTED:
			if (!createArmyPanel.areAllUnitsPlaced()) {
				showAlert("You must place all units in the army field !!!");
				break;    		
			}
			if (net.regArmy(this.warID, createArmyPanel.getArmyName(), createArmyPanel.getUnitsAndPlaces())
					== IClientNetworkModule.ERROR_WHEN_REGISTERING_ARMY) {
				showAlert("It was not possible to register the army !!!");
				break;    		
			}
			else {
				showAlert("Army was successfully registered. Now it is time to wait for the start of the war !!!");
			}
			warPanel.resetPanel();
			warPanel.addOwnArmy(createArmyPanel.getUnitsAndPlaces());
			showPanel("warPanel");
			showMenu("playingMenuPanel");
			state = StateGUI.STATE_WAIT_FOR_START_WAR;
			break;

		case ACTION_TO_MAIN_MENU_FROM_REGISTER_ARMY_MENU:
			showPanel("welcomePanel");
			showMenu("mainMenuPanel");
			state = StateGUI.STATE_WELCOME;
			break;

		case ACTION_START_WAR_SELECTED:
			res = net.startWar(this.warID,this.ownArmyID);
			if (res == IClientNetworkModule.ERROR_WHEN_STARTING_WAR) {
				showAlert("There was an error when starting the war");
				break;
			}
			showAlert("The war started !!!");
			state = StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN;
			break;
		
		case ACTION_SURRENDER:
			showPanel("welcomePanel");
			showMenu("mainMenuPanel");
			break;


		case CONNECT:
			res = net.connect(
					ip.getText(),
					Integer.parseInt(port.getText())
					);
			// connection OK
			if (res == 1) {
				System.out.println("gui:Connected to server.");
				ip.setEnabled(false);
				port.setEnabled(false);
				btConnect.setEnabled(false);
				btDisconnect.setEnabled(true);
				btCreateWarMenu.setEnabled(true);
				btListWarsMenu.setEnabled(true);
			}
			// connection NOK
			else {
				System.out.println("gui:Connection to server failed.");
			}
			break;

		case DISCONNECT:
			res = net.disconnect();
			// disconnection OK
			if (res == 1) {
				System.out.println("gui:Disconnected from server.");
				ip.setEnabled(true);
				port.setEnabled(true);
				btConnect.setEnabled(true);
				btDisconnect.setEnabled(false);
				btCreateWarMenu.setEnabled(false);
				btListWarsMenu.setEnabled(false);
			}
			// disconnection NOK
			else {
				System.out.println("gui:Disonnection form server failed.");
			}
			break;
		}
	}

	public void notifyClickOnCell(int armyID, int row, int col) {
		int res = net.shot(warID, ownArmyID, armyID, row, col);
		switch(res) {
		case IClientNetworkModule.SHOT_IN_TARGET:
			showAlert("You shot in target !!!");
			state = StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN;
			break;
		case IClientNetworkModule.SHOT_NOT_IN_TARGET:
			showAlert("You did not shoot in target !!!");
			state = StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN;
			break;
		case IClientNetworkModule.ERROR_WHEN_SHOOTING:
			showAlert("There was an error when shooting :(");
			state = StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN;
			break;
		}
	}


	//-----------------------------------
	// IGUIModule methods
	//-----------------------------------

	public void bindNetModule(IClientNetworkModule net) {
		this.net = net;
	}

	public int notifyArmyInWar(int armyID) {
		if (state != StateGUI.STATE_WAIT_FOR_START_WAR)
			throw new IllegalStateException("notifyArmyInWar(...) must be called when waiting for start war");
		return warPanel.addEnemyArmyField(armyID);
	}


	public int startWar() {
		if (state != StateGUI.STATE_WAIT_FOR_START_WAR)
			throw new IllegalStateException("startWar(...) must be called when waiting for start war");
		if (!warPanel.isArmyInWar(ownArmyID)) return IGUIModule.ERROR_NOT_REGISTERED_IN_WAR;
		warPanel.startWar();
		state = StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN;
		return NOTIFICATION_RECEIVED_OK;
	}


	public int turnArmy() {
		if (state == StateGUI.STATE_IN_WAR_AND_IN_TURN)
			return IGUIModule.ERROR_ALREADY_MY_TURN;
		if (state != StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN)
			throw new IllegalStateException("turnArmy(...) must be called when waiting for turn");
		if (!warPanel.isArmyInWar(ownArmyID)) return IGUIModule.ERROR_NOT_REGISTERED_IN_WAR;
		warPanel.turnArmy();
		state = StateGUI.STATE_IN_WAR_AND_IN_TURN;
		return NOTIFICATION_RECEIVED_OK;
	}


	public int turnArmyTimeout() {
		if (state == StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN)
			return IGUIModule.ERROR_NOT_MY_TURN;
		if (state != StateGUI.STATE_IN_WAR_AND_IN_TURN)
			throw new IllegalStateException("turnArmyTimeout(...) must be called when in my turn");
		if (!warPanel.isArmyInWar(ownArmyID)) return IGUIModule.ERROR_NOT_REGISTERED_IN_WAR;
		warPanel.turnArmyTimeout();
		state = StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN;
		return NOTIFICATION_RECEIVED_OK;
	}


	public int endWar(int winnerArmyID) {
		if (state == StateGUI.STATE_IN_WAR_AND_IN_TURN)
			return IGUIModule.ERROR_END_WAR_WHEN_IN_MY_TURN;
		if (state != StateGUI.STATE_IN_WAR_AND_WAIT_FOR_TURN)
			throw new IllegalStateException("turnArmyTimeout(...) must be called when in my turn");
		if (!warPanel.isArmyInWar(ownArmyID)) return IGUIModule.ERROR_NOT_REGISTERED_IN_WAR;
		warPanel.endWar();
		state = StateGUI.STATE_WAR_ENDED;
		return NOTIFICATION_RECEIVED_OK;
	}


	public int shotImpact(int targetArmyID, int row, int col, ShotImpact impact) {
		if (!warPanel.isArmyInWar(targetArmyID)) return IGUIModule.ERROR_NOT_REGISTERED_IN_WAR;

		if (targetArmyID == ownArmyID) {
			switch (impact) {
			case SHOT_IN_EMPTY_CELL:
				warPanel.drawShot(targetArmyID, row, col, false);
				showAlert("You received a shot in your field but wihout any risk");
				break;
			case SHOT_IN_UNIT_BUT_STILL_OPERATIONAL:
				warPanel.drawShot(targetArmyID, row, col, true);
				showAlert("You received a shot in one of your units !!!");
				break;
			case SHOT_IN_UNIT_AND_DESTROYED:
				warPanel.drawShot(targetArmyID, row, col, true);
				showAlert("You received a shot and one of your units is destroyed !!!");
				break;
			case SHOT_IN_ALREADY_SHOT_UNIT:
				showAlert("You received a shot in an already shot unit !!!");
				break;
			}
		}
		else {
			switch (impact) {
			case SHOT_IN_EMPTY_CELL:
				warPanel.drawShot(targetArmyID, row, col, false);
				break;
			case SHOT_IN_UNIT_BUT_STILL_OPERATIONAL:
				warPanel.drawShot(targetArmyID, row, col, true);
				break;
			case SHOT_IN_UNIT_AND_DESTROYED:
				warPanel.drawShot(targetArmyID, row, col, true);
				break;
			case SHOT_IN_ALREADY_SHOT_UNIT:

				break;
			}
		}
		return IGUIModule.NOTIFICATION_RECEIVED_OK;
	}
	
	public void setCreateWarPanel (CreateWarPanel panel) {
		this.createWarPanel = panel;
	}
	
	public int getWarID() {
		return this.warID;
	}

}



