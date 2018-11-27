
package edu.baylor.ecs.Controllers;

/*
 * File:		WindowManager.java
 * Description: Handles the base creation of windows while logged in
 * 				As well as swapping between windows
 */

import javax.swing.*;
import edu.baylor.ecs.FitLifeApp.Account;


public class WindowManager {

	// -----------------------------------------

	private static final LogInController login = LogInController.getInstance();
	private static final AcctCreator acctCreator = AcctCreator.getInstance();
	private static final CalendarController calendarWindow = CalendarController.getInstance();
	private static final HomePageController home = HomePageController.getInstance();

	// -----------------------------------------
	private JFrame window;

	private Account acct;


	private static volatile WindowManager instance = null;

	/* singleton constructor */
	protected WindowManager() {
		acct = null;
		window = new JFrame("Welcome");
	}

	public static WindowManager getInstance() {
		if (instance == null) {
			synchronized (WindowManager.class) {
				if (instance == null) {
					instance = new WindowManager();
				}
			}
		}
		return instance;
	}

	//// Handles Base construction of frame
	// Constructs a frame with a menu bar with various pages
	// This functionality is used by many, maybe it should just be inherited

	// sets the Jframe window to the Jframe returned by makeWindow with window
	// passed to it
	public void toHome() {
		home.makeWindow(acct);
	}

	// Makes log in window
	public void toLogIn() {
		login.makeWindow();
	}

	// makes account creation window
	public void toAcctCreation() {
		acctCreator.makeWindow();
	}

	// makes Calendar window
	public void toCalendar() {
		// same stuff for calendar window
		calendarWindow.makeWindow();

	}


	// Also not yet moved
	public void addWorkoutWindow() {

	}

	public void setAcct(Account src) {
		// TODO Auto-generated method stub
		System.out.println(src.toString());
		acct = src;
	}
	
	public Account getAcct() {
		return acct;
	}
	
	

	public CalendarController getCalendarWindow() {
		return calendarWindow;
	}

	public JFrame getWindow() {
		return window;
	}

}