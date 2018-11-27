package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class NutritionDialog {
	private static volatile NutritionDialog instance = null;
	private JFrame window;	
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
		
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,15))); 

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
			
			// Creates a meal which can use the add method of NutritionController to enter it
			// into the database
			// We need to store the username somewhere though
			//Meal aMeal = new Meal(carbs, fat, hydration, name, protein);
			
	
			FileWriter w = new FileWriter(file, true);
			PrintWriter p = new PrintWriter(w);
	
			p.write("fu" + "," + name + "," + carbs.toString() + "," + fat.toString() + ","
					+ protein.toString() + "," + hydration.toString() +"\n");
			System.out.println("fu" + "," + name + "," + carbs.toString() + "," + fat.toString() + ","
					+ protein.toString() + "," + hydration.toString() +"\n");
	
			w.close();
			p.close();
		}
	}
	
	public void destroy() {
		window.dispose();
	}
}
