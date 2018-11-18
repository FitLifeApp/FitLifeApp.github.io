package edu.baylor.ecs.FitLifeApp;

/*
 * File:		LogIn.java
 * Description: Handles the creation of the log in window
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;

public class LogIn {

	final private static String initVector = "thisis 16 chars.";
	final private static String key = "1234567890123456";
	// Used for encryption. Guaranteed unpredictable

	static private JTextField uName; // Used to hold username inputs
	static private JPasswordField pWord; // Used to hold password inputs

	// Listener used for buttons in LogIn window
	static class LogInListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Log In")) {
				if (LogIn.validate()) {
					WindowManager.setAcct(LogIn.getAcct());
					WindowManager.toHome();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Incorrect Username/Password", "Failed Login",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getActionCommand().equals("Create Account")) {
				WindowManager.toAcctCreation();
			} else if (e.getActionCommand().equals("Forgot Password")) {
				WindowManager.toAcctCreation();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static JFrame makeWindow(JFrame window) {
		// Makes log in page
		// Was experimenting with Grid bag Layout
		// Actually turned out pretty good

		if (window != null) {
			window.dispose();
			// If window isn't null, meaning it came from another window, get rid of it
		}
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Make a panel for grid bag layout

		JLabel uLabel = new JLabel("Username: ");
		c.fill = GridBagConstraints.HORIZONTAL;
		// Have it fill the entire horizontal of the grid
		c.weightx = 0;
		// Make it a thinner column than others
		c.gridx = 0;
		c.gridy = 0;
		// Set position
		pane.add(uLabel, c);

		uName = new JTextField();
		c.weightx = 1;
		// Have a wider column
		c.gridx = 1;
		c.gridy = 0;
		pane.add(uName, c);

		JLabel pLabel = new JLabel("Password: ");
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(pLabel, c);

		pWord = new JPasswordField();
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(pWord, c);

		JButton createAcct = new JButton("Create Account");
		createAcct.addActionListener(new LogInListener());
		c.fill = GridBagConstraints.NONE;
		// Don't worry about filling the column
		// If set to horizontal, all buttons would be connected
		// I prefer a gap
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(createAcct, c);

		JButton lostPW = new JButton("Forgot Password");
		lostPW.addActionListener(new LogInListener());
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(lostPW, c);

		JButton logIn = new JButton("Log In");
		logIn.addActionListener(new LogInListener());
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		pane.add(logIn, c);

		window = new JFrame("LogIn");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(pane);
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 80, (int) window.getSize().getHeight() + 20);
		window.setVisible(true);

		return window;
	}

	static boolean validate() {

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

	public static Account getAcct() {

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