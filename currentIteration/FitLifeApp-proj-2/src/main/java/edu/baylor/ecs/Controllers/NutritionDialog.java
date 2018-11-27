package edu.baylor.ecs.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Meal;

public class NutritionDialog {
	private static volatile NutritionDialog instance = null;
	private JFrame window;	
	private MealController mc = MealController.getInstance();
	private NutritionDialog() {}
	
	public static NutritionDialog getInstance() {
		if(instance == null) {
			synchronized(NutritionDialog.class) {
				if(instance == null) {
					instance = new NutritionDialog();
				}
			}
		}
		return instance;
	}
	
	
	/*
	 * function opens the window to get sleep information from the user
	 */
	public void addMeal(Date day) throws IOException {

		window = new JFrame("Add Meal");

		File file = new File("Meal.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();
		JTextField field5 = new JTextField();

		Object[] message = { "Name", field1, "Carbs", field2, "Fat", field3, "Protein", field4, "Hydration", field5};
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {

			String name = field1.getText();
			Integer carbs = Integer.valueOf(field2.getText());
			Integer fat = Integer.valueOf(field3.getText());
			Integer protein = Integer.valueOf(field4.getText());
			Integer hydration = Integer.valueOf(field5.getText());
			
			
			Meal aMeal = new Meal(carbs, fat, hydration, name, protein);
			mc.add(Account.getuName(), aMeal, day);
			
	
			FileWriter w = new FileWriter(file, true);
			PrintWriter p = new PrintWriter(w);
	
			p.write(Account.getuName() + "," + name + "," + carbs.toString() + "," + fat.toString() + ","
					+ protein.toString() + "," + hydration.toString() +"\n");
			System.out.println(Account.getuName() + "," + name + "," + carbs.toString() + "," + fat.toString() + ","
					+ protein.toString() + "," + hydration.toString() +"\n");
	
			w.close();
			p.close();
		}
	}
	
	
	

}
