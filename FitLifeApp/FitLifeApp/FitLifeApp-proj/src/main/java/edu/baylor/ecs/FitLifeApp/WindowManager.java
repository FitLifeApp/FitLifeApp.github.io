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

		JTable cal = new JTable(4, 7);
		// Display an empty 28 day calendar

		window.add(cal, BorderLayout.CENTER);
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 50, (int) window.getSize().getHeight() + 50);
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
		} else {
			System.err.println("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}