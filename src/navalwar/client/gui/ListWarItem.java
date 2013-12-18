package navalwar.client.gui;

public class ListWarItem {

	private int warID;
	private String name;
	
	public ListWarItem(int warID, String name) {
		this.warID = warID;
		this.name = name;
	}
	
	public int getWarID() { return warID; }
	public String getName() { return name; }
	
	public String toString() { return name; }
}
