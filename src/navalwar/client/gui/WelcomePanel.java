package navalwar.client.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel {
	
	private static ImageIcon IMG_WELCOME = new ImageIcon("res/welcome.jpg");
	
	public WelcomePanel() {
		setLayout(new GridLayout(1,1));
		add(new JLabel(IMG_WELCOME));
		//setPreferredSize(new Dimension(IMG_WELCOME.getIconWidth(), IMG_WELCOME.getIconHeight()));
		setPreferredSize(new Dimension(600, 180));
	}

}
