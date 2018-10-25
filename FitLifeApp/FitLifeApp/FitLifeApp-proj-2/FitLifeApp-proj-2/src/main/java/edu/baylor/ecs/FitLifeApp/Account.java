package edu.baylor.ecs.FitLifeApp;
/*
 * File:			Account.java
 * Description:		Holds account details
 */
import java.awt.Color;

public class Account {
	/*
	 * 	c1 -> Base1
	 *  c2 -> Base2
	 *  c3 -> Trim1
	 *  c4 -> Trim2
	 *  I don't really know proper coloring terms
	 *  Here because one of the requirements is being able to change colors
	 */
	private Color colorBase1;
	private Color colorBase2;
	private Color colorTrim1;
	private Color colorTrim2;

	/*
	 * String username, password, email
	 * List<String> Workouts //temp type, only string as that is how currently read
	 */
	
	
	public Color getColorBase1() {
		return colorBase1;
	}



	public Color getColorBase2() {
		return colorBase2;
	}



	public Color getColorTrim1() {
		return colorTrim1;
	}



	public Color getColorTrim2() {
		return colorTrim2;
	}



	Account() {
		colorBase1 = new Color(72, 201, 176);
		colorBase2 = new Color(46, 64, 83);
		colorTrim1 = new Color(174, 214, 241);
		colorTrim2 = new Color(84, 153, 199);
	}
}
