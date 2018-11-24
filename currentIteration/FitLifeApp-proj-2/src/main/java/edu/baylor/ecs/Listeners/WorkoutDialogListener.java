package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.baylor.ecs.Controllers.CalendarWindow;

public class WorkoutDialogListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		CalendarWindow cd = CalendarWindow.getInstance();
		if(e.getActionCommand().equals("Confirm")) {
			try {
				cd.getAddworkout().addWorkoutDialog();
				//cd.getAddworkout().destroy();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
