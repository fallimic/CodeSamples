package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;


public class CampusPathControl extends JPanel {

	private CampusModel mod;
	private CampusBuilding start;
	private CampusBuilding end;
	private CampusMapView viewer; // map region
	private JPanel buttons; // controls region
	private JComboBox<String> c1; // start drop down
	private JComboBox<String> c2; // destination drop down
	
	/**
	 * Creates all of the gui components
	 * @param mod CampusModel containing all the building and path data
	 * @param i	The campus_map.jpg image
	 */
	public CampusPathControl(final CampusModel mod, Image i) {
		super();
		this.mod = mod;
		start = mod.getBuilding("BAG");
		end = mod.getBuilding("BAG");
		setPreferredSize(new Dimension(1024, 768));

		buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(1024, 40));
		buttons.setBackground(Color.gray);
		add(buttons, BorderLayout.NORTH);

		makeButtons();
		makePopup();
		viewer = new CampusMapView(i);
		add(viewer, BorderLayout.SOUTH);

	}

	//Creates the reset, find path, and drop down controls and adds them to the buttons container
	private void makeButtons() {
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewer.reset();
				c1.setSelectedIndex(0); // reset Drop down
				c2.setSelectedIndex(0); // reset drop down
			}
		});

		JButton pathB = new JButton("FindPath");
		pathB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewer.reset();
				CampusPath path = mod.getPath(start, end);
				viewer.setPath(path);
			}
		});
		makeDropDown();
		buttons.add(pathB);
		buttons.add(reset);
	}

	//creates the drop down menus and adds them to the buttons container
	private void makeDropDown() {
		c1 = new JComboBox<String>();
		c2 = new JComboBox<String>();
		Collection<CampusBuilding> buildings = mod.getBuildings();
		for (CampusBuilding b : buildings) {
			c1.addItem(b.toString());
			c2.addItem(b.toString());
		}

		c1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) ((JComboBox<?>) e.getSource())
						.getSelectedItem();
				String name = (selected.split(":"))[0];
				start = mod.getBuilding(name);
			}
		});

		c2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) ((JComboBox<?>) e.getSource())
						.getSelectedItem();
				String name = (selected.split(":"))[0];
				end = mod.getBuilding(name);
			}
		});

		c1.setPreferredSize(new Dimension(330, 25));
		c2.setPreferredSize(new Dimension(330, 25));
		buttons.add(new JLabel("Start:"));
		buttons.add(c1);
		buttons.add(new JLabel("Destination:"));
		buttons.add(c2);
	}
	
	//Creates the welcome popup box that appears on startup
	private void makePopup() {
		final JDialog welcome = new JDialog();
		welcome.setAlwaysOnTop(true);
		welcome.setAutoRequestFocus(true);
		JLabel message = new JLabel();
		message.setText("<HTML><h1 align=\"center\">Welcome to Campus Maps!</h1><p align=\"center\">Please select a starting building"
				+ " and a destination building from the drop down menus above and click \"Find Path\"</p></HTML>");
		message.setHorizontalAlignment(SwingConstants.CENTER);
		welcome.add(message, BorderLayout.CENTER);
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				viewer.reset(); //resets the view to make the image load
				welcome.dispose();
			}

		});
		welcome.add(ok, BorderLayout.SOUTH);
		welcome.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		welcome.setBounds((int) screenSize.getWidth() / 2 - 225,
				(int) (screenSize.getHeight() / 2 - 150), 450, 300); //center it in the window
		welcome.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		welcome.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int currentWidth = getWidth();
		int currentHeight = getHeight();
		viewer.setBounds(0, 40, currentWidth, currentHeight - 40);
		buttons.setBounds(0, 0, currentWidth, 40);
	}
}
