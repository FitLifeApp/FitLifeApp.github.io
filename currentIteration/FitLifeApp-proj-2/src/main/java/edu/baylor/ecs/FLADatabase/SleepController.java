package edu.baylor.ecs.FLADatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public final class SleepController extends DatabaseController{
	
	
	private static volatile SleepController instance = null;
	
	 private SleepController(){}
	 
	 public static SleepController getInstance() {
		 if(instance == null) {
			 synchronized(SleepController.class) {
				 if(instance == null) {
					 instance = new SleepController();
				 }
			 }
		 }
		 return instance;
	 }
	
	/* Creates the Meal Table that needs to be used for all the users' Meals
	 * This only needs to be created once at the beginning of execution the first time
	 * However it will not break anything if it is run after the table has already been made
	 * */
	public void createTable() {
		String createTableSQL = "CREATE TABLE Sleep(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "duration FLOAT NOT NULL, " + " rating INT NOT NULL, "+ "startTime TIME NOT NULL," + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = super.getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			System.out.println(createTableSQL);
			statement.execute(createTableSQL);
			System.out.println("Table \"Sleep\" is created!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/* Inserts a record into the Sleep table
	 * */
	public void add(String username, Double duration, Integer rating, Time startTime) {
		String insertTableSQL = "INSERT INTO Sleep" + "(userName, duration, rating, startTime, fat, protein, hydration) " + "VALUES"
				+ "('"+ username + "'," + duration.doubleValue() + ",'" + rating.intValue() + "'," + startTime + ")";
		try (Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			System.out.println(insertTableSQL);
			statement.executeUpdate(insertTableSQL);
			System.out.println("Record is inserted into Sleep table!");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/*
	 * edits a meal already existing in the Sleep table by the given id
	 * Doesn't change the username
	 * */
	public void edit(Integer id, Double duration, Integer rating, Time startTime) {
			//update the table
			String updateTableSQL = "UPDATE Sleep" + " SET duration = "  + duration.doubleValue() + ", rating = '" + rating.intValue() + 
					"', startTime = " + startTime + " WHERE id = " + id.intValue();
			try (Connection dbConnection = getDBConnection();
					Statement statement = dbConnection.createStatement();){
				
				System.out.println(updateTableSQL);
				statement.execute(updateTableSQL);
				System.out.println("Record is updated to Sleep table!");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	
	
	
	/*
	 * Deletes a record from the Sleep table specified by its id
	 * */
	public void delete(Integer id) {
		
		String deleteTableSQL = "DELETE FROM Sleep WHERE id = " + id.intValue();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
		
			System.out.println(deleteTableSQL);
			statement.execute(deleteTableSQL);
			System.out.println("Record is deleted from Sleep table!");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
