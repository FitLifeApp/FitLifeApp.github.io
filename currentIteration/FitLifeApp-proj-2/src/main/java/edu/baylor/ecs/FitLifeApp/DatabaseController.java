package edu.baylor.ecs.FitLifeApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.iapi.error.StandardException;


public final class DatabaseController {
	private static final String DB_CONNECTION = "jdbc:derby:FitLifeApp;";
	private static final String DB_USER = null;
	private static final String DB_PASSWORD = null;
	private static volatile DatabaseController instance = null;
	
	private DatabaseController() {}
	
	public static DatabaseController getInstance() {
		if(instance == null) {
			synchronized(DatabaseController.class) {
				if(instance == null) {
					instance = new DatabaseController();
				}
			}
		}
		return instance;
	}
	
	
	/* method is used to connect to and create a database
	 * Realistically this method only needs to be called once at the 
	 * beginning of execution before any login or account creation can be done
	 */
	public void connectAndCreate() {
		
		System.out.println("-------- Derby JDBC Connection Testing ------------");
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Derby JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Derby JDBC Driver Registered!");
		
		try (Connection connection = DriverManager.getConnection("jdbc:derby:FitLifeApp;create=true", "", "");){
			
			if (connection != null) {
				System.out.println("All good!");
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		
	}
	
	/*	Method is used to conenct to the existing database after the connection
	 * 	has been closed.
	 * 
	 */
	public void connect() {
		System.out.println("-------- Derby JDBC Connection Testing ------------");
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Derby JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Derby JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:derby:FitLifeApp;", "", "");
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			if (connection != null) {
				try {
					if (!connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/* Creates the workoutTable that needs to be used for all the users' workouts
	 * This only needs to be created once at the beginning of execution the first time
	 * However it will not break anything if it is run after the table has already been made
	 * */
	public void createWorkoutTable() {
		String createTableSQL = "CREATE TABLE Workout(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "duration INT NOT NULL, " + "name VARCHAR(255) NOT NULL, "
				+ "type VARCHAR(255) NOT NULL, "+ "userWeight FLOAT NOT NULL, " + "workoutWeight FLOAT NOT NULL, " + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = getDBConnection();
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
	public void addWorkout(String username, Integer duration, String name, String type, Double userWeight, Double workoutWeight) {
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
	 * Deletes a record from the Workout table specified by its id
	 * */
	public void deleteWorkout(Integer id) {
		
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
	
	/*
	 * edits a workout already existing in the workout table by the given id
	 * Doesn't change the username
	 * */
	public void editWorkout(Integer id, Integer duration, String name, String type, Double userWeight, Double workoutWeight) {
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
	
	
	
	/*	This method is used in all queries to get the
	 * 	Connection to the database
	 */
	private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			if(e.getCause() instanceof StandardException) {
				System.err.println(e.getCause());
				System.err.println("Most likely connection open already (and new cannot be opened)");
				
			}
			System.exit(0);
		}
		return dbConnection;
	}
	
	
	

}
