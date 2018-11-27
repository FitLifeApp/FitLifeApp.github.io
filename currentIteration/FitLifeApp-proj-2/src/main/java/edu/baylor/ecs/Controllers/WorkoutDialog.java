package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
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

	public void addWorkout(Date day) throws Exception {
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,15))); 
		
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
		
		JLabel l1 = new JLabel("Workout Type");
		l1.setMaximumSize(new Dimension(2000,30));
		l1.setFont(new Font("Workout Type", Font.PLAIN, 15));
		
		JLabel l2 = new JLabel("Name of Workout");
		l2.setMaximumSize(new Dimension(2000,30));
		l2.setFont(new Font("Name of Workout", Font.PLAIN, 15));
		
		JLabel l3 = new JLabel("Duration");
		l3.setMaximumSize(new Dimension(2000,30));
		l3.setFont(new Font("Duration", Font.PLAIN, 15));
		
		JLabel l4 = new JLabel("Your Weight");
		l4.setMaximumSize(new Dimension(2000,30));
		l4.setFont(new Font("Your Weight", Font.PLAIN, 15));
		
		JLabel l5 = new JLabel("Weight Used");
		l5.setMaximumSize(new Dimension(2000,30));
		l5.setFont(new Font("Weight Used", Font.PLAIN, 15));

		Object[] message = { l1, cb, l2, field1, l3, field2, l4, field3, l5, field4 };
		
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
