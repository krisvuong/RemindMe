import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author michelle chan
 *
 * This object will handle the read and write CSV files
 */

public class FileHandler {

	/**
	 * @param objToCSV
	 * 
	 * This function writes the data into the CSV file by line
	 * Both reminder and assignment are persisted from this function to different CSV files
	 * If a new entry is inputed, it will be appended to the file
	 * If a entry is modified, the new information will replace the old one in the CSV file
	 */
	
	public static void writeToCSV(BaseObj objToCSV) {
		// writes object into CSV output file, if the file already exists will delete and create a new CSV file.

		if (objToCSV != null) {
			
			List<String> lines = new ArrayList<String>();
			String header = "Name,StartDate,EndDate,Time";
			lines.add(header);
			if (objToCSV instanceof Reminder) {
				// reads in all the data from the reminder CSV file and stores into a reminder list without sorting
				List<Reminder> reminderList = readReminderFromCSV();
				if (reminderList == null) reminderList = new ArrayList<Reminder>();
				boolean isUpdate = false;
				for (Reminder reminder : reminderList) {
					// checks to see if the reminder name already exists in the file. 
					// if it already exists, the contents will be replaced by new data.
					StringBuffer csvLine = new StringBuffer();
					if (reminder.getName() != null && reminder.getName().equalsIgnoreCase(objToCSV.getName())) {
						csvLine = csvLine.append(objToCSV.getName() != null?objToCSV.getName():"").append(",")
								.append(convertCalendar2String(objToCSV.getStartDate())).append(",")
								.append(convertCalendar2String(objToCSV.getEndDate())).append(",")
								.append(objToCSV.getTime() != null?objToCSV.getTime():"");
						// sets update to true. the new data will not be appended to the bottom of the file. 
						isUpdate = true;
						
					} else {
						csvLine = csvLine.append(reminder.getName() != null ? reminder.getName() : "").append(",")
								.append(convertCalendar2String(reminder.getStartDate())).append(",")
								.append(convertCalendar2String(reminder.getEndDate())).append(",")
								.append(reminder.getTime() != null ? reminder.getTime() : "");

					}

					lines.add(csvLine.toString());
				}

				if (!isUpdate) {
					// if the name doesn't already exist, the new input is appended to the bottom of the file. 
					StringBuffer csvLine = new StringBuffer();
					csvLine = csvLine.append(objToCSV.getName() != null?objToCSV.getName():"").append(",")
							.append(convertCalendar2String(objToCSV.getStartDate())).append(",")
							.append(convertCalendar2String(objToCSV.getEndDate())).append(",")
							.append(objToCSV.getTime() != null?objToCSV.getTime():"");
					lines.add(csvLine.toString());
				}

			} else { 
				// reads in all the data from the assignment CSV file and stores into an assignment list without sorting
				List<Assignment> assignListList = readAssignmentFromCSV();
				if (assignListList == null) assignListList = new ArrayList<Assignment>();
				boolean isUpdate = false;
				for (Assignment assignment : assignListList) {
					// checks to see if the assignment name already exists in the file. 
					// if it already exists, the contents will be replaced by new data.
					StringBuffer csvLine = new StringBuffer();
					if (assignment.getName() != null && assignment.getName().equalsIgnoreCase(objToCSV.getName())) {
						csvLine = csvLine.append(objToCSV.getName() != null?objToCSV.getName():"").append(",")
								.append(convertCalendar2String(objToCSV.getStartDate())).append(",")
								.append(convertCalendar2String(objToCSV.getEndDate())).append(",")
								.append(objToCSV.getTime() != null?objToCSV.getTime():"");
						// sets update to true. the new data will not be appended to the bottom of the file. 
						isUpdate = true;

					} else {
						csvLine = csvLine.append(assignment.getName() != null ? assignment.getName(): "").append(",")
								.append(convertCalendar2String(assignment.getStartDate())).append(",")
								.append(convertCalendar2String(assignment.getEndDate())).append(",")
								.append(assignment.getTime() != null?assignment.getTime():"");

					}

					lines.add(csvLine.toString());
				}

				if (!isUpdate) {
					// if the name doesn't already exist, the new input is appended to the bottom of the file. 
					StringBuffer csvLine = new StringBuffer();
					csvLine = csvLine.append(objToCSV.getName() != null?objToCSV.getName():"").append(",")
							.append(convertCalendar2String(objToCSV.getStartDate())).append(",")
							.append(convertCalendar2String(objToCSV.getEndDate())).append(",")
							.append(objToCSV.getTime() != null?objToCSV.getTime():"");
					lines.add(csvLine.toString());
				}

			}
			
			// calls writeFile to write to CSV file 
			writeFile(objToCSV.getPathFileName(), lines);
		}
	}
	
