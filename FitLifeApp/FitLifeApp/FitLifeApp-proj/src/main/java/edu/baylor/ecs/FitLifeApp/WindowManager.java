package swing;

import java.awt.*;
import java.io.FileNotFoundException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.SimpleDateFormat;


public class WindowManager implements ActionListener, ItemListener{
	JFrame window;
	JPanel cards; //a panel that uses CardLayout

	final static String ex1 = "Aerobic Exercise";
	final static String ex2 = "Strength Exercise";
	final static String ex3 = "Balance Exercise";
	final static String ex4 = "Flexibility Exercise";
	
	private Color c1 = new Color(72, 201, 176);
	private Color c2 = new Color(26, 188, 156);
	private Color c3 = new Color(174, 214, 241);
	private Color c4 = new Color(84, 153, 199);
	
    FlowLayout experimentLayout = new FlowLayout();
	
	JLabel result;
	String currentPattern;
	WindowManager() {
		window = null;

		toLogIn();
	}

	private JFrame makeWindow() {
		//Handles Base construction of frame
		//Constructs a frame with a menu bar with various pages

		JFrame f = new JFrame("Home");
		JMenuBar jmb1 = new JMenuBar();
		jmb1.setBackground(c2);
		
		JMenu menu = new JMenu("Menu");
		menu.setFont(new Font("Menu", Font.PLAIN, 30));
		menu.setPreferredSize(new Dimension(100, 100));
		menu.setForeground(Color.white);
		
		JMenuItem home = new JMenuItem("Home");
		home.setFont(new Font("Home", Font.PLAIN, 20));
		home.setBackground(c2);
		home.setForeground(Color.white);
		
		JMenuItem logOut = new JMenuItem("Log Out");
		logOut.setFont(new Font("Log Out", Font.PLAIN, 20));
		logOut.setBackground(c2);
		logOut.setForeground(Color.white);
		
		JMenuItem toCal = new JMenuItem("View Calendar");
		toCal.setFont(new Font("View Calendar", Font.PLAIN, 20));
		toCal.setBackground(c2);
		toCal.setForeground(Color.white);
		
		JMenuItem addWorkout = new JMenuItem("Add Workout");
		addWorkout.setFont(new Font("Add Workout", Font.PLAIN, 20));
		addWorkout.setBackground(c2);
		addWorkout.setForeground(Color.white);
		
		JMenuItem planWorkout = new JMenuItem("Plan Workout");
		planWorkout.setFont(new Font("Plan Workout", Font.PLAIN, 20));
		planWorkout.setBackground(c2);
		planWorkout.setForeground(Color.white);
		

		menu.add(home);
		menu.add(toCal);
		menu.add(addWorkout);
		menu.add(planWorkout);
		menu.add(logOut);

		//Buttons to swap between pages

		home.addActionListener(this);
		logOut.addActionListener(this);
		toCal.addActionListener(this);
		addWorkout.addActionListener(this);
		planWorkout.addActionListener(this);

		jmb1.add(menu, BorderLayout.LINE_END);
		//jmb1.add(logOut, BorderLayout.LINE_START);


		f.add(jmb1, BorderLayout.PAGE_START);
		//f.add(m, BorderLayout.PAGE_START);

		//Doesn't handle sizing page or making visible
		return f;
	}

