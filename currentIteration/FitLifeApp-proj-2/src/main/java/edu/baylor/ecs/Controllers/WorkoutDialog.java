package edu.baylor.ecs.Controllers;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

import edu.baylor.ecs.FLADatabase.WorkoutController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Workout;

public final class WorkoutDialog {

	private static volatile WorkoutDialog instance = null;
	private JFrame window = null;
	private final String ex1 = "Aerobic Exercise";
	private final String ex2 = "Strength Exercise";
	private final String ex3 = "Balance Exercise";
	private final String ex4 = "Flexibility Exercise";
	private WorkoutController wc = WorkoutController.getInstance();

	private WorkoutDialog() {}

	public static WorkoutDialog getInstance() {
		if (instance == null) {
			synchronized (WorkoutDialog.class) {
				if (instance == null) {
					instance = new WorkoutDialog();
				}
			}
		}
		return instance;
	}

	public void addWorkout(Date day) throws IOException {
		
		window = new JFrame("Enter Workout");
		
		File file = new File("workout.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();
		
		
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		
		

		Object[] message = { "Workout Type", cb, "Name of Workout", field1, "Duration", field2, "Your Weight", field3, "Weight Used",
				field4 };
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			
			//creates a workout and adds it to the database
			String type = null, exercise = null;
			Integer duration = null;
			Double weight = null, weightUsed = null;
			try {
				
				type = cb.getSelectedItem().toString();
				exercise = field1.getText();
				duration = Integer.valueOf(field2.getText());
				weight = Double.valueOf(field3.getText());
				weightUsed = Double.valueOf(field4.getText());
				
				if(duration.compareTo(Integer.valueOf(1)) < 0 || duration.compareTo(Integer.valueOf(1440)) > 0 || 
						weight.compareTo(Double.valueOf("4.0")) < 0 || weight.compareTo(Double.valueOf("1400.0")) > 0 || 
						weightUsed.compareTo(Double.valueOf("4.0")) < 0 || weight.compareTo(Double.valueOf("1400.0")) > 0) {
					throw new NumberFormatException();
				}else if(exercise.length() < 4) {
					throw new IllegalArgumentException();
				}
				
			
				Workout aWorkout = new Workout(Integer.valueOf(field2.getText()),
						field1.getText(), cb.getSelectedItem().toString(), Double.valueOf(field3.getText()),
						Double.valueOf(field4.getText()));
				wc.add(Account.getuName(), aWorkout, day);
			}catch(NumberFormatException e){
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\nYour Weight and Weight Used must be a number from 4.0 to 1400.0\nDuration must be an integer from 1 to 1440.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			}catch(IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Not a valid exercise name!.\nMust be 4 or more characters", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
	
			FileWriter w = new FileWriter(file, true);
			PrintWriter p = new PrintWriter(w);
	
			p.write( Account.getuName() + "," + type + "," + exercise + "," + weight + "," + weightUsed + "," +  duration + "," + day.toString() + "\n");
			System.out.println(
					Account.getuName() + "," + type + "," + exercise + "," + weight + "," + weightUsed + "," +  duration + "," + day.toString() + "\n");
	
			w.close();
			p.close();
		}
	}

	public void destroy() {
		window.dispose();
	}

}
