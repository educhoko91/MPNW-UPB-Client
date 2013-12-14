package navalwar.client.gui;

import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListWarsPanel extends JPanel {
	
	private JList listWars;
	private DefaultListModel listModel;
	private JScrollPane scrollPane;
	
	
	public ListWarsPanel() {
		listModel = new DefaultListModel();
	
		listWars = new JList(listModel);
        listWars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listWars.setSelectedIndex(0);
        listWars.setVisibleRowCount(15);
        listWars.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					// listWars.getSelectedIndex();
				}
			}
		});

        scrollPane = new JScrollPane(listWars);
        
		setLayout(new FlowLayout());
		add(scrollPane);

	}


	public void updateTest() {
		listModel.addElement("guerra1");
		listModel.addElement("this is another war");
		listModel.addElement("another war");
		listModel.addElement("another war2");
		listModel.addElement("another war3");
		listModel.addElement("another war4");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
		listModel.addElement("another war5");
	}
	

}
