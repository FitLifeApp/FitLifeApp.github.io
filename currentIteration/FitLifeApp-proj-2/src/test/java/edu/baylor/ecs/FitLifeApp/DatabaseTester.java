package edu.baylor.ecs.FitLifeApp;

import org.junit.Test;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FLADatabase.WorkoutController;

public class DatabaseTester {
	
	private MealController mc = MealController.getInstance();
	private SleepController sc = SleepController.getInstance();
	private WorkoutController wc = WorkoutController.getInstance();
	
	
	@Test
	public void databaseCreateAndConnect() {
		mc.connectAndCreate();
		sc.connectAndCreate();
		wc.connectAndCreate();
	}
	
	@Test
	public void databaseCreateWorkoutTable() {
		wc.createTable();
		sc.createTable();
		mc.createTable();
	}
	
	@Test
	public void databaseAddAndRemoveWorkout() {
		databaseCreateWorkoutTable();
		wc.add("benji", new Integer(200), "Sprints", "Cardio", new Double(183.5), new Double(0));
		//get all from 
		wc.delete(1);
	}
	
	@Test
	public void databaseEditWorkout() {
		
		//dbc.connectAndCreate();
		//dbc.createWorkoutTable();
		
		//dbc.addWorkout("benji", new Integer(200), "Sprints", "Cardio", new Double(183.5), new Double(0));
		//dbc.editWorkout(1, new Integer(300), "Sprints", "Cardio", new Double(183.5), new Double(10));
		//dbc.deleteWorkout(1);
		
		
	}
	
	
	
	
	
	
}
