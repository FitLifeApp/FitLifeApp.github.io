package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import edu.baylor.ecs.FLADatabase.WorkoutController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Workout;
import edu.baylor.ecs.Listeners.BasicActListener;
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
	private String type = null;
	private WorkoutController wc = WorkoutController.getInstance();

	private WorkoutDialog() {
	}

	public static WorkoutDialog getInstance() {
		if (instance == null) {
			synchronized (WorkoutDialog.class) {
				if (instance == null) {
					instance = new WorkoutDialog();
				}
			}
		}
		return instance;
	}

	// state change listener
	class BasicItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent evt) {
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, (String) evt.getItem());
		}

	}

	public void addWorkout(Date day) throws Exception {
		
		/*
		window = new JFrame("Select a Type");
		this.day = day;

		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		setType(String.valueOf(cb.getSelectedItem()));
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
		window.setVisible(true);*/
		
		//--------------------------->yufan's modification
		window = new JFrame("New Workout");
		this.day = day;

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
		j1.addActionListener(new WorkoutDialogListener());
		j1.addActionListener(new BasicActListener());
		
		JButton j2 = new JButton("Cancel");
		j2.setFont(new Font("Cancel", Font.PLAIN, 20));
		j2.setForeground(Color.white);
		j2.setBackground(new Color( 44, 62, 80 ));
		j2.setMaximumSize(new Dimension(2000,50));
		j2.addActionListener(new WorkoutDialogListener());
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
		
	}

	public void addWorkoutDialog() throws IOException {
		/*
		window = new JFrame();

		File file = new File("workout.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();
		JTextField field4 = new JTextField();

		Object[] message = { "Name of Workout", field1, "Duration", field2, "Your Weight", field3, "Weight Used",
				field4 };
		int opt = JOptionPane.showConfirmDialog(window, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			
			//creates a workout and adds it to the database
			Workout aWorkout = new Workout(Integer.valueOf(field2.getText()),
			field1.getText(), type, Double.valueOf(field3.getText()),
			Double.valueOf(field4.getText()));
			wc.add(Account.getuName(), aWorkout, day);
	
			String exercise = field1.getText();
			Integer duration = Integer.valueOf(field2.getText());
			String weight = field3.getText();
			Integer weightUsed = Integer.valueOf(field4.getText());
	
			FileWriter w = new FileWriter(file, true);
			PrintWriter p = new PrintWriter(w);
	
			p.write( Account.getuName() + "," + getType() + "," + exercise + "," + weight + "," + weightUsed + "," +  duration + "," + day.toString() + "\n");
			System.out.println(
					Account.getuName() + "," + getType() + "," + exercise + "," + weight + "," + weightUsed + "," +  duration + "," + day.toString() + "\n");
	
			w.close();
			p.close();
		}*/
		
		//--------------------------->yufan's modification
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,15))); 

		JFrame f = new JFrame();

		File file = new File("workout.csv");

		JLabel l1 = new JLabel("Name of Workout                          ");
		l1.setMaximumSize(new Dimension(2000,30));
		l1.setFont(new Font("Name of Workout                           ", Font.PLAIN, 15));

		JLabel l2 = new JLabel("Duration     ");
		l2.setMaximumSize(new Dimension(2000,30));
		l2.setFont(new Font("Duration", Font.PLAIN, 15));

		JLabel l3 = new JLabel("Your Weight");
		l3.setMaximumSize(new Dimension(2000,30));
		l3.setFont(new Font("Your Weight", Font.PLAIN, 15));
		
		JLabel l4 = new JLabel("Weight Used");
		l4.setMaximumSize(new Dimension(2000,30));
		l4.setFont(new Font("Weight Used", Font.PLAIN, 15));

		JTextField field1 = new JTextField();
		field1.setMaximumSize(new Dimension(2000,30));
		field1.setFont(new Font("", Font.PLAIN, 20));

		JTextField field2 = new JTextField();
		field2.setMaximumSize(new Dimension(2000,30));
		field2.setFont(new Font("", Font.PLAIN, 20));

		JTextField field3 = new JTextField();
		field3.setMaximumSize(new Dimension(2000,30));
		field3.setFont(new Font("", Font.PLAIN, 20));
		
		JTextField field4 = new JTextField();
		field4.setMaximumSize(new Dimension(2000,30));
		field4.setFont(new Font("", Font.PLAIN, 20));

		b1.add(l1);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(l2);
		b1.add(field2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(l3);
		b1.add(field3);
		b1.add(Box.createVerticalStrut(10));
		b1.add(l4);
		b1.add(field4);
		b1.add(Box.createVerticalStrut(10));

		JPanel pane = new JPanel(); 
		pane.add(b1);


		//Object[] message = { "Name of Workout", field1, "Duration", field2, "Your Weight", field3, };
		JOptionPane.showConfirmDialog(f, pane, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		String exercise = field1.getText();
		Integer duration = Integer.valueOf(field2.getText());
		String weight = field3.getText();
		FileWriter w = new FileWriter(file, true);
		PrintWriter p = new PrintWriter(w);
		p.write("fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		System.out.println(
				"fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		w.close();
		p.close();
	}

	public void destroy() {
		window.dispose();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
