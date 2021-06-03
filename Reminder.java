
import java.util.Calendar;

/**
 * @author michelle chan
 *
 *This class is extended from BaseObj 
 */

public class Reminder extends BaseObj{
	
	/**
	 * This is a constructor without any parameters. It will set the name of the CSV file to store and read the reminder. 
	 */
	
	public Reminder() {
		super(".\\reminder.csv");

	}

	/**
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param time
	 * 
	 * This constructor consists of 4 parameters: name, startDate, endDate, time
	 * These parameters will be set to the superclass variables (the file name will not change)
	 */
	
	public Reminder(String name, Calendar startDate, Calendar endDate, String time) {
		super(name, startDate, endDate, time, ".\\reminder.csv");

	}


	
}
