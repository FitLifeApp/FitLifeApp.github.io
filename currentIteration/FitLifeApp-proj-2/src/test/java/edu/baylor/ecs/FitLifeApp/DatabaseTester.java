package edu.baylor.ecs.FitLifeApp;

import org.junit.Test;

import edu.baylor.ecs.FLADatabase.DatabaseController;

public class DatabaseTester {
	
	private DatabaseController dbc = DatabaseController.getInstance();
	
	
	@Test
	public void databaseCreateAndConnect() {
		dbc.connectAndCreate();
	}
	
	@Test
	public void databaseCreateWorkoutTable() {
		dbc.connectAndCreate();
		dbc.createWorkoutTable();
	}
	
	@Test
	public void databaseAddAndRemoveWorkout() {
		
		dbc.connectAndCreate();
		dbc.createWorkoutTable();
		
		dbc.addWorkout("benji", new Integer(200), "Sprints", "Cardio", new Double(183.5), new Double(0));
		dbc.deleteWorkout(1);
		
		
	}
	
	@Test
	public void databaseEditWorkout() {
		
		dbc.connectAndCreate();
		dbc.createWorkoutTable();
		
		dbc.addWorkout("benji", new Integer(200), "Sprints", "Cardio", new Double(183.5), new Double(0));
		dbc.editWorkout(1, new Integer(300), "Sprints", "Cardio", new Double(183.5), new Double(10));
		dbc.deleteWorkout(1);
		
		
	}
	
	
	
	
	
	
}
