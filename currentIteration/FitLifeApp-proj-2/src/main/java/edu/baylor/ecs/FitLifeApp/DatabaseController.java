package edu.baylor.ecs.FitLifeApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DatabaseController {
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
	
	

}
