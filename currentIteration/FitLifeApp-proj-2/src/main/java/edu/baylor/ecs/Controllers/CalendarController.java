package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FLADatabase.WorkoutController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Meal;
import edu.baylor.ecs.FitLifeApp.Sleep;
import edu.baylor.ecs.FitLifeApp.Workout;
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
	private final WorkoutController  wc = WorkoutController.getInstance();
	private final MealController mc = MealController.getInstance();
	private final SleepController sc = SleepController.getInstance();
	
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
		

		/*if window already exists, destroy it*/

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

	public void destroyWindow() {
		window.dispose();
	}
	
	
	public void toDay(Date day) {
		
		if(window != null) {
			window.dispose();
		}
		
		window = new JFrame("Review Workout");
		

		ArrayList<String[]> arr = new ArrayList<String[]>();
		String[] temp;
		int row;
		switch(getMode().intValue()) {
			
			case 0: 
				//Query from Workout table
				List<Workout> workouts = new ArrayList<Workout>();
				workouts = wc.select(Account.getuName(), day);
				String[] header1 = {"Name", "Type", "User Weight", "Workout Weight", "Duration"};
				arr.add(header1);
				temp = new String[5];
				for(Workout x : workouts) {
					temp[0] = x.getName();
					temp[1] = x.getType();
					temp[2] = x.getUserWeight().toString();
					temp[3] = x.getWorkoutWeights().toString();
					temp[4] = x.getDuration().toString();
					arr.add(temp);
				}
				row = arr.size();
				break;
			case 1: 
				//Query from Meal table
				List<Meal> meals = new ArrayList<Meal>();
				String[] header2 = {"Name", "Carbs", "Protein", "Fat", "Calories", "Hydration"};
				arr.add(header2);
				meals = mc.select(Account.getuName(), day);
				temp = new String[6];
				for(Meal x: meals) {
					temp[0] = x.getName();
					temp[1] = x.getCarbs().toString();
					temp[2] = x.getProtein().toString();
					temp[3] = x.getFat().toString();
					temp[4] = x.getCalories().toString();
					temp[5] = x.getHydration().toString();
					arr.add(temp);
				}
				row = arr.size();
				break;
			case 2: 
				//Query from Sleep table
				List<Sleep> sleeps = new ArrayList<Sleep>();
				sleeps = sc.select(Account.getuName(), day);
				String[] header3 = {"Duration", "Rating", "Start Time"};
				arr.add(header3);
				temp = new String[3];
				for(Sleep x : sleeps) {
					temp[0] = x.getDuration().toString();
					temp[1] = x.getRating().toString();
					temp[2] = x.getStartTime().toString();
				}
				row = arr.size();
				break;
			default: return;
		}
		
		
		

		// Not use header yet
		// String header[] = {"Name","Your weight","Lift weight","Duration"};
		JTable data = new JTable(row, temp.length);

		// Only add the corresponding data to the table (No more write space caused by
		// hiding part of the data)
		for (int i = 0; i < arr.size(); i++) {
			for (int j = 0; j < data.getColumnCount(); j++) {
				data.setValueAt(arr.get(i)[j], i, j);
			}
		}

		// Sorter for the table
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(data.getModel());
		data.setRowSorter(sorter);

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
				} else {
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

