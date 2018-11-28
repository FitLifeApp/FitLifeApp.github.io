package edu.baylor.ecs.Listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;

import edu.baylor.ecs.Controllers.CalendarController;
import edu.baylor.ecs.FitLifeApp.LogItem;
import edu.baylor.ecs.FitLifeApp.Meal;
import edu.baylor.ecs.FitLifeApp.Sleep;
import edu.baylor.ecs.FitLifeApp.Workout;

public class ReviewTableListener extends MouseAdapter{
	private List<LogItem> arr;

	public ReviewTableListener(List<LogItem> logItems) {this.arr = logItems;}
	public void mouseClicked(MouseEvent e) {
		
	    if (e.getClickCount() == 2) {
	      CalendarController cw = CalendarController.getInstance();
	      JTable target = (JTable)e.getSource();
	      
	      
	      int row = 0;
	      try {
	    	//Protection against filtering
	    	  row = target.getRowSorter().convertRowIndexToModel(target.getSelectedRow());
	      }catch(IndexOutOfBoundsException e1) {
	    	  
	      }
	      
	      //Don't get the header
	      if(row > 0) {
	    	  if(cw.getMode().compareTo(Integer.valueOf(0)) == 0){
	    		  //pop open an editor for this row
	    		  Workout aWorkout = (Workout) arr.get(row-1);
	    		  try {
					cw.showEditWorkoutDialog(aWorkout);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	  
		      } else if (cw.getMode().compareTo(Integer.valueOf(1)) == 0) {
		    	  Meal aMeal = (Meal) arr.get(row-1);
		    	  try {
					cw.showEditMealDialog(aMeal);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	  
		      }else if(cw.getMode().compareTo(Integer.valueOf(2)) == 0) {
		    	  Sleep aSleep = (Sleep) arr.get(row-1);
		    	  try {
					cw.showEditSleepDialog(aSleep);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	  
		      }
	    	  
	    	  cw.toDay(cw.getDay());
	      }
	     
	 
	    } else if (e.getButton() == MouseEvent.BUTTON3) {
	    	CalendarController cw = CalendarController.getInstance();
		      JTable target = (JTable)e.getSource();
		      
		      
		      int row = 0;
		      try {
			    	//Protection against filtering
			    	  row = target.getRowSorter().convertRowIndexToModel(target.getSelectedRow());
			      }catch(IndexOutOfBoundsException e1) {
			    	  
			      }
		    //Don't get the header
		      if(row > 0) {
		    	  if(cw.getMode().compareTo(Integer.valueOf(0)) == 0){
		    		  //pop open an editor for this row
		    		  Workout aWorkout = (Workout) arr.get(row-1);
		    		  try {
						cw.showDeleteWorkout(aWorkout);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	  
			      } else if (cw.getMode().compareTo(Integer.valueOf(1)) == 0) {
			    	  Meal aMeal = (Meal) arr.get(row-1);
			    	  try {
						cw.showDeleteMeal(aMeal);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	  
			      }else if(cw.getMode().compareTo(Integer.valueOf(2)) == 0) {
			    	  Sleep aSleep = (Sleep) arr.get(row-1);
			    	  try {
						cw.showDeleteSleep(aSleep);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	  
			      }
		    	  
		    	  cw.toDay(cw.getDay());
		      }
	    }
  }
	
}
