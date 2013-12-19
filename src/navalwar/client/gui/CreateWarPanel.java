package navalwar.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateWarPanel extends JPanel {

	private JTextArea tfWarName;
	private JTextArea taWarDesc;
	
	public CreateWarPanel() {
        //setBorder(BorderFactory.createTitledBorder("Game"));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		//JPanel panel2 = new JPanel();
        //panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //panel.setLayout(new Gri);
		//panel.setLayout(new GridLayout(4,1));
        //panel.setLayout(new GridLayout(1,1));
       
        
        //panel.add(Box.createVerticalGlue());
        panel.add(Box.createRigidArea(new Dimension(400,150)));
        panel.add(new JLabel("War name:"));
        //tfWarName = new JTextField("");
        tfWarName = new JTextArea(1, 30);
        //tfWarName.setColumns(30);
        //tfWarName.setRows(1);
        tfWarName.setBackground(Color.BLACK);
        tfWarName.setForeground(Color.WHITE);
        //tfWarName.setHorizontalAlignment(JTextField.LEFT);
        panel.add(tfWarName);
        panel.add(new JLabel("War description:"));
        taWarDesc = new JTextArea(5, 30);
        //taWarDesc.setColumns(30);
        //taWarDesc.setRows(5);
        //taWarDesc.setSize(new Dimension(200,50));
        taWarDesc.setBackground(Color.BLACK);
        taWarDesc.setForeground(Color.WHITE);
        //taWarDesc.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        //taWarDesc.setAlignmentY(JTextArea.TOP_ALIGNMENT);
        panel.add(taWarDesc);
        //panel.add(Box.createVerticalGlue());
        panel.add(Box.createRigidArea(new Dimension(400,150)));
        
        //panel2.add(Box.createGlue());
        //panel2.add(panel);
        //panel2.add(Box.createGlue());
        
        add(Box.createRigidArea(new Dimension(100,400)));
        add(panel);
        add(Box.createRigidArea(new Dimension(100,400)));
//        add(Box.createVerticalGlue());
        	}
	
	
	public String getWarName() { return tfWarName.getText(); }
	public String getWarDesc() { return taWarDesc.getText(); }
	
	public void resetPanel() {
		tfWarName.setText("");
		taWarDesc.setText("");
	}
}
