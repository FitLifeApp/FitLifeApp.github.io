package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import edu.baylor.ecs.Controllers.WindowManager.BasicItemListener;
import edu.baylor.ecs.Listeners.BasicActListener;
import edu.baylor.ecs.Listeners.CalendarListener;

public class AddWorkoutWindow extends WindowManager {
	private static volatile AddWorkoutWindow instance = null;
	private JFrame window = null;

	public AddWorkoutWindow() {}
	public static AddWorkoutWindow getInstance() {
		if (instance == null) {
			synchronized(AddWorkoutWindow.class) {
				if(instance == null) {
					instance = new AddWorkoutWindow();
				}
			}
		}
		return instance;
	}

	public JFrame makeWindow() {

		window = new JFrame("New Workout");

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		window.setLayout(flowLayout);
		
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b1.setMaximumSize(new Dimension(2000,150));

		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		comboBoxPane.setBackground(new Color(174, 214, 241));
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(new BasicItemListener());
		comboBoxPane.add(cb);

		JButton j1 = new JButton("Confirm");
		j1.setFont(new Font("Confirm", Font.PLAIN, 20));
		j1.setForeground(Color.white);
		j1.setBackground(new Color( 44, 62, 80 ));
		j1.setMaximumSize(new Dimension(2000,50));
		j1.addActionListener(new BasicActListener());
		
		JButton j2 = new JButton("Cancel");
		j2.setFont(new Font("Cancel", Font.PLAIN, 20));
		j2.setForeground(Color.white);
		j2.setBackground(new Color( 44, 62, 80 ));
		j2.setMaximumSize(new Dimension(2000,50));
		j2.addActionListener(new BasicActListener());

		// Create the "cards".
		JPanel card1 = new JPanel();
		card1.setBackground(new Color(174, 214, 241));

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(card1, ex1);
		cards.add(card1, ex2);
		cards.add(card1, ex3);
		cards.add(card1, ex4);
		
		JLabel title = new JLabel("Select a Type");
		title.setFont(new Font("Select a Type", Font.PLAIN, 40));
		
		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);
		
		b1.add(Box.createVerticalStrut(80));
		b1.add(tt);
		b1.add(Box.createVerticalStrut(30));
		b1.add(comboBoxPane);
		b1.add(Box.createVerticalStrut(25));
		b1.add(cards);
		
		Box b2 = new Box(BoxLayout.Y_AXIS);
		b2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b2.setMaximumSize(new Dimension(2000,150));
		
		b2.add(j1);
		b2.add(Box.createVerticalStrut(5));
		b2.add(j2);
		
		b1.add(b2);
		
		window.getContentPane().setBackground(new Color(174, 214, 241));

		window.add(b1);

		// Display the window.
		window.pack();
		window.setSize(new Dimension(500, 500));
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		return window;
	}
	
	public void destroyWindow() {
		window.dispose();
	}
}
