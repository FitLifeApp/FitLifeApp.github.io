package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import java.util.Properties;
import javax.swing.JFrame;


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import edu.baylor.ecs.Listeners.CalendarListener;

//Not sure where exactly, but there is the issue of also showing the menu when opening the calendar
//So you could swap to another screen while looking at the calendar.
//Might cause file problems, might cause other problems


public final class CalendarController extends WindowManager{
	
	static boolean flag = false;
	private  JDatePanelImpl datePanel = null;
	private Date day = java.util.Calendar.getInstance().getTime();
	private JFrame window = null;
	private static Integer mode = null;
	private static final WorkoutDialog addworkout = WorkoutDialog.getInstance();
	private static final SleepDialog addSleep = SleepDialog.getInstance();
	private static final NutritionDialog addMeal = NutritionDialog.getInstance();
	
	private static volatile CalendarController instance = null;
	
	private CalendarController() {}
	
	
	
	public static CalendarController getInstance() {
		if (instance == null) {
			synchronized(CalendarController.class) {
				if(instance == null) {
					instance = new CalendarController();
				}
			}
		}
		return instance;
	}
	
	
	public boolean getFlag() {
		return flag;
	}

	public static void setFlag(boolean flag) {
		CalendarController.flag = flag;
	}
	
	
	//Copied from toCalendar
	public void makeWindow() {
		if(window != null) {
			window.dispose();
		}
		
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
	}
	
	

	public void showAddWorkoutDialog() throws Exception {
		addworkout.addWorkout(day);
	}
	
	public void showAddSleepDialog() throws Exception {
		addSleep.addSleep(day);
	}
	
	public void showAddMealDialog() throws Exception {
		addMeal.addMeal(day);
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
	
	public WorkoutDialog getAddworkout() {
		return addworkout;
	}
	
	public Integer getMode() {
		return mode;
	}

	public static void setMode(Integer m) {
		mode = m;
	}
}

