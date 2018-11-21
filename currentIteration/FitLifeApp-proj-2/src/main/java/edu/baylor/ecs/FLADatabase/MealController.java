package edu.baylor.ecs.FLADatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//make singleton
public final class MealController extends DatabaseController {
	
	private static volatile MealController instance = null;
	
	 private MealController(){}
	 
	 public static MealController getInstance() {
		 if(instance == null) {
			 synchronized(MealController.class) {
				 if(instance == null) {
					 instance = new MealController();
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
		String createTableSQL = "CREATE TABLE Meal(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "calories INT NOT NULL, " + "name VARCHAR(255) NOT NULL, "
				+ " carbs INT NOT NULL, "+ "fat INT NOT NULL, " + "protein INT NOT NULL, " + "hydration INT NOT NULL, " + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = super.getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			System.out.println(createTableSQL);
			statement.execute(createTableSQL);
			System.out.println("Table \"Meal\" is created!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/* Inserts a record into the Meal table
	 * */
	public void add(String username, Integer calories, String name, Integer carbs, Integer fat, Integer protein, Integer hydration) {
		String insertTableSQL = "INSERT INTO Meal" + "(userName, calories, name, carbs, fat, protein, hydration) " + "VALUES"
				+ "('"+ username + "'," + calories.intValue() + ",'" + name + "'," + carbs.intValue() + "," + fat.intValue() + "," + protein.intValue() + ", " + hydration.intValue() +  ")";
		try (Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			System.out.println(insertTableSQL);
			statement.executeUpdate(insertTableSQL);
			System.out.println("Record is inserted into Meal table!");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/*
	 * edits a meal already existing in the Meal table by the given id
	 * Doesn't change the username
	 * */
	public void edit(Integer id, Integer calories, String name, Integer carbs, Integer fat, Integer protein, Integer hydration) {
			//update the table
			String updateTableSQL = "UPDATE Meal" + " SET calories = "  + calories.intValue() + ", name = '" + name + 
					"', carbs = " + carbs.intValue() + ", fat = " + fat.intValue() + ", protein = " + protein.intValue() + ", hydration = " + hydration.intValue() +
					" WHERE id = " + id.intValue();
			try (Connection dbConnection = getDBConnection();
					Statement statement = dbConnection.createStatement();){
				
				System.out.println(updateTableSQL);
				statement.execute(updateTableSQL);
				System.out.println("Record is updated to Meal table!");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	
	
	
	/*
	 * Deletes a record from the Meal table specified by its id
	 * */
	public void delete(Integer id) {
		
		String deleteTableSQL = "DELETE FROM Meal WHERE id = " + id.intValue();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
		
			System.out.println(deleteTableSQL);
			statement.execute(deleteTableSQL);
			System.out.println("Record is deleted from Meal table!");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
