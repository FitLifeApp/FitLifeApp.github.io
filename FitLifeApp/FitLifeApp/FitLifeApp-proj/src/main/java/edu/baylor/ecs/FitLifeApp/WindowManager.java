package displayTest;

/*
 * Program: FitLife
 * File: WindowManager.java
 * Description: Creates various windows and handles moving between windows
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

public class WindowManager implements ActionListener {
	private UtilDateModel model = new UtilDateModel();
	private JDatePanelImpl datePanel;
	private Date day = java.util.Calendar.getInstance().getTime();
	JFrame window;

	WindowManager() {
		window = null;


		toLogIn();

		/*
		 * window = makeWindow(); window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * //window.pack(); window.setSize(300, 300); window.setVisible(true);
		 */
	}

	private JFrame makeWindow() {
		// Handles Base construction of frame
		// Constructs a frame with a menu bar with various pages

		JFrame f = new JFrame();
		JMenuBar m = new JMenuBar();
		JMenuItem home = new JMenuItem("Home");
		JMenuItem logOut = new JMenuItem("Log Out");
		JMenuItem toCal = new JMenuItem("View Calendar");
		// Buttons to swap between pages

		home.addActionListener(this);
		logOut.addActionListener(this);
		toCal.addActionListener(this);

		m.add(home);
		m.add(logOut);
		m.add(toCal);

		f.add(m, BorderLayout.PAGE_START);

		// Doesn't handle sizing page or making visible
		return f;
	}

	public void toHome() {
		// Displays home page
		// Not sure what all would go here
		// Currently used for BorderLayout testing

		window.dispose();
		window = makeWindow();
		// Get rid of whatever is currently displayed
		// Make basic frame

		JLabel l1 = new JLabel("Info One: Line Start");
		JLabel l2 = new JLabel("Info Two: Line End");
		JLabel l3 = new JLabel("Info Three: Page End");
		JLabel l4 = new JLabel("Info Fore: Center");
		// Labels for BorderLayout tests

		window.add(l1, BorderLayout.LINE_START);
		window.add(l2, BorderLayout.LINE_END);
		window.add(l3, BorderLayout.PAGE_END);
		window.add(l4, BorderLayout.CENTER);

		window.pack();
		window.setSize((int) window.getSize().getWidth() + 30, (int) window.getSize().getHeight() + 100);
		// Testing for window sizes.
		// In my opinion, just saying pack makes it too compact

		window.setVisible(true);
	}

	public void toLogIn() {
		window = LogIn.makeWindow(window, this);
	}

	public void toCalendar() {
		window.dispose();
		window = makeWindow();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		
		datePanel.setShowYearButtons(true);
		datePanel.setEnabled(true);
		datePanel.setFocusable(true);
		datePanel.addActionListener(this);

		
		window.add(datePanel, BorderLayout.CENTER);
		window.pack();
		//window.setSize((int)window.getSize().getWidth() + 50, (int)window.getSize().getHeight() + 50);
		window.setSize((int)window.getSize().getWidth() + 100, (int)window.getSize().getHeight() + 100);
		window.setVisible(true);
	}
	
	
	public void toDay(Date day) {
		window.dispose();
		window = makeWindow();
		
		File file = new File("workout.csv");
		int row = 0;
		ArrayList <String[]> arr = new ArrayList<String[]>();
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String string = input.nextLine();
				String[] str = string.split(",");
				String[] temp = new String[5];
				for (int i = 0; i < temp.length; i ++) {
					temp[i] = str[i];
				}
				arr.add(temp);
				row ++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
		JTable data = new JTable(row, 5);
		for (int i = 0; i < data.getRowCount(); i++) {
			for (int j = 0; j < arr.get(0).length; j++) {
				data.setValueAt(arr.get(0)[j], i, j);
			}
		}

		
		
		
		window.add(data, BorderLayout.CENTER);
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 50, (int)window.getSize().getHeight() + 50);
		window.setVisible(true);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Log In")) {
			if(LogIn.validate(window)) {
				toHome();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Incorrect Username/Password", "Failed Login", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand().equals("Home")) {
			toHome();
			System.out.println("HOME");
		} else if (e.getActionCommand().equals("Log Out")) {
			toLogIn();
			System.out.println("LogOute");
		} else if (e.getActionCommand().equals("View Calendar")) {
			toCalendar();
			System.out.println("View Cal");
		} else if (e.getActionCommand().equals("Date selected")) {
			System.out.println((Date)datePanel.getModel().getValue());
			// Call toDay if the selected day already has been selected. (or the selected day is today)
			if (day.equals((Date)datePanel.getModel().getValue())) {
				toDay((Date)datePanel.getModel().getValue());
			}
			day = (Date)datePanel.getModel().getValue();
		} else {
			System.err.println("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}