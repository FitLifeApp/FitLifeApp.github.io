package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public final class SleepDialog {
	private static volatile SleepDialog instance = null;
	private JFrame window;

	private SleepDialog() {
	}

	public static SleepDialog getInstance() {
		if (instance == null) {
			synchronized (SleepDialog.class) {
				if (instance == null) {
					instance = new SleepDialog();
				}
			}
		}
		return instance;
	}

	/*
	 * function opens the window to get sleep information from the user
	 */
	public void addSleep(Date day) throws IOException {
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,15))); 

		window = new JFrame("Add Sleep");

		File file = new File("Sleep.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		Object[] message = { "Duration", field1, "Rating", field2, "Start Time", field3 };
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			// Creates a sleep which can use the add method of SleepController to enter it
			// into the database
			// We need to store the username somewhere though
			// Sleep aSleep = new Sleep(Double.valueOf(field1.getText()),
			// Integer.valueOf(field2.getText()), Time.valueOf(field3.getText()));
	
			Double duration = Double.valueOf(field1.getText());
			Integer rating = Integer.valueOf(field2.getText());
			Time startTime = Time.valueOf(field3.getText());
	
			FileWriter w = new FileWriter(file, true);
			PrintWriter p = new PrintWriter(w);
	
			p.write("fu" + "," + duration.toString() + "," + rating.toString() + "," + startTime.toString() + ","
					+ day.toString() + "\n");
			System.out.println("fu" + "," + duration.toString() + "," + rating.toString() + "," + startTime.toString() + ","
					+ day.toString() + "\n");
	
			w.close();
			p.close();
		}
	}
	
	public void destroy() {
		window.dispose();
	}
}
