 /* Names: Kris, Michelle L, Michelle M, Teja
  * Date: May 2021
  * Teacher: Mr. Ho
  * Description: Final Summative Project
  */
import java.util.*;
import java.text.*;

class RemindMe{
  
  public static void main(String[] args){
    
    //temporary variables
    String startDay = "2021/06/27";  //(YYYY/MM/DD)
    String endDay = "2021/09/29";
    
    System.out.println(countdown(startDay, endDay));
  }
  
  
  public static String countdown(String startDay, String endDay){
    
    //Declare necessary variables
    long daysTilStart;  //days until assignment start date
    long daysDuration;  //duration of assignment (in days)
    String returnMsg = "";
    Date start = new Date();  //initialize start date
    Date end = new Date();  //initialize end date
    Date today = new Date();  //get present date
    
    //Create date and time format (template)
    SimpleDateFormat simple = new SimpleDateFormat ("yyyy/MM/dd");
    
    //Parse String input dates into Date types
    try{
      start = simple.parse(startDay);
      end = simple.parse(endDay);
    }
    catch(Exception e){
    }
    
    //Calculate total days until start, and duration of assignment
    daysTilStart = (start.getTime() - today.getTime())/86400000;  //method gives milliseconds since July 1 1970 (divide by 86400000 to get days)
    daysDuration = (end.getTime() - start.getTime())/86400000;
    
    //Create return String
    returnMsg = daysTilStart + "," + daysDuration;

    //Return countdown message
    return returnMsg;
  }
}