package edu.baylor.ecs.FLADatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//make singleton
public final class WorkoutController extends DatabaseController{
	 private static volatile WorkoutController instance = null;
	
	 private WorkoutController(){}
	 
	 public static WorkoutController getInstance() {
		 if(instance == null) {
			 synchronized(WorkoutController.class) {
				 if(instance == null) {
					 instance = new WorkoutController();
				 }
			 }
		 }
		 return instance;
	 }
	
	
	/* Creates the workoutTable that needs to be used for all the users' workouts
	 * This only needs to be created once at the beginning of execution the first time
	 * However it will not break anything if it is run after the table has already been made
	 * */
	public void createTable() {
		String createTableSQL = "CREATE TABLE Workout(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "duration INT NOT NULL, " + "name VARCHAR(255) NOT NULL, "
				+ "type VARCHAR(255) NOT NULL, "+ "userWeight FLOAT NOT NULL, " + "workoutWeight FLOAT NOT NULL, " + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = super.getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			System.out.println(createTableSQL);
			// execute the SQL statement
			statement.execute(createTableSQL);
			System.out.println("Table \"Workout\" is created!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/* Inserts a record into the Workout table
	 * */
	public void add(String username, Integer duration, String name, String type, Double userWeight, Double workoutWeight) {
		String insertTableSQL = "INSERT INTO Workout" + "(userName, duration, name, type, userWeight, workoutWeight) " + "VALUES"
				+ "('"+ username + "'," + duration.intValue() + ",'" + name + "','" + type + "'," + userWeight.doubleValue() + "," + workoutWeight.doubleValue() + ")";
		try (Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			System.out.println(insertTableSQL);
			statement.executeUpdate(insertTableSQL);
			System.out.println("Record is inserted into Workout table!");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/*
	 * edits a workout already existing in the workout table by the given id
	 * Doesn't change the username
	 * */
	public void edit(Integer id, Integer duration, String name, String type, Double userWeight, Double workoutWeight) {
			//update the table
			String updateTableSQL = "UPDATE Workout" + " SET duration = "  + duration.intValue() + ", name = '" + name + 
					"', type = '" + type + "', userWeight = " + userWeight.doubleValue() + ", workoutWeight = " + workoutWeight.doubleValue() + 
					" WHERE id = " + id.intValue();
			try (Connection dbConnection = getDBConnection();
					Statement statement = dbConnection.createStatement();){
				
				System.out.println(updateTableSQL);
				statement.execute(updateTableSQL);
				System.out.println("Record is updated to Workout table!");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	
	
	/*
	 * Deletes a record from the Workout table specified by its id
	 * */
	public void delete(Integer id) {
		
		String deleteTableSQL = "DELETE FROM Workout WHERE id = " + id.intValue();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
		
			System.out.println(deleteTableSQL);
			statement.execute(deleteTableSQL);
			System.out.println("Record is deleted from Workout table!");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/* This function selects all records from the meal table	
	 * 
	 */
	public void selectAll() {
		String deleteTableSQL = "SELECT * FROM Workout";
		try ( Connection dbConnection = getDBConnection();
			  Statement statement = dbConnection.createStatement();){
			
			System.out.println(deleteTableSQL);
			ResultSet rs = statement.executeQuery(deleteTableSQL);
			System.out.println("Record selected from Workout table!");
			
			//loops through and return as a list of strings
			List<String[]> row = new ArrayList<String[]>();
			if(rs.next() == false) {
				System.out.println("Result set is empty in java.");
			}else {
				do {
					
					
					
				}while(rs.next());
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
