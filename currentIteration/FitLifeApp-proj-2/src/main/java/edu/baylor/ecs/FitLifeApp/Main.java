package edu.baylor.ecs.FitLifeApp;

public class Main {
	public static void main(String[]args)  {

		final WindowManager m1 = WindowManager.getInstance();
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				m1.toLogIn();
			}
		});
	}
}
