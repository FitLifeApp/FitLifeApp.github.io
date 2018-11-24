package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.LogIn;

//Listener used for buttons in LogIn window
	//maybe this can be removed to another class
	//can this just bubble up?
	public class LoginListener implements ActionListener {
		LogIn login = LogIn.getInstance();
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Sign In")) {
				if (login.validate()) {
					login.setAcct(login.getAcct());
					login.destroyWindow();
					login.toHome();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Incorrect Username/Password", "Failed Login",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getActionCommand().equals("Create Account")) {
				login.destroyWindow();
				login.toAcctCreation();
			} else if (e.getActionCommand().equals("Forgot Password")) {
				login.toAcctCreation();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
