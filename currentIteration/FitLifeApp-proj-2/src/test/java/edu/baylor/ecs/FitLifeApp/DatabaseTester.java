package edu.baylor.ecs.FitLifeApp;

import org.junit.Test;

public class DatabaseTester {
	
	private DatabaseController dbc = DatabaseController.getInstance();
	
	
	@Test
	public void databaseConnect() {
		dbc.connect();
	}
	
	
	
	
	
}
