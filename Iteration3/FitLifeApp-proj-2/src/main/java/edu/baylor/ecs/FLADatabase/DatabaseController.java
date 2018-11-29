package edu.baylor.ecs.FLADatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.derby.iapi.error.StandardException;


public class DatabaseController {
	protected static final String DB_CONNECTION = "jdbc:derby:FitLifeApp;";
	protected static final String DB_USER = null;
	protected static final String DB_PASSWORD = null;
	
	
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
	
	
	
	/*	This method is used in all queries to get the
	 * 	Connection to the database
	 */
	protected Connection getDBConnection() {
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
