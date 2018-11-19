package edu.baylor.ecs.FitLifeApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public final class LogIn {

	private WindowManager wm = WindowManager.getInstance();
	
	final private static String initVector = "thisis 16 chars.";
	final private static String key = "1234567890123456";
	// Used for encryption. Guaranteed unpredictable

	static private JTextField uName; // Used to hold username inputs
	static private JPasswordField pWord; // Used to hold password inputs
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int screenWidth = screenSize.width-100;
	private static int screenHeight = screenSize.height-100;
	private static Account acct = new Account();
	
	
	
	/*The singleton itself*/
	private static volatile LogIn instance = null;
	
	private LogIn() {}
	
	/*Singleton method to get or create the LogIn*/
	public static LogIn getInstance() {
		if(instance == null) {
			synchronized(LogIn.class) {
				if(instance == null) {
					instance = new LogIn();
				}
			}
		}
		return instance;
	}

	// Listener used for buttons in LogIn window
	public class LogInListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Sign In")) {
				if (instance.validate()) {
					wm.setAcct(instance.getAcct());
					wm.toHome();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Incorrect Username/Password", "Failed Login",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getActionCommand().equals("Create Account")) {
				wm.toAcctCreation();
			} else if (e.getActionCommand().equals("Forgot Password")) {
				wm.toAcctCreation();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public JFrame makeWindow(JFrame window) {
		// Makes log in page
		// Was experimenting with Grid bag Layout
		// Actually turned out pretty good

		if (window != null) {
			window.getContentPane().removeAll();
			//window.dispose();
			// If window isn't null, meaning it came from another window, get rid of it
		}
		
		//window = new JFrame("Welcome");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		
		window.setLayout(flowLayout);
	    
		Box p1 = new Box(BoxLayout.Y_AXIS);
		p1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		p1.setMaximumSize(new Dimension(2000,150));
		
		Box p2 = new Box(BoxLayout.Y_AXIS);
		p2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		p2.setMaximumSize(new Dimension(2000,150));
		
		Box p3 = new Box(BoxLayout.Y_AXIS);
		p3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		p3.setMaximumSize(new Dimension(2000,150));
		
		Box p4 = new Box(BoxLayout.Y_AXIS);
		p4.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		p4.setMaximumSize(new Dimension(2000,150));

		Box board = new Box(BoxLayout.Y_AXIS);
		board.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		JLabel uLabel = new JLabel("Username");
		uLabel.setFont(new Font("Username", Font.PLAIN, 20));
		
		uName = new JTextField();
		uName.setMaximumSize(new Dimension(2000,30));
		uName.setFont(new Font("", Font.PLAIN, 20));
		
		p1.add(uLabel);
		p1.add(uName);
		
		JLabel pLabel = new JLabel("Password");
		pLabel.setFont(new Font("Password", Font.PLAIN, 20));

		pWord = new JPasswordField();
		pWord.setMaximumSize(new Dimension(2000,30));
		pWord.setFont(new Font("", Font.PLAIN, 20));
		
		p2.add(pLabel);
		p2.add(pWord);

		JButton createAcct = new JButton("Create Account");
		createAcct.setFont(new Font("Create Account", Font.PLAIN, 20));
		createAcct.setForeground(Color.white);
		createAcct.setBackground(new Color( 44, 62, 80 ));
		createAcct.setMaximumSize(new Dimension(2000,50));
		createAcct.addActionListener(new LogInListener());
		
		JButton lostPW = new JButton("Forgot Password");
		lostPW.setFont(new Font("Forgot Password", Font.PLAIN, 20));
		lostPW.setForeground(Color.white);
		lostPW.setBackground(new Color( 44, 62, 80 ));
		lostPW.setMaximumSize(new Dimension(2000,50));
		lostPW.addActionListener(new LogInListener());

		JButton logIn = new JButton("Sign In");
		logIn.setFont(new Font("Sign In", Font.PLAIN, 20));
		logIn.setForeground(Color.white);
		logIn.setBackground(new Color( 44, 62, 80 ));
		logIn.setMaximumSize(new Dimension(2000,50));
		logIn.addActionListener(new LogInListener());
		
		p3.add(createAcct);
		p3.add(Box.createVerticalStrut(5));
		p3.add(lostPW);
		p3.add(Box.createVerticalStrut(5));
		p3.add(logIn);

		board.add(Box.createVerticalStrut(screenHeight/2-250));
		
		JLabel welcome = new JLabel("Welcome to FitLife");	
		welcome.setFont(new Font("Welcome to FitLife", Font.PLAIN, 50));
		
		p4.add(welcome);
				
		board.add(p4);
		board.add(Box.createVerticalStrut(40));
		board.add(p1);
		board.add(Box.createVerticalStrut(10));
		board.add(p2);
		board.add(Box.createVerticalStrut(10));
		board.add(p3);
		
		window.add(board);
		window.getContentPane().setBackground(new Color(234, 242, 248));
		window.repaint();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		
		//set screen size and make the window spawn in the middle of the screen, regardless the monitor resolution
		window.setSize(new Dimension(screenWidth, screenHeight));
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		return window;
	}

	boolean validate() {

		BufferedReader br = null;
		Scanner scnr = null;
		String fileContents = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
			scnr = new Scanner(br).useDelimiter("\\Z");
			// But I say close?
			fileContents = scnr.next();
		} catch (FileNotFoundException e1) {

			JOptionPane.showMessageDialog(new JFrame(), "No accounts were found", "Dialog", JOptionPane.ERROR_MESSAGE);

			return false;

		} finally {
			if (scnr != null) {
				scnr.close();
			}
		}
		
		System.out.println("ENC LOGIN: " + fileContents);
		fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");
		System.out.println("DEC LOGIN: " + fileContents);
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(fileContents.split("\n")));

		boolean isTrue = false;

		for (int i = 0; i < lines.size() && !isTrue; i++) {
			String[] acct;
			acct = lines.get(i).split(",");
			if (acct.length >= 2) {
				if (uName.getText().equals(acct[0]) && Arrays.equals(pWord.getPassword(), acct[1].toCharArray())) {
					isTrue = true;
				}
			}
		}

		return isTrue;

	}

	public Account getAcct() {

		boolean gotID = false;
		int id = -1;
		Account a = null;

		
		BufferedReader br = null;
		Scanner scnr = null;
		String fileContents = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
			scnr = new Scanner(br).useDelimiter("\\Z");
			// But I say close?
			fileContents = scnr.next();
		} catch (FileNotFoundException e1) {

			JOptionPane.showMessageDialog(new JFrame(), "No accounts were found. This shouldn't happen",
					"Dialog", JOptionPane.ERROR_MESSAGE);

			return null;

		} finally {
			if (scnr != null) {
				scnr.close();
			}
		}
		
		System.out.println("ENC getAcct: " + fileContents);
		fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");
		System.out.println("DEC getAcct: " + fileContents);
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(fileContents.split("\n")));

		
		for(int i = 0; i < lines.size() && !gotID; i++) {
			String[] line;
			line = lines.get(i).split(",");
			if (line.length >= 3) {
				if (uName.getText().equals(line[0])) {
					gotID = true;
					id = Integer.parseInt(line[2]);
				}
			}
		}
		
		


		if (gotID && id != -1) {
			File acct = new File("ACCT" + Integer.toString(id));
			try {
				
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(acct)));
					scnr = new Scanner(br).useDelimiter("\\Z");
					// But I say close?
					fileContents = scnr.next();
				} catch (FileNotFoundException e1) {

					JOptionPane.showMessageDialog(new JFrame(), "No accounts were found. This "
							+ " REALLY shouldn't happen",
							"Dialog", JOptionPane.ERROR_MESSAGE);

					return null;

				} finally {
					if (scnr != null) {
						scnr.close();
					}
				}
				
				fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");
				StringReader strReader = new StringReader(fileContents);
				
				
				JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				a = (Account) jaxbUnmarshaller.unmarshal(strReader);
				strReader.close();
			} catch (JAXBException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Error unmarshalling", "Dialog", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Could not find account or no associated ID", "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}

		return a;

	}
}