package edu.baylor.ecs.FitLifeApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FLADatabase.WorkoutController;

public class DatabaseTester {
	
	private MealController mc = MealController.getInstance();
	private SleepController sc = SleepController.getInstance();
	private WorkoutController wc = WorkoutController.getInstance();
	private Workout w1 = new Workout(Integer.valueOf(20), "sprints", "cardio", Double.valueOf(150.5), Double.valueOf(10));
	private Workout w2 = new Workout(Integer.valueOf(30), "push-ups", "chest", Double.valueOf(151.5), Double.valueOf(45));
	private Workout w3 = new Workout(Integer.valueOf(40), "Bench Press", "chest", Double.valueOf(152.5), Double.valueOf(120));
	private Meal m1 = new Meal(Integer.valueOf(50), Integer.valueOf(10), Integer.valueOf(35), "Chicken and Rice", Integer.valueOf(32));
	private Meal m2 = new Meal(Integer.valueOf(100), Integer.valueOf(20), Integer.valueOf(40), "Steak and Potatoes", Integer.valueOf(64));
	private Meal m3 = new Meal(Integer.valueOf(25), Integer.valueOf(60), Integer.valueOf(15), "Spaghetti", Integer.valueOf(128));
	private Sleep s1 = new Sleep(Double.valueOf(480), Integer.valueOf(5), new Time(9000));
	private Sleep s2 = new Sleep(Double.valueOf(510), Integer.valueOf(9), new Time(10000));
	private Sleep s3 = new Sleep(Double.valueOf(450), Integer.valueOf(3), new Time(8000000));
	
	@Test
	public void databaseCreateAndConnect() {
		mc.connectAndCreate();
		sc.connectAndCreate();
		wc.connectAndCreate();
	}
	
	@Test
	public void databaseTables() {
		wc.createTable();
		sc.createTable();
		mc.createTable();
	}
	
	@Test
	public void addAndRemoveWorkout() {
		List<Workout> entered = new ArrayList<Workout>();
		List<Workout> returned;
		List<Meal> entered2 = new ArrayList<Meal>();
		List<Meal> returned2;
		List<Sleep> entered3 = new ArrayList<Sleep>();
		List<Sleep> returned3;
		
		
		entered.add(w1);
		entered.add(w2);
		entered.add(w3);
		
		entered2.add(m1);
		entered2.add(m2);
		entered2.add(m3);
		
		entered3.add(s1);
		entered3.add(s2);
		entered3.add(s3);
		
		
		databaseTables();
		for (Workout x : entered) {
			wc.add("benji", x.getDuration(), x.getName(), x.getType(), x.getUserWeight(), x.getWorkoutWeights());
		}
		for (Meal x : entered2) {
			mc.add("benji", x.getCalories(), x.getName(), x.getCarbs(), x.getFat(), x.getProtein(), x.getHydration());
		}
		for(Sleep x : entered3) {
			sc.add("benji", x.getDuration(), x.getRating(), x.getStartTime());
		}
		
		returned = wc.selectAll();
		returned2 = mc.selectAll();
		returned3 = sc.selectAll();
		
		for(int i = 0; i < returned.size(); i++) {
			assertEquals(entered.get(i).getDuration().intValue(), returned.get(i).getDuration().intValue());
			assertEquals(entered.get(i).getName(), returned.get(i).getName());
			assertEquals(entered.get(i).getType(), returned.get(i).getType());
			assertEquals(entered.get(i).getUserWeight().toString(), returned.get(i).getUserWeight().toString());
			assertEquals(entered.get(i).getWorkoutWeights().toString(), returned.get(i).getWorkoutWeights().toString());
		}
		
		for(int i = 0; i < returned2.size(); i++) {
			assertEquals(entered2.get(i).getCalories().intValue(), returned2.get(i).getCalories().intValue());
			assertEquals(entered2.get(i).getCarbs().intValue(), returned2.get(i).getCarbs().intValue());
			assertEquals(entered2.get(i).getFat().intValue(), returned2.get(i).getFat().intValue());
			assertEquals(entered2.get(i).getHydration().intValue(), returned2.get(i).getHydration().intValue());
			assertEquals(entered2.get(i).getName(), returned2.get(i).getName());
			assertEquals(entered2.get(i).getProtein().intValue(), returned2.get(i).getProtein().intValue());
		}
		
		for(int i = 0; i < returned3.size(); i++) {
			assertEquals(entered3.get(i).getDuration().toString(), returned3.get(i).getDuration().toString());
			assertEquals(entered3.get(i).getRating().intValue(), returned3.get(i).getRating().intValue());
			assertEquals(entered3.get(i).getStartTime(), returned3.get(i).getStartTime());
		}
		
		
		//get all from 
		wc.deleteAll();
		mc.deleteAll();
		sc.deleteAll();
	}
	
	@Test
	public void deleteAll() {
		List<Workout> returned = new ArrayList<Workout>();
		List<Meal> returned2 = new ArrayList<Meal>();
		List<Sleep> returned3 = new ArrayList<Sleep>();
		wc.deleteAll();
		returned = wc.selectAll();
		assertTrue(returned.size() == 0);
		
		mc.deleteAll();
		returned2 = mc.selectAll();
		assertTrue(returned2.size() == 0);
		
		sc.deleteAll();
		returned3 = sc.selectAll();
		assertTrue(returned3.size() == 0);
	}
	
	
	@Test
	public void deleteDatabases() {
		mc.dropTable();
		sc.dropTable();
		wc.dropTable();
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
