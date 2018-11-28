package edu.baylor.ecs.Controllers;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
			
			Workout aWorkout = new Workout(duration,
					exercise, type, weight,
					weightUsed);
			wc.add(Account.getuName(), aWorkout, day);
	
		}
	}
	
	public void editWorkout(Workout aWorkout){
		window = new JFrame("Edit Workout");
		
		JTextField field1 = new JTextField(aWorkout.getName());
		JTextField field2 = new JTextField(aWorkout.getDuration().toString());
		JTextField field3 = new JTextField(aWorkout.getUserWeight().toString());
		JTextField field4 = new JTextField(aWorkout.getWorkoutWeights().toString());
		
		
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
			
			aWorkout = new Workout(aWorkout.getId(), duration, exercise, type, weight, weightUsed);
			
			wc.edit(aWorkout);
		}

	}

	public void deleteWorkout(Workout aWorkout) {
		window = new JFrame("Delete Workout");
		
		int opt = JOptionPane.showConfirmDialog(window, "Warning!\nYou are about to delete the selected Workout.\nIs this what you want?", "Enter Information", JOptionPane.YES_NO_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.NO_OPTION) {
			
			wc.delete(aWorkout.getId());
	
		}
	}
	
	public void destroy() {
		window.dispose();
	}

}
