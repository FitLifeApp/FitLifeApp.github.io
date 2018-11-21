package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.FitLifeApp.CalendarWindow;
import edu.baylor.ecs.FitLifeApp.HomePage;

public class HomeListener implements ActionListener {
	

	@Override
	public void actionPerformed(ActionEvent e) {
		HomePage home = HomePage.getInstance();
		if (e.getActionCommand().equals("EXERCISE")) {
			//flag = true;
			CalendarWindow.setFlag(true);
			home.toCalendar();
			System.out.println("View Cal");
			System.out.println("Adding Workout");

		} else if (e.getActionCommand().equals("Review EXERCISE")) {
			home.toCalendar();
			CalendarWindow.setFlag(false);
			//flag = false;
			System.out.println("View Cal");
			System.out.println("Adding Workout");

		} else {
			//If this occurs, I missed a button somewhere
			JOptionPane.showMessageDialog(new JFrame(), "Hmm, more non-existant buttons...\nFind them all for a prize at the end!",
					"Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
}
