import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 
 * THIS IS A UNIT TESTING FILE FOR FILE I/O CODER
 * PLEASE REMOVE BEFORE SUBMITTING THE FINAL PRODUCT
 *
 */



public class TestDriver {
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	 
		Assignment assign1 = new Assignment();
		assign1.setName("Michelle Chan Assign 1");
		assign1.setStartDate(Calendar.getInstance());
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, 2);
	//	assign1.setEndDate(cal1);
		assign1.setEndDate(Calendar.getInstance());
		assign1.setTime("15:00");
		
		Assignment assign2 = new Assignment();
		assign2.setName("Michelle Chan Assign 2");
		assign2.setStartDate(Calendar.getInstance());
		assign2.setEndDate(Calendar.getInstance());
		assign2.setTime("11:00");
		
		Assignment assign3 = new Assignment();
		assign3.setName("Michelle Chan Assign 3");
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, 2);
	//	assign3.setEndDate(cal2);
		assign3.setEndDate(Calendar.getInstance());
		assign3.setStartDate(Calendar.getInstance());
		assign3.setTime("14:00");
		
		
		
		FileHandler.writeToCSV(assign1);
		FileHandler.writeToCSV(assign2);
		FileHandler.writeToCSV(assign3);
		
		List<Assignment> assignList = FileHandler.readAssignment();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		for (Assignment assign : assignList) {
			System.out.println("Name: " + assign.getName());
			System.out.println("EndDate: " + sdf.format(assign.getEndDate().getTime()));
		}
		
		
		Reminder reminder1 = new Reminder();
		reminder1.setName("Michelle Chan Reminder 1");
		reminder1.setStartDate(Calendar.getInstance());
		Calendar cal11 = Calendar.getInstance();
		cal11.add(Calendar.MONTH, 2);
		
	//	reminder1.setEndDate(cal11);
		reminder1.setEndDate(Calendar.getInstance());
		reminder1.setTime("15:00");
		
		Reminder reminder2 = new Reminder();
		reminder2.setName("Michelle Chan Reminder 2");
		reminder2.setStartDate(Calendar.getInstance());
		reminder2.setEndDate(Calendar.getInstance());
		reminder2.setTime("12:00");
		
		Reminder reminder3 = new Reminder();
		reminder3.setName("Michelle Chan Reminder 3");
		Calendar cal12 = Calendar.getInstance();
		cal12.add(Calendar.DATE, -2);
	//	reminder3.setEndDate(cal12);
		reminder3.setEndDate(Calendar.getInstance());
		reminder3.setStartDate(Calendar.getInstance());
		reminder3.setTime("18:00");
		
		FileHandler.writeToCSV(reminder1);
		FileHandler.writeToCSV(reminder2);
		FileHandler.writeToCSV(reminder3);
		
		List<Reminder> reminderList = FileHandler.readReminder();
	
	
		for (Reminder reminder : reminderList) {
			System.out.println("Name: " + reminder.getName());
			System.out.println("EndDate: " + sdf.format(reminder.getEndDate().getTime()));
		}
		

	}

}
