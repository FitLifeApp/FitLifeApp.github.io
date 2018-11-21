
package edu.baylor.ecs.FitLifeApp;

/*
 * File:		WindowManager.java
 * Description: Handles the base creation of windows while logged in
 * 				As well as swapping between windows
 */
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.*;

import java.util.*;

	public class WindowManager {

	static JPanel cards; // a panel that uses CardLayout

	final static String ex1 = "Aerobic Exercise";
	final static String ex2 = "Strength Exercise";
	final static String ex3 = "Balance Exercise";
	final static String ex4 = "Flexibility Exercise";

	// -----------------------------------------
	
	private static LogIn login = LogIn.getInstance();
	private static AcctCreator acctCreator = AcctCreator.getInstance();
	private static CalendarWindow calendarWindow = CalendarWindow.getInstance();
	
	//-----------------------------------------
	private static JFrame window;
	private static Account acct;
	
	
	private static volatile WindowManager instance = null;
	
	/*singleton constructor*/
	protected WindowManager() {
		acct = null;
		window = new JFrame("Welcome");
	}
	
	public static WindowManager getInstance() {
		if (instance == null) {
			synchronized(WindowManager.class) {
				if(instance == null) {
					instance = new WindowManager();
				}
			}
		}
		return instance;
	}
	

	/*
	 * //I assume leftovers JLabel result; String currentPattern;
	 * This seems fine but why 
	 */
	 class BasicActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			//Home button is clicked, spawn home page
			if (e.getActionCommand().equals("Home")) {
				toHome();
				System.out.println("HOME");
				
			//if logout is selected displose of window and spawn log in screen	
			} else if (e.getActionCommand().equals("Log Out")) {
				window.dispose();
				toLogIn();
				System.out.println("Logged Out");
				
			//if view calendar is selected spawn calendar
			} else if (e.getActionCommand().equals("View Calendar")) {

				// Might get removed
				toCalendar();
				System.out.println("View Cal");
				
			//if a date is selected use joption pane	
			} else if (e.getActionCommand().equals("Date selected")) {

				JOptionPane.showMessageDialog(new JFrame(), "\"Date Selected\" in WindowManager/BasicActListener",
						"Failed Login", JOptionPane.ERROR_MESSAGE);
				
			//if the confirm button is hit show the add workout dialog
			} else if (e.getActionCommand().equals("Confirm")) {
				try {
					calendarWindow.showAddWorkoutDialog();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				
			//TO DO: remove code, unneeded
			} else if (e.getActionCommand().equals("Plan Workout")) {
				// addWorkoutWindow();
				System.out.println("Planning Workout");
			} else {
				System.err.println("Unhandled Action Command: " + e.getActionCommand());
			}
		}
	}

	//state change listener
	static class BasicItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent evt) {
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, (String) evt.getItem());
		}

	}

	
	//// Handles Base construction of frame
	// Constructs a frame with a menu bar with various pages
	//This functionality is used by many, maybe it should just be inherited
	public JFrame makeWindow(JFrame window) {
		
		//remove everything form the content pane and set the layout
		window.getContentPane().removeAll();
		window.setLayout(new BorderLayout());

		//setup menubar
		JMenuBar jmb1 = new JMenuBar();
		jmb1.setPreferredSize(new Dimension(2000, 70));
		jmb1.setBackground(acct.getColorBase2());

		//setup menu
		JMenu menu = new JMenu("Menu");
		menu.setFont(new Font("Menu", Font.PLAIN, 25));
		menu.setPreferredSize(new Dimension(2000, 70));
		menu.setForeground(Color.white);

		//setup menu items
		JMenuItem home = new JMenuItem("Home");
		home.setFont(new Font("Home", Font.PLAIN, 20));
		home.setBackground(acct.getColorBase2());
		home.setForeground(Color.white);

		JMenuItem logOut = new JMenuItem("Log Out");
		logOut.setFont(new Font("Log Out", Font.PLAIN, 20));
		logOut.setBackground(acct.getColorBase2());
		logOut.setForeground(Color.white);

		JMenuItem toCal = new JMenuItem("View Calendar");
		toCal.setFont(new Font("View Calendar", Font.PLAIN, 20));
		toCal.setBackground(acct.getColorBase2());
		toCal.setForeground(Color.white);

		JMenuItem addWorkout = new JMenuItem("Add Workout");
		addWorkout.setFont(new Font("Add Workout", Font.PLAIN, 20));
		addWorkout.setBackground(acct.getColorBase2());
		addWorkout.setForeground(Color.white);

		JMenuItem planWorkout = new JMenuItem("Plan Workout");
		planWorkout.setFont(new Font("Plan Workout", Font.PLAIN, 20));
		planWorkout.setBackground(acct.getColorBase2());
		planWorkout.setForeground(Color.white);

		//add items to the menu
		menu.setMaximumSize(new Dimension(2000, 50));
		menu.add(home);
		menu.add(toCal);
		menu.add(addWorkout);
		menu.add(planWorkout);
		menu.add(logOut);

		// Buttons to swap between pages
		home.addActionListener(instance.new BasicActListener());
		logOut.addActionListener(instance.new BasicActListener());
		toCal.addActionListener(instance.new BasicActListener());
		addWorkout.addActionListener(instance.new BasicActListener());
		planWorkout.addActionListener(instance.new BasicActListener());

		//add the menu
		jmb1.add(menu);

		//pack the frame
		window.add(jmb1, BorderLayout.NORTH);
		window.pack();
		
		// Doesn't handle sizing page or making visible
		return window;
	}

	//sets the Jframe window to the Jframe returned by makeWindow with window passed to it
	public void toHome() {
		window = makeWindow(window);
		window = HomePage.makeWindow(window, acct);
	}

	//Makes log in window
	public void toLogIn() {
		window = login.makeWindow(window);
	}

	//makes account creation window
	public void toAcctCreation() {
		window = acctCreator.makeWindow(window);
	}

	
	//makes Calendar window
	public void toCalendar() {
		//smae stuff for calendar window
		window = calendarWindow.makeWindow(window);

	}

	// Not moved yet.
	public void toDay(Date day) {
		window.dispose();
		window = makeWindow(window);

		File file = new File("workout.csv");
		int row = 0;
		ArrayList<String[]> arr = new ArrayList<String[]>();
		
		// Get all the data from the file
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String string = input.nextLine();
				String[] temp = new String[6];
				String[] str = string.split(",");
				for (int i = 0; i < str.length; i++) {
					temp[i] = str[i];
				}
				arr.add(temp);
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Select only the data from that specific day
		String date = day.toString();
		String specificDay = date.substring(0, 10);
		ArrayList<String[]> subArr = new ArrayList<String[]>();
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i)[5].contains(specificDay)) {
				subArr.add(arr.get(i));
				row++;
			}
		}
		
		// Not use header yet
		String header[] = {"Name","Your weight","Lift weight","Duration"};
		JTable data = new JTable(row, 4);
		
		// Only add the corresponding data to the table (No more write space caused by hiding part of the data)
		for (int i = 0; i < subArr.size(); i++) {
			for (int j = 0; j < data.getColumnCount(); j++) {
				data.setValueAt(subArr.get(i)[j + 1], i, j);
			}
		}
		
		// Sorter for the table
		TableRowSorter <TableModel> sorter = new TableRowSorter <TableModel> (data.getModel());
		data.setRowSorter(sorter);
		sorter.setComparator(1, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		});
		sorter.setComparator(2, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		});
		sorter.setComparator(3, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		});
		
		// Add filter for user search specific data they interested
		JPanel filter = new JPanel();
		filter.setLayout(new FlowLayout());
		JLabel lab = new JLabel("Enter the work out name: ");
		filter.add(lab);
		JTextField TF = new JTextField("");
		TF.setPreferredSize(new Dimension(150, 30));
		filter.add(TF);
		JButton BF = new JButton("Search");
		filter.add(BF);
		BF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = TF.getText();
				if (str.length() == 0) {
					sorter.setRowFilter(null);
				}
				else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str, 0));
				}
			}
		});

		window.add(data, BorderLayout.CENTER);
		
		window.add(filter, BorderLayout.SOUTH);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setSize((int) window.getSize().getWidth() + 50, (int) window.getSize().getHeight() + 50);
		window.setVisible(true);
	}

	// Also not yet moved
	public static void addWorkoutWindow() {
		window = new JFrame("Select a Type");

		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(new BasicItemListener());
		comboBoxPane.add(cb);

		JButton j1 = new JButton("Confirm");

		j1.addActionListener(instance.new BasicActListener());

		// Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(j1);

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(card1, ex1);
		cards.add(card1, ex2);
		cards.add(card1, ex3);
		cards.add(card1, ex4);

		window.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
		window.getContentPane().add(cards, BorderLayout.CENTER);

		window.setLocationRelativeTo(null);

		// Display the window.
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 100, (int) window.getSize().getHeight() + 30);
		window.setVisible(true);
	}

	public void setAcct(Account src) {
		// TODO Auto-generated method stub
		System.out.println(src.toString());
		WindowManager.acct = src;

	}
}