	public void toHome() {
		//Displays home page
		//Not sure what all would go here
		//Currently used for BorderLayout testing

		window.dispose();
		window = makeWindow();
		
		final JPanel p1 = new JPanel();
        p1.setLayout(experimentLayout);
        experimentLayout.setAlignment(FlowLayout.TRAILING);
        
        final JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        experimentLayout.setAlignment(FlowLayout.TRAILING);
        
        JPanel board = new JPanel();
        board.setLayout(new FlowLayout());

        JButton jb1 = new JButton("NUTRITION");
        jb1.setFont(new Font("NUTRITION", Font.PLAIN, 50));
        jb1.setBackground(c4);
        jb1.setForeground(Color.WHITE);
        
		JButton jb2 = new JButton("EXERCISE");
		jb2.setFont(new Font("EXERCISE", Font.PLAIN, 50));
		jb2.setBackground(c4);
		jb2.setForeground(Color.WHITE);
		
		JButton jb3 = new JButton("SLEEP");
		jb3.setFont(new Font("SLEEP", Font.PLAIN, 50));
		jb3.setBackground(c4);
		jb3.setForeground(Color.WHITE);
		
		JButton jb4 = new JButton("Review NUTRITION");
		jb4.setFont(new Font("Review NUTRITION", Font.PLAIN, 18));
		jb4.setBackground(c4);
		jb4.setForeground(Color.WHITE);
		
		JButton jb5 = new JButton("Review EXERCISE");
		jb5.setFont(new Font("Review EXERCISE", Font.PLAIN, 18));
		jb5.setBackground(c4);
		jb5.setForeground(Color.WHITE);
		
		JButton jb6 = new JButton("Review SLEEP");
		jb6.setFont(new Font("Review SLEEP", Font.PLAIN, 18));
		jb6.setBackground(c4);
		jb6.setForeground(Color.WHITE);
        
        jb1.setPreferredSize(new Dimension(400, 400));
		jb2.setPreferredSize(new Dimension(400, 400));
		jb3.setPreferredSize(new Dimension(400, 400));
		
		jb4.setPreferredSize(new Dimension(400, 50));
		jb5.setPreferredSize(new Dimension(400, 50));
		jb6.setPreferredSize(new Dimension(400, 50));
        
        //Add buttons to the experiment layout
		p1.add(jb1);
		p1.add(jb2);
		p1.add(jb3);
        
		p2.add(jb4);
		p2.add(jb5);
		p2.add(jb6);
  
        //Left to right component orientation is selected by default
        p1.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        
        p2.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        
        JTextField jtf1 = new JTextField("Welome Home");
        jtf1.setEnabled(false);
        jtf1.setFont(new Font("Welome Home", Font.PLAIN, 30));
        
        
        JPanel jpt = new JPanel();
        jpt.setForeground(Color.BLACK);
        jpt.add(jtf1, BorderLayout.CENTER);
        
        board.add(p1, BorderLayout.CENTER);
        board.add(p2, BorderLayout.AFTER_LINE_ENDS);
        
        board.setBackground(c3);
        window.add(board, BorderLayout.CENTER);
        window.add(jpt, BorderLayout.SOUTH);
        
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 500, (int)window.getSize().getHeight() + 600);
		//Testing for window sizes.
		//In my opinion, just saying pack makes it too compact
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public void toLogIn() {
		//Makes log in page
		//Was experimenting with Grid bag Layout
		//Actually turned out pretty good

		if(window != null) {
			window.dispose();
			//If window isn't null, meaning it came from another window, get rid of it
		}
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//Make a panel for grid bag layout

		JLabel uLabel = new JLabel("Username: ");

		c.fill = GridBagConstraints.HORIZONTAL;
		//Have it fill the entire horizontal of the grid
		c.weightx = 0;
		//Make it a thinner column than others
		c.gridx = 0;
		c.gridy = 0;
		//Set position
		pane.add(uLabel, c);

		JTextField uName = new JTextField();
		c.weightx = 1;
		//Have a wider column
		c.gridx = 0;
		c.gridy = 1;
		pane.add(uName, c);

		JLabel pLabel = new JLabel("Password: ");
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(pLabel, c);

		JTextField pWord = new JTextField();
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		pane.add(pWord, c);

		JButton createAcct = new JButton("Create Account...");
		createAcct.setPreferredSize(new Dimension(200,30));

		createAcct.addActionListener(this);
		c.fill = GridBagConstraints.NONE;
		//Don't worry about filling the column
		//If set to horizontal, all buttons would be connected
		//I prefer a gap
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		pane.add(createAcct, c);

		JButton lostPW = new JButton("Forgot Password");
		lostPW.setPreferredSize(new Dimension(200,30));

		lostPW.addActionListener(this);
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 5;
		pane.add(lostPW, c);

		JButton logIn = new JButton("Log In");
		logIn.setPreferredSize(new Dimension(200,30));

		logIn.addActionListener(this);
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 6;
		pane.add(logIn, c);

		window = new JFrame("Log In");
		window.add(pane);
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 80, (int)window.getSize().getHeight() + 20);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public void toCalendar() {
		window = makeWindow();
<<<<<<< HEAD

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
		// window.setSize((int)window.getSize().getWidth() + 50,
		// (int)window.getSize().getHeight() + 50);
		window.setSize((int) window.getSize().getWidth() + 100, (int) window.getSize().getHeight() + 100);
		window.setVisible(true);
	}

