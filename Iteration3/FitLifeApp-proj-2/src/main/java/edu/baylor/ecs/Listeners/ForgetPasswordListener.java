package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.ForgetPasswordController;

public class ForgetPasswordListener implements ActionListener  {

	ForgetPasswordController acct = ForgetPasswordController.getInstance();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Check Account")) {
			if (acct.checkAccount()) {
				
				// send email and add temporary password
				acct.destroy();
				acct.toLogIn();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Account Not Exist", "Failed To Find User",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getActionCommand().equals("Cancel")) {
			acct.destroy();
			acct.toLogIn();

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}