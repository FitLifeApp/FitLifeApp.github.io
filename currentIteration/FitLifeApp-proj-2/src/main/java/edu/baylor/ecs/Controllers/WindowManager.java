
package edu.baylor.ecs.Controllers;

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

import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.Listeners.BasicActListener;

import java.awt.event.*;

import java.util.*;

	public class WindowManager {

	JPanel cards; // a panel that uses CardLayout

	final String ex1 = "Aerobic Exercise";
	final String ex2 = "Strength Exercise";
	final String ex3 = "Balance Exercise";
	final String ex4 = "Flexibility Exercise";

	// -----------------------------------------
	
	private static LogIn login = LogIn.getInstance();
	private static AcctCreator acctCreator = AcctCreator.getInstance();
	private static CalendarWindow calendarWindow = CalendarWindow.getInstance();
	private static HomePage home = HomePage.getInstance();
	
	//-----------------------------------------
	private JFrame window;

	private Account acct;
	
	
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
	

	//state change listener
	class BasicItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent evt) {
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, (String) evt.getItem());
		}

	}

	
	//// Handles Base construction of frame
	// Constructs a frame with a menu bar with various pages
	//This functionality is used by many, maybe it should just be inherited
	

	//sets the Jframe window to the Jframe returned by makeWindow with window passed to it
	public void toHome() {
		home.makeWindow(acct);
	}

	//Makes log in window
	public void toLogIn() {
		login.makeWindow();
	}

	//makes account creation window
	public void toAcctCreation() {
		window = acctCreator.makeWindow(window);
	}

	
	//makes Calendar window
	public void toCalendar() {
		//same stuff for calendar window
		calendarWindow.makeWindow();

	}

	// Not moved yet.
	public void toDay(Date day) {
		window.dispose();

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
		//String header[] = {"Name","Your weight","Lift weight","Duration"};
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
	public void addWorkoutWindow() {
		this.window = new JFrame("Select a Type");

		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(new BasicItemListener());
		comboBoxPane.add(cb);

		JButton j1 = new JButton("Confirm");

		j1.addActionListener(new BasicActListener());

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
		acct = src;

	}
	
	
	public CalendarWindow getCalendarWindow() {
		return calendarWindow;
	}
	
	public JFrame getWindow() {
		return window;
	}
	
	
}