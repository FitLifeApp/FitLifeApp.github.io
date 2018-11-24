package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.baylor.ecs.Listeners.WorkoutDialogListener;

public final class WorkoutDialog {
	private static volatile WorkoutDialog instance = null;
	private JFrame window = null;
	private JPanel cards;
	private final String ex1 = "Aerobic Exercise";
	private final String ex2 = "Strength Exercise";
	private final String ex3 = "Balance Exercise";
	private final String ex4 = "Flexibility Exercise";
	private Date day;
	
	private WorkoutDialog(){}
	
	public static WorkoutDialog getInstance() {
		if(instance == null) {
			synchronized(WorkoutDialog.class) {
				if(instance == null) {
					instance = new WorkoutDialog();
				}
			}
		}
		return instance;
	}
	
	
	//state change listener
		class BasicItemListener implements ItemListener {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) evt.getItem());
			}

		}
	
	public void addWorkout(Date day) throws Exception{
		
		window = new JFrame("Select a Type");
		this.day = day;
		
		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(new BasicItemListener());
		comboBoxPane.add(cb);

		JButton j1 = new JButton("Confirm");

		j1.addActionListener(new WorkoutDialogListener());

		// Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(j1);

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(card1, ex1);
		cards.add(card1, ex2);
		cards.add(card1, ex3);
		cards.add(card1, ex4);

		window.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
		window.getContentPane().add(cards, BorderLayout.CENTER);

		window.setLocationRelativeTo(null);

		// Display the window.
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 100, (int) window.getSize().getHeight() + 30);
		window.setVisible(true);
	}
	
	public void addWorkoutDialog() throws IOException {
		
		window = new JFrame();
		
		File file = new File("workout.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		Object[] message = { "Name of Workout", field1, "Duration", field2, "Your Weight", field3, };
		JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		
		String exercise = field1.getText();
		String duration = field2.getText();
		String weight = field3.getText();
		
		FileWriter w = new FileWriter(file, true);
		PrintWriter p = new PrintWriter(w);
		
		p.write("fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		System.out.println(
				"fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		
		w.close();
		p.close();
	}
	
	public void destroy(){
		window.dispose();
	}
	
}
