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
	
	public JList listWars;
	private DefaultListModel listModel;
	private JScrollPane scrollPane;
	private int selectedIndex;
	
	
	public ListWarsPanel() {
		listModel = new DefaultListModel();
	
		listWars = new JList(listModel);
        listWars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listWars.setSelectedIndex(0);
        listWars.setVisibleRowCount(15);
        listWars.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					selectedIndex = listWars.getSelectedIndex();
				}
			}
		});

        scrollPane = new JScrollPane(listWars);
        
        selectedIndex = -1;
        
		setLayout(new FlowLayout());
		add(scrollPane);

	}


	public void resetPanel() {
		listModel.removeAllElements();
	}


	public void addWarItem(ListWarItem item) {
		listModel.addElement(item);
	}
	
	public int getSelectedWarID() {
		if (selectedIndex == -1) return -1;
		return ((ListWarItem) listModel.get(selectedIndex)).getWarID();
		
	}


	public String getSelectedWarName() {
		if (selectedIndex == -1) return "";
		return ((ListWarItem) listModel.get(selectedIndex)).getName();
	}
	

}