	/**
	 * @return
	 * 
	 * Reads the assignment data from the assignment CSV file
	 * Sorts the list of objects by the end date in ascending order using insertion sort. 
	 */
	
	public static List<Assignment> readAssignment() {
		List<Assignment> assignments = readAssignmentFromCSV();
		insertionSortAssigment(assignments);
		return assignments;
	}

	/**
	 * @return
	 * 
	 * Reads the reminder data from the reminder CSV file
	 * Sorts the list of objects by the end date in ascending order using insertion sort. 
	 */
	
	public static List<Reminder> readReminder() {
		List<Reminder> reminders = readReminderFromCSV();
		insertionSortReminder(reminders);
		return reminders;
	}
			
	/**
	 * @param pathFileName
	 * @param lines
	 * 
	 * This function will write the contents into the CSV file with the inputed path and filename 
	 */
	
	private static void writeFile (String pathFileName, List<String> lines) {		

		// if the path filename is null or blank, or the list has no content, the information will not be written to file
		if (pathFileName == null || pathFileName.isBlank() || lines == null) return;
		
			FileWriter outFile = null;
			PrintWriter out = null;
			try {
				File file = new File(pathFileName);
				boolean isFileExist = file.exists();

				// checks if file already exists, then deletes the existing CSV file and creates a new one
				// otherwise, create a new CSV file
				if (isFileExist) {
					if (file.delete()) {
						file.createNewFile();
					}
				} else {
					file.createNewFile();
				}

				outFile = new FileWriter(file, false);
				out = new PrintWriter(outFile);

				// loop through the String array to print each entry to the CSV file
				for (String line : lines) {
					out.println(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// closes all file handlers
				if (out != null) {
					out.close();
				}
				try {
					if (outFile != null)
						outFile.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}
	
/**
 * @return
 * 
 * Reads the assignment data from the assignment CSV file and returns a list of the assignments from the CSV file
 */
	
	private static List<Assignment> readAssignmentFromCSV() {

		Assignment assignment = new Assignment();
		String pathFileName = assignment.getPathFileName();
		FileReader fopen = null;
		BufferedReader opened = null;
		String line;
		List<Assignment> assignList = new ArrayList<Assignment>();
		File file = null;
		try {
			file = new File(pathFileName);
			// checks if the CSV file exists. otherwise, returns an empty list
			if ( file.exists() ) {				
				fopen = new FileReader(pathFileName);
				opened = new BufferedReader(fopen);
				int counter = 0;
				opened.readLine();
				while ((line = opened.readLine()) != null) {
		
					assignment = new Assignment();
					String[] lineArr = line.split(",");
					if (lineArr.length == 4) {
						assignment.setName(lineArr[0]);
						assignment.setStartDate(convertString2Calendar(lineArr[1]));
						assignment.setEndDate(convertString2Calendar(lineArr[2]));
						assignment.setTime(lineArr[3]);
		
						assignList.add(assignment);
					}
		
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// closes all file handlers
				if (opened != null)
					opened.close();

				if (fopen != null)
					fopen.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return (assignList);

	}

	/**
	 * @return
	 * 
	 * Reads the reminder data from the reminder CSV file and returns a list of the reminders from the CSV file
	 */
	
	private static List<Reminder> readReminderFromCSV() {

		Reminder reminder = new Reminder();
		String pathFileName = reminder.getPathFileName();
		FileReader fopen = null;
		BufferedReader opened = null;
		String line;
		List<Reminder> reminderList = new ArrayList<Reminder>();
		File file = null;
		try {
			file = new File(pathFileName);
			// checks if the CSV file exists. otherwise, returns an empty list
			if ( file.exists() ) {
				fopen = new FileReader(pathFileName);
				opened = new BufferedReader(fopen);
				int counter = 0;
				opened.readLine();
				while ((line = opened.readLine()) != null) {
	
					reminder = new Reminder();
					String[] lineArr = line.split(",");
					if (lineArr.length == 4) {
						reminder.setName(lineArr[0]);
						reminder.setStartDate(convertString2Calendar(lineArr[1]));
						reminder.setEndDate(convertString2Calendar(lineArr[2]));
						reminder.setTime(lineArr[3]);
	
						reminderList.add(reminder);
					}
	
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// closes all file handlers
				if (opened != null)
					opened.close();

				if (fopen != null)
					fopen.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return (reminderList);
	}

	/**
	 * @param reminders
	 * 
	 * This sorts the reminder dates and times by ascending order through insertion sort
	 */
	
	private static void insertionSortReminder(List<Reminder> reminders) {
		if (reminders == null) return;
		
		int len = reminders.size();
		
		for (int i = 1; i < len; i++) {
			Reminder tempReminder = reminders.get(i);
			Calendar tempCalWTime = addTime2Cal(tempReminder.getEndDate(), tempReminder.getTime());
			
			int pos = i - 1;
			while ((pos > -1) && (addTime2Cal(reminders.get(pos).getEndDate(), reminders.get(pos).getTime()).after(tempCalWTime))) {
				reminders.set(pos + 1, reminders.get(pos));
				pos--;
			}
			reminders.set(pos + 1, tempReminder);
			
		}
		
	}
	
	/**
	 * @param assignments
	 * 
	 * This sorts the assignment dates and times by ascending order through insertion sort
	 */
	
	private static void insertionSortAssigment(List<Assignment> assignments) {
		if (assignments == null) return;
		
		int len = assignments.size();
		for (int i = 1; i < len; i++) {
			Assignment tempAssign = assignments.get(i);
			Calendar tempCalWTime = addTime2Cal(tempAssign.getEndDate(), tempAssign.getTime());
			int pos = i - 1;
			while ((pos > -1) && (addTime2Cal(assignments.get(pos).getEndDate(), assignments.get(pos).getTime()).after(tempCalWTime))) {
				assignments.set(pos + 1, assignments.get(pos));
				pos--;
			}
			assignments.set(pos + 1, tempAssign);
			
		}
		
	} 
	
	/**
	 * @param cal
	 * @param time
	 * @return
	 * 
	 * Adds a time string to the java calendar
	 */
	
	private static Calendar addTime2Cal(Calendar cal, String time) {
		if (cal == null || time == null || time.isBlank()) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf_time = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = cal.getTime();
		String dateStr = sdf.format(date);
		dateStr = dateStr + " " + time;
		try {
			date = sdf_time.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Calendar calTime = Calendar.getInstance();
		calTime.setTime(date);
		return calTime;
	}

	/**
	 * @param dateStr
	 * @return
	 * 
	 * Converts the date string to the java calendar
	 */
	
	private static Calendar convertString2Calendar(String dateStr) {
		if (dateStr == null || dateStr.isBlank()) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * @param cal
	 * @return
	 * 
	 * Converts the java calendar to a string
	 */
	
	private static String convertCalendar2String(Calendar cal) {
		if (cal == null) return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = cal.getTime();
		String dateStr = "";
		try {
			dateStr = sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return dateStr;
	}

}
