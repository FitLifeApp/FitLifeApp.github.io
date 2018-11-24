package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.baylor.ecs.Controllers.CalendarController;

//Listener for Calendar specific buttons
//Like the home listener, I might have missed some
//Because I'm not familiar with this part of the code
public class CalendarListener implements ActionListener {

	CalendarController calendarWindow = CalendarController.getInstance();

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("View Calendar")) {

			calendarWindow.toCalendar();
			System.out.println("View Cal");
		} else if (e.getActionCommand().equals("Date selected")) {

			System.out.println((Date) calendarWindow.getDatePanel().getModel().getValue());
			// Call toDay if the selected day already has been selected. (or the selected
			// day is today)
			if (calendarWindow.getDay().equals((Date) calendarWindow.getDatePanel().getModel().getValue())) {

				if (calendarWindow.getFlag() == true) {

					//set the day with the day selected
					calendarWindow.setDay((Date) calendarWindow.getDatePanel().getModel().getValue());
					//determine the mode
					
					//Workout mode
					if(calendarWindow.getMode().intValue() == 0) {
						try {
							calendarWindow.showAddWorkoutDialog();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					//Nutrition Mode
					} else if (calendarWindow.getMode().intValue() == 1) {
						try {
							calendarWindow.showAddMealDialog();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					//Sleep mode	
					} else if (calendarWindow.getMode().intValue() == 2) {
							try {
								calendarWindow.showAddSleepDialog();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
					
				//we're in review mode	
				} else {

					calendarWindow.toDay((Date) calendarWindow.getDatePanel().getModel().getValue());
				}
			}
			calendarWindow.setDay((Date) calendarWindow.getDatePanel().getModel().getValue());
		} else if (e.getActionCommand().equals("Plan Workout")) {

			// addWorkoutWindow();
			System.out.println("Planning Workout");
		} else {

			System.err.println("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}
