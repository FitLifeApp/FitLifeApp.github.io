package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Meal;

public class NutritionDialog{
	private static volatile NutritionDialog instance = null;
	private JFrame window;	
	private MealController mc = MealController.getInstance();
	private final Integer LOWER = new Integer(0);
	private final Integer UPPER = new Integer(99999);
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

		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();
		JTextField field5 = new JTextField();
		
		

		Object[] message = { "Name", field1, "Carbs", field2, "Fat", field3, "Protein", field4, "Hydration", field5};
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			String name;
			Integer carbs; 
			Integer fat;
			Integer protein;
			Integer hydration;
			
			try {
				name = field1.getText();
				carbs = Integer.valueOf(field2.getText());
				fat = Integer.valueOf(field3.getText());
				protein = Integer.valueOf(field4.getText());
				hydration = Integer.valueOf(field5.getText());
				
				if(name.length() < 4) {
					throw new IllegalArgumentException();
				}else if(carbs.compareTo(LOWER) < 0 || carbs.compareTo(UPPER) > 0 ||
						fat.compareTo(LOWER) < 0 || fat.compareTo(UPPER) > 0 ||
						protein.compareTo(LOWER) < 0 || protein.compareTo(UPPER) > 0 || 
						hydration.compareTo(LOWER) < 0 || hydration.compareTo(UPPER) > 0) {
					
					throw new NumberFormatException();
				}
			}catch (NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\n All macros must be from 1 to 99999.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid Name!\nName field must be at least 4 characters.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			Meal aMeal = new Meal(carbs, fat, hydration, name, protein);
			mc.add(Account.getuName(), aMeal, day);
			

	
			
			System.out.println(Account.getuName() + "," + name + "," + carbs.toString() + "," + fat.toString() + ","
					+ protein.toString() + "," + hydration.toString() +"\n");
			
		}
	}
	
	
	public void editMeal(Meal aMeal) {
		window = new JFrame("Edit Meal");

		
		JTextField field1 = new JTextField(aMeal.getName());
		JTextField field2 = new JTextField(aMeal.getCarbs().toString());
		JTextField field3 = new JTextField(aMeal.getFat().toString());
		JTextField field4 = new JTextField(aMeal.getProtein().toString());
		JTextField field5 = new JTextField(aMeal.getHydration().toString());
		
		

		Object[] message = { "Name", field1, "Carbs", field2, "Fat", field3, "Protein", field4, "Hydration", field5};
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			String name;
			Integer carbs; 
			Integer fat;
			Integer protein;
			Integer hydration;
			
			try {
				name = field1.getText();
				carbs = Integer.valueOf(field2.getText());
				fat = Integer.valueOf(field3.getText());
				protein = Integer.valueOf(field4.getText());
				hydration = Integer.valueOf(field5.getText());
				
				if(name.length() < 4) {
					throw new IllegalArgumentException();
				}else if(carbs.compareTo(LOWER) < 0 || carbs.compareTo(UPPER) > 0 ||
						fat.compareTo(LOWER) < 0 || fat.compareTo(UPPER) > 0 ||
						protein.compareTo(LOWER) < 0 || protein.compareTo(UPPER) > 0 || 
						hydration.compareTo(LOWER) < 0 || hydration.compareTo(UPPER) > 0) {
					
					throw new NumberFormatException();
				}
			}catch (NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\n All macros must be from 1 to 99999.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid Name!\nName field must be at least 4 characters.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			aMeal = new Meal(aMeal.getId(), carbs, fat, hydration, name, protein);
			mc.edit(aMeal);
		}
	}
	

	public void destroy() {
		window.dispose();
	}

	
	public void deleteMeal(Meal aMeal) {
		window = new JFrame("Delete Meal");
		
		int opt = JOptionPane.showConfirmDialog(window, "Warning!\nYou are about to delete the selected Meal.\nIs this what you want?", "Enter Information", JOptionPane.YES_NO_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.NO_OPTION) {
			
			mc.delete(aMeal.getId());
	
		}
	}
	
}