	public void toDay(Date day) {
		window.dispose();
		window = makeWindow();

		File file = new File("workout.csv");
		int row = 0;
		ArrayList<String[]> arr = new ArrayList<String[]>();
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String string = input.nextLine();
				String[] temp = new String[6];

				String[] str = string.split(",");
				for (int i = 0; i < temp.length; i++) {
					temp[i] = str[i];
				}
				arr.add(temp);
				System.out.println(temp[1]);
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		JTable data = new JTable(row, 4);
		for (int i = 0; i < data.getRowCount(); i++) {
			for (int j = 0; j < data.getColumnCount(); j++) {
				String str = arr.get(i)[5];
				String d = str.substring(0, 10);
				System.out.println(d);
				if (day.toString().contains(d)) {
					data.setValueAt(arr.get(i)[j + 1], i, j);
				}
			}
			// if (data.getValueAt(i, 0).equals(null)) {
			//
			// data.removeRowSelectionInterval(i, i);
			// }
		}

		window.add(data, BorderLayout.CENTER);
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 50, (int) window.getSize().getHeight() + 50);
		window.setVisible(true);
=======

		JTable cal = new JTable(4, 7);
		//Display an empty 28 day calendar

		window.add(cal, BorderLayout.CENTER);
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 50, (int)window.getSize().getHeight() + 50);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public void addWorkoutWindow() {
		window = new JFrame("Select a Type");
		
		JPanel comboBoxPane = new JPanel(); //use FlowLayout
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(cb);

		JButton j1 = new JButton("Confirm");
	
		j1.addActionListener(this);
		
		//Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(j1);

		//Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(card1, ex1);
		cards.add(card1, ex2);
		cards.add(card1, ex3);
		cards.add(card1, ex4);
		
		window.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
		window.getContentPane().add(cards, BorderLayout.CENTER);
		
		
		window.setLocationRelativeTo(null);
		
		//Display the window.
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 100, (int)window.getSize().getHeight() + 30);
		window.setVisible(true);		
	}
	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, (String)evt.getItem());
	}

	public void showAddWorkoutDialog() {

		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		Object[] message = {
				"Name of Workout", field1, 
				"Duration", field2,
				"Your Weight", field3,
		};
		int opt = JOptionPane.showConfirmDialog(null, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		if (opt == JOptionPane.OK_OPTION)
		{
			window.dispose();
		}
>>>>>>> master
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Log In")) {
<<<<<<< HEAD
			if(LogIn.validate(window)) {
				toHome();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Incorrect Username/Password", "Failed Login", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getActionCommand().equals("Create Account...")) {
			window = AcctCreator.createAcctWindow(window, this);
		} else if (e.getActionCommand().equals("Home")) {
=======
			toHome();
			System.out.println("Logged In");
		} else if(e.getActionCommand().equals("Home")) {
>>>>>>> master
			toHome();
			System.out.println("HOME");
		} else if(e.getActionCommand().equals("Log Out")) {
			window.dispose();
			toLogIn();
			System.out.println("Logged Out");
		} else if(e.getActionCommand().equals("View Calendar")) {
			toCalendar();
			System.out.println("View Cal");
<<<<<<< HEAD
		} else if (e.getActionCommand().equals("Date selected")) {
			System.out.println((Date)datePanel.getModel().getValue());
			// Call toDay if the selected day already has been selected. (or the selected day is today)
			if (day.equals((Date)datePanel.getModel().getValue())) {
				toDay((Date)datePanel.getModel().getValue());
			}
			day = (Date)datePanel.getModel().getValue();
		} else if(e.getActionCommand().equals("Create Account")) {
			if(AcctCreator.createAcct()) {
				toLogIn();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Account Creation Failed", "Failed Creation", JOptionPane.ERROR_MESSAGE);
			}
=======
		} else if(e.getActionCommand().equals("Add Workout")) {
			//createAndShowGUI();
			addWorkoutWindow();
			System.out.println("Adding Workout");

		} else if (e.getActionCommand().equals("Confirm")) {
			showAddWorkoutDialog();
		} else if(e.getActionCommand().equals("Plan Workout")) {
			//addWorkoutWindow();
			System.out.println("Planning Workout");
>>>>>>> master
		} else {
			System.err.println("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}