package sample;

import javax.swing.*;
import java.awt.*;

public class CampusPathsMain {

	/**
	 * Creates the main gui window of the CampusPaths application 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Campus Paths");
		Image i = Toolkit.getDefaultToolkit().getImage(
				"src/data/campus_map.jpg");
		CampusModel mod = new CampusModel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CampusPathControl control = new CampusPathControl(mod, i);
		frame.add(control, BorderLayout.CENTER);
		frame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((int) screenSize.getWidth() / 2 - control.getWidth() / 2,
						(int) (screenSize.getHeight() / 2 - control.getHeight() / 2),
						control.getWidth(), control.getHeight());
		frame.setVisible(true);
	}

}
