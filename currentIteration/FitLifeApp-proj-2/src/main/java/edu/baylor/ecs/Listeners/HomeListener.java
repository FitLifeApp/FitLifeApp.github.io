package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.CalendarWindow;
import edu.baylor.ecs.Controllers.HomePage;

public class HomeListener implements ActionListener {
	

	@Override
	public void actionPerformed(ActionEvent e) {
		HomePage home = HomePage.getInstance();
		if (e.getActionCommand().equals("EXERCISE")) {
			CalendarWindow.setFlag(true);
			home.toCalendar();
			System.out.println("View Cal");
			System.out.println("Adding Workout");

		} else if (e.getActionCommand().equals("Review EXERCISE")) {
			CalendarWindow.setFlag(false);
			home.toCalendar();
			System.out.println("View Cal");
			System.out.println("Reviewing Workout");

		} else if (e.getActionCommand().equals("Log Out")) {
			home.destory();
			home.toLogIn();
			System.out.println("Logged Out");
		//if view calendar is selected spawn calendar
		} else {
			//If this occurs, I missed a button somewhere
			JOptionPane.showMessageDialog(new JFrame(), "Hmm, more non-existant buttons...\nFind them all for a prize at the end!",
					"Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
}
