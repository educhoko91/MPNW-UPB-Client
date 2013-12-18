package navalwar.client.gui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	public List<Integer> listWarIds;
	private int selectedWarId;
	
	
	public ListWarsPanel() {
		listModel = new DefaultListModel();
		listWarIds = new ArrayList<>();
	
		listWars = new JList(listModel);
        listWars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listWars.setSelectedIndex(0);
        listWars.setVisibleRowCount(15);
        listWars.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					int index = listWars.getSelectedIndex();
					selectedWarId = listWarIds.get(index);
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
	
	public void loadList(Map<Integer, String> map) {
		for (Entry<Integer, String> item : map.entrySet()) {
			listModel.addElement(item.getValue());
			listWarIds.add(item.getKey());
		}
	}
	
	public int getSelectedWarID() {
		return this.selectedWarId;
	}
	

}
