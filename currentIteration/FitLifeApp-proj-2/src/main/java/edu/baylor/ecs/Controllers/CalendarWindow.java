package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import edu.baylor.ecs.Listeners.CalendarListener;

//Not sure where exactly, but there is the issue of also showing the menu when opening the calendar
//So you could swap to another screen while looking at the calendar.
//Might cause file problems, might cause other problems


public final class CalendarWindow extends WindowManager{
	static boolean flag = false;
	private  JDatePanelImpl datePanel = null;
	private Date day = java.util.Calendar.getInstance().getTime();
	private JFrame window = null;

	private static volatile CalendarWindow instance = null;
	
	private CalendarWindow() {}
	
	public static CalendarWindow getInstance() {
		if (instance == null) {
			synchronized(CalendarWindow.class) {
				if(instance == null) {
					instance = new CalendarWindow();
				}
			}
		}
		return instance;
	}
//To move flag out of WindowManager, it is put here
	//I'm not sure what flag does, so if it belongs somewhere else
	//Feel free to move it
	
	public boolean getFlag() {
		return flag;
	}

	public static void setFlag(boolean flag) {
		CalendarWindow.flag = flag;
	}
	
	
	//Copied from toCalendar
	public JFrame makeWindow() {
		
		window = new JFrame("Calendar");
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		setDatePanel(new JDatePanelImpl(new UtilDateModel(), p));

		getDatePanel().setShowYearButtons(true);
		getDatePanel().setEnabled(true);
		getDatePanel().setFocusable(true);
		getDatePanel().addActionListener(new CalendarListener());
			//This listener is larger than it needs to be because I don't know what is checked

		window.add(getDatePanel(), BorderLayout.CENTER);	
		
		window.setMaximumSize(new Dimension(500,250));
		window.setPreferredSize(new Dimension(500,250));
		window.setMinimumSize(new Dimension(500,250));
		window.setLocationRelativeTo(null);
		
		window.pack();
		window.setVisible(true);
		return window;
		
	}


	public void showAddWorkoutDialog() throws Exception {
		File file = new File("workout.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		Object[] message = { "Name of Workout", field1, "Duration", field2, "Your Weight", field3, };
		JOptionPane.showConfirmDialog(new JFrame(), message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		String exercise = field1.getText();
		String duration = field2.getText();
		String weight = field3.getText();
		FileWriter w = new FileWriter(file, true);
		PrintWriter p = new PrintWriter(w);
		p.write("fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		System.out.println(
				"fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		w.close();
		p.close();

		//I have no idea what this is for
		//Strictly speaking, I have no idea how most of this works
		/*
		if (opt == JOptionPane.OK_OPTION) {
			window.dispose();
		}
		*/
	}
	
	public void destroyWindow() {
		window.dispose();
	}


	public JDatePanelImpl getDatePanel() {
		return datePanel;
	}
	
	private void setDatePanel(JDatePanelImpl jDatePanelImpl) {
		this.datePanel = jDatePanelImpl;
		
	}
	
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}
	
}

