package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.WindowManager;

public /*
 * //I assume leftovers JLabel result; String currentPattern;
 * This seems fine but why 
 */
 class BasicActListener implements ActionListener {
	
	WindowManager wm = WindowManager.getInstance();
	
	public void actionPerformed(ActionEvent e) {
		
		//Home button is clicked, spawn home page
		if (e.getActionCommand().equals("Home")) {
			wm.toHome();
			System.out.println("HOME");
			
		//if logout is selected displose of window and spawn log in screen	
		} else if (e.getActionCommand().equals("Log Out")) {
			wm.getWindow().dispose();
			wm.toLogIn();
			System.out.println("Logged Out");
			
		//if view calendar is selected spawn calendar
		} else if (e.getActionCommand().equals("View Calendar")) {

			// Might get removed
			wm.toCalendar();
			System.out.println("View Cal");
			
		//if a date is selected use joption pane	
		} else if (e.getActionCommand().equals("Date selected")) {

			JOptionPane.showMessageDialog(new JFrame(), "\"Date Selected\" in WindowManager/BasicActListener",
					"Failed Login", JOptionPane.ERROR_MESSAGE);
			
		//if the confirm button is hit show the add workout dialog
		} else if (e.getActionCommand().equals("Confirm")) {
			try {
				wm.getCalendarWindow().showAddWorkoutDialog();
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			
		//TO DO: remove code, unneeded
		} else if (e.getActionCommand().equals("Plan Workout")) {
			// addWorkoutWindow();
			System.out.println("Planning Workout");
		} else {
			System.err.println("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}
