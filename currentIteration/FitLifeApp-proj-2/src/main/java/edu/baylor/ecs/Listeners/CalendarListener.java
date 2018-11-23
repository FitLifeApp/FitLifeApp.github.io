package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import edu.baylor.ecs.Controllers.AddWorkoutWindow;
import edu.baylor.ecs.Controllers.CalendarWindow;

	//Listener for Calendar specific buttons
	//Like the home listener, I might have missed some
	//Because I'm not familiar with this part of the code
	public class CalendarListener implements ActionListener {
		
		CalendarWindow calendarWindow = CalendarWindow.getInstance();
		AddWorkoutWindow workoutWindow = AddWorkoutWindow.getInstance();
		
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
						calendarWindow.setDay((Date) calendarWindow.getDatePanel().getModel().getValue());
						calendarWindow.destroyWindow();
						workoutWindow.makeWindow();
						
					} else {
						calendarWindow.toDay((Date) calendarWindow.getDatePanel().getModel().getValue());
					}
				}
				calendarWindow.setDay((Date) calendarWindow.getDatePanel().getModel().getValue());
			} else {
				System.err.println("Unhandled Action Command: " + e.getActionCommand());
			}
		}
	}
