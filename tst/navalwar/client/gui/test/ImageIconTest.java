package navalwar.client.gui.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class ImageIconTest {
	
	ImageIcon icon;
	FileNotFoundException e;

	@Test(expected= Exception.class)
	public void testWrongFile() {
		icon = new ImageIcon("None Existing File");
	}
	
	@Test
	public void testCorretFile() {
		icon = new ImageIcon("res/empty_cell.jpg");
	}
	
	@Test
	public void testExpectedNull() {
		icon = new ImageIcon("None Existing File");
		assertNull(icon);
	}

}
