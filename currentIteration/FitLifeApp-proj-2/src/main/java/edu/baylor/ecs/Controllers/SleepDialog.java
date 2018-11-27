package edu.baylor.ecs.Controllers;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Sleep;

public final class SleepDialog {
	private static volatile SleepDialog instance = null;
	private JFrame window;
	private SleepController sc = SleepController.getInstance();
	private String rating = null;
	private final String ex1 = "0";
	private final String ex2 = "1";
	private final String ex3 = "2";
	private final String ex4 = "3";
	private final String ex5 = "4";
	private final String ex6 = "5";
	private final String ex7 = "6";
	private final String ex8 = "7";
	private final String ex9 = "8";
	private final String ex10 = "9";
	
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

		window = new JFrame("Add Sleep");

		File file = new File("Sleep.csv");
		JTextField field1 = new JTextField();

		/*Sets up a JSpinner for time*/
		SpinnerDateModel sdm = new SpinnerDateModel();
		JSpinner timeSpinner = new JSpinner(sdm);
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		
		/*Setup a JComboBox for the */
		String comboBoxItems[] = { ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		setRating(String.valueOf(cb.getSelectedItem()));
		cb.setEditable(false);
	
		Object[] message = { "Duration", field1, "Rating", cb, "Start Time", timeSpinner};
		
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		
		
		
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			
			//convert date and time to just time
			SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
			sdf.setTimeZone(TimeZone.getDefault());
			String da = sdf.format(timeSpinner.getValue());
			Double duration = null;
			
			
			try {
				/*create sleep object and add it to the database*/
				Sleep aSleep = new Sleep(Double.valueOf(field1.getText()),
				Integer.valueOf(cb.getSelectedItem().toString()), Time.valueOf(da));
				
				/*test integrity of the entered value*/
				duration = Double.valueOf(field1.getText());
				if(duration.compareTo(Double.valueOf("0.1")) < 0 || duration.compareTo(Double.valueOf("23.9")) > 0) {
					throw new NumberFormatException();
				}
				
				sc.add(Account.getuName(), aSleep, day);
			}catch(NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid entry for Duration.\nMust be a number from 0.1 to 23.9", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Integer rating = Integer.valueOf(cb.getSelectedItem().toString());
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

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
}
