import java.util.Calendar;

/**
 * @author michelle chan
 *
 * This is an abstract class that can be extended, but not instantiated directly
 */
public abstract class BaseObj {
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private String time;
	
	private String pathFileName;
	
	
/**
 * @param name
 * @param startDate
 * @param endDate
 * @param time
 * @param pathFileName
 * 
 * This constructor consists of 5 parameters: name, startDate, endDate, time, pathFileName
 */
	public BaseObj(String name, Calendar startDate, Calendar endDate, String time, String pathFileName) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.time = time;
		this.pathFileName = pathFileName;
	}
	
	public BaseObj(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPathFileName() {
		return pathFileName;
	}


	
	
}
