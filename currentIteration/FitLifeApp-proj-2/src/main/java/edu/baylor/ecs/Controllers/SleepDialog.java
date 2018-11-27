package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Sleep;

public final class SleepDialog {
	private static volatile SleepDialog instance = null;
	private JFrame window;
	private SleepController sc = SleepController.getInstance();
	
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

		/*Sets up a JSpinner for time*/
		SpinnerDateModel sdm = new SpinnerDateModel();
		JSpinner timeSpinner = new JSpinner(sdm);
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		
		Object[] message = { "Duration", field1, "Rating", field2, "Start Time", timeSpinner};
		
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		
		
		
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			//convert date and time to just time
			SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
			sdf.setTimeZone(TimeZone.getDefault());
			String da = sdf.format(timeSpinner.getValue());
			System.out.println(timeSpinner.getValue().toString());
			Sleep aSleep = new Sleep(Double.valueOf(field1.getText()),
			Integer.valueOf(field2.getText()), Time.valueOf(da));
			
			sc.add(Account.getuName(), aSleep, day);
	
			Double duration = Double.valueOf(field1.getText());
			Integer rating = Integer.valueOf(field2.getText());
			Time startTime = Time.valueOf(da);
	
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
