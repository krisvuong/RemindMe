 /* Names: Kris, Michelle L, Michelle M, Teja
  * Date: May 2021
  * Teacher: Mr. Ho
  * Description: Final Summative Project
  */
import java.util.*;
import java.text.*;

class RemindMe{
  
  public static void main(String[] args){
    
    //temp variables
    String taskName = "[assignment name]";
    String dueDate = "2021/05/25";  //(YYYY/MM/DD)
    String dueTime = "23:59";       //(HH:mm) - 24h clock
    
    System.out.println(countdown(taskName, dueDate, dueTime));
  }
  
  
   
  
  //add methods here
  
  
  public static String countdown(String taskName, String dueDate, String dueTime){
    
    //Declare necessary variables
    long totalSec;  //used for calculations
    
    long diffYear;  //remaining number of years/months/days... until due date
    long diffMonth;
    long diffDay;
    long diffHour;
    long diffMin;
    long diffSec;
    
    String returnMsg = "";
    
    Date due = new Date();  //initialize dates
    String niceDue;  //final formatted due date
    
    //Create date and time formats (templates)
    SimpleDateFormat simple = new SimpleDateFormat ("yyyy/MM/dd/HH:mm");
    SimpleDateFormat pretty = new SimpleDateFormat ("EEEE MMM d, y, h:mm a");  //ex. "Tuesday May 25, 2021, 9:39 PM"
    
    //Get current date
    Date today = new Date();
    
    //Format due date
    try{
      due = simple.parse(dueDate + "/" + dueTime);
    }
    catch(Exception e){
    }
    niceDue = pretty.format(due); 
    //add negative time
    
    //Total seconds until due date and time
    totalSec = (due.getTime() - today.getTime())/1000;  //method gives milliseconds since July 1 1970 (divide by 1000 to get seconds)
    
    //Find number of years/months/days... until due date and time
    diffYear = totalSec/31536000;
    diffMonth = (totalSec - (diffYear*31536000))/2635200;
    diffDay = (totalSec-(diffYear*31536000)-(diffMonth*2635200))/86400;
    diffHour = (totalSec-(diffYear*31536000)-(diffMonth*2635200)-(diffDay*86400))/3600;
    diffMin = (totalSec-(diffYear*31536000)-(diffMonth*2635200)-(diffDay*86400)-(diffHour*3600))/60;
    diffSec = totalSec-(diffYear*31536000)-(diffMonth*2635200)-(diffDay*86400)-(diffHour*3600)-(diffMin*60);
    
    
    //Create return String
    if(diffYear>0){
      returnMsg += diffYear + " year";
      if(diffYear>1){
        returnMsg += "s";
      }
      returnMsg += ", ";
    }
    if(diffMonth>0){
      returnMsg += diffMonth + " month";
      if(diffMonth>1){
        returnMsg += "s";
      }
      returnMsg += ", ";
    }
    if(diffDay>0){
      returnMsg += diffDay + " day";
      if(diffDay>1){
        returnMsg += "s";
      }
      returnMsg += ", ";
    }
    if(diffHour>0){
      returnMsg += diffHour + " hour";
      if(diffHour>1){
        returnMsg += "s";
      }
      returnMsg += ", ";
    }
    if(diffMin>0){
      returnMsg += diffMin + " minute";
      if(diffMin>1){
        returnMsg += "s";
      }
      returnMsg += ", ";
    }
    if(diffSec>0){
      returnMsg += diffSec + " second";
      if(diffSec>1){
        returnMsg += "s";
      }
      returnMsg += ", ";
    }
    returnMsg += "until " + taskName + " is due on " + niceDue;
    
    //Return countdown message
    return returnMsg;
  }
}