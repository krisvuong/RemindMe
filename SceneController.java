import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;
import java.util.Optional;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.Group;

public class SceneController {

    // Main Scene Variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Schedule Scene Variables
    @FXML
    private ComboBox<String> timePick;

    ObservableList<String> timeSlots = FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00",
            "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00");

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lbDate;

    @FXML
    private Label label;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Button exitBtn;

    @FXML
    private TextField txtField;

    @FXML
    private TextField txtField1;

    @FXML
    private DatePicker startDay;

    @FXML
    private DatePicker endDay;

    @FXML
    private Button submitNewAssignment;

    @FXML
    private Text newAssignmentError;

    @FXML 
    private Text newAssignmentSuccess;

    @FXML
    private TextField assignmentName;

    @FXML
    private Text astrix1;

    @FXML
    private Text astrix2;

    @FXML
    private Text astrix3;

    @FXML
    private Label titleee;

    @FXML
    private Text ganttName;
    
    @FXML
    private Text ganttStart;
    
    @FXML
    private Text ganttEnd;

    @FXML
    private Rectangle rectangle1;

    @FXML
    private Rectangle rectangle2;
    
    @FXML
    private Rectangle rectangle3;
    
    @FXML
    private Rectangle rectangle4;
    
    @FXML
    private Rectangle rectangle5;
    
    @FXML
    private Rectangle rectangle6;
    
    @FXML
    private Rectangle rectangle7;
    
    @FXML
    private Rectangle rectangle8;
    
    @FXML
    private Rectangle rectangle9;
    
    @FXML
    private Rectangle rectangle10;
    
    @FXML
    private Rectangle rectangle11;
    
    @FXML
    private Rectangle rectangle12;
    
    @FXML
    private Rectangle rectangle13;

    @FXML
    private Button reload;

    @FXML
    private Text day1;

    @FXML
    private Text day2;

    @FXML
    private Text day3;

    @FXML
    private Text day4;

    @FXML
    private Text day5;

    @FXML
    private Text day6;

    @FXML
    private Text day7;

    @FXML
    private Text day8;

    @FXML
    private Text day9;

    @FXML
    private Text day10;

    @FXML
    private Text day11;

    @FXML
    private Text day12;

    @FXML
    private Text month1;

    @FXML 
    private Text month2;

    /**
     * @author: Teja
     * @param: ActionEvent event
     */
    // Main Scene Functions
    public void switchScene1(ActionEvent event) throws IOException {
        // Validation for missing inputs
        if (txtField.getText().equals("") || txtField1.getText().equals("")) {
            // sends an error pop up if there are any missing inputs
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("Missing inputs");
            alert3.setHeaderText("You are missing some inputs!");
            alert3.setContentText("Please make sure you make all the information before submitting");
            Optional<ButtonType> result3 = alert3.showAndWait();
            if (result3.isPresent() && result3.get() == ButtonType.OK) {
            }
        }
        // If all the user inputs are entered. (this condition also prevents any
        // Exceptions from occuring, incase the user didn't enter valid values)
        else {

            // call method called timeDifference(), and store the value in a variable called
            // "timeDuration"
            long timeDuration = timeDifference();
            // initializing variables
            String minAhead = txtField1.getText();
            long minAheadCalc = Long.parseLong(minAhead);
            String date = datePicker.getValue().toString();
            String time = timePick.getValue().toString();
            String name = txtField.getText();
            // total Duration = the duration between current time and the time entered by
            // the user (long timeDuration. This variable stores value in milliseconds) -
            // number of minutes in advance the user would
            // like the pop up to occur (convert this value to milliseconds too).
            long totalDuration = timeDuration - (minAheadCalc * 60000);
            // If the entered "minutes in advance" has a value higher than variable
            // "timeDuration", display an error pop up.
            // Since duration can't be in negatives, and clear the input field.
            if (totalDuration < 0) {
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Input Time Wrong");
                alert2.setHeaderText("The number you've enter for 'Minutes in Advance' is invalid!");
                alert2.setContentText("Please re-enter the correct info");
                Optional<ButtonType> result1 = alert2.showAndWait();
                if (result1.isPresent() && result1.get() == ButtonType.OK) {
                    txtField1.clear();
                }
                // If all the entered info is valid.
            } else {
                // Display an Comfirmation pop up allowing the user to see all the values he/she
                // entered
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setContentText("assignment name: " + name + "\nDate: " + date + "\ntime: " + time
                        + "\nMinutes in advance: " + minAhead);
                alert.setTitle("Thank you for using our program");
                alert.setHeaderText(
                        "Your Assignment will be submitted when you pressed 'OK'. \n Click 'CANCEL' to edit reminder");
                Optional<ButtonType> result = alert.showAndWait();
                // Once the user is done checking, and pressed "OK", a timer task method is
                // initiated in order to schedule the pop up
                // reminder to the user's inputed time.
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    Timer timer;
                    timer = new Timer();
                    // After the totalDuration(value is calculated based on the user's input), a
                    // class called RemindTask() is called, after the total duration is complete
                    timer.schedule(new RemindTask(), totalDuration);

                    // goes back to the main menu once the user presses "OK" for the final sumbit.
                    root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    /**
                     * @author: Michelle chan Sends info to the csv file
                     */
                    Assignment assign1 = new Assignment();
                    assign1.setName(name);
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue() - 1,
                            datePicker.getValue().getDayOfMonth());

                    assign1.setEndDate(calendar);
                    assign1.setStartDate(Calendar.getInstance());
                    // assign1.setStartDate(Calendar.getInstance());
                    assign1.setTime(time);
                    FileHandler.writeToCSV(assign1);

                    // This else feature allows the user to edit any changes of the inputs before
                    // final submit
                } else {
                }
            }
        }

    }

    /**
     * @author: Teja
     * @description: This method is used to display the pop up notification, which
     *               will occur at the time value the user inputs.
     */
    class RemindTask extends TimerTask {
        String name = txtField.getText();

        public void run() {
            // Used Swing as a pop up message.
            JOptionPane.showMessageDialog(null, name);
            // timer.cancel(); // Terminate the timer thread
        }
    }

    public void switchScene2(ActionEvent event) throws IOException, ParseException {
        root = FXMLLoader.load(getClass().getResource("schedule.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene3(ActionEvent event) throws IOException, ParseException {
        root = FXMLLoader.load(getClass().getResource("ganttChart.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene4(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene5(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("newAssignment.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene6(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("allReminders.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Schedule Scene FUnctions
    @FXML
    void selectDate(ActionEvent event) {
        Long diffDays = validationDate();
        if (diffDays < 0) {
            clearDate();
        }
    }

    @FXML
    void clickBox(MouseEvent event) {
        timePick.setItems(timeSlots);
    }

    @FXML
    void exitt(ActionEvent event) {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
    }

    /**
     * @author: Teja
     * @return: diffDays
     * @Description:
     */
    public long validationDate() {
        long diffDays = 0;
        String inputDate = "";
        Duration dateDifference = null;
        // If the user forgot to enter the date, send a pop up message, asking them to
        // enter the date, and then the time.
        if (datePicker.getValue() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Re-enter your time after selecting the date");
            alert.setTitle("Select Date");
            alert.setHeaderText("Please select the date first");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

            }
            // If the user entered the date first, calculate the duration between the
            // current date, and the date entered by the user.
        } else if (datePicker.getValue() != null) {
            inputDate = datePicker.getValue().toString();
            LocalDate inputDateCalc = LocalDate.parse(inputDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate currentDate = LocalDate.now();
            dateDifference = Duration.between(currentDate.atStartOfDay(), inputDateCalc.atStartOfDay());
            // convert the duration to milliSeconds since the timertask calculates to the
            // delay in milliseconds.
            diffDays = (dateDifference.toMillis());

        }

        return diffDays;
    }

    public long validationTime() {
        // get the value of the time ented by the user.
        String futureTime = (timePick.getValue().toString());
        LocalTime inputTimeCalc = LocalTime.parse(futureTime);
        LocalTime currentTime = LocalTime.now();
        // get the value of the duration between the current time and the time entered
        // by the user
        Duration timeDifference = Duration.between(currentTime, inputTimeCalc);
        // convert the time into milliseconds, since the timerTask calculates the delay
        // in milliseconds
        long timeDiff = (timeDifference.toMillis());

        return timeDiff;
    }

    private void clearDate() {
        // sends a pop up error indicating that the user's entered date is in the past,
        // and it clears the input field.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Please make sure to enter a valid date!");
        alert.setTitle("Error on date");
        alert.setHeaderText("The date you entered is in the past");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            datePicker.getEditor().clear();
        }
    }

    public long timeDifference() {
        // Calls validation time and Validation date, in order to receive the validated
        // values
        Long diffDays = validationDate();
        long timeDiff = validationTime();
        // adds both the values, in order to find the combined duration
        Long timeDuration = diffDays + timeDiff;
        return timeDuration;
    }

    @FXML
    void selectTime(ActionEvent event) {
        // once the user enteres the time, a method called timeDifference is called
        long timeDuration = timeDifference();
        if (timeDuration < 0) {
            // if the time duration is less than 0, for example, the user entered today's
            // date, and inputs the time as 4am, when the current time is 5am
            // This condition would call a method called clear time
            clearTime();
        }
    }

    private void clearTime() {
        // Sends an error pop up, asking the user to enter the corrent time.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Please make sure to enter a valid Time after choosing your date");
        alert.setTitle("Error on Time");
        alert.setHeaderText("The time you entered is invalid for your inputted date!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        }
    }


    /* 
     * Handles option 2 of the main menu: enter a new assignment
     * 
     * @author - Kris Vuong
     * @param ActionEvent event
     * @throws IOException
     */
    public void assignmentDuration(ActionEvent event) throws IOException {
        //Reset error or success messages
        astrix1.setText("");
        astrix2.setText("");
        astrix3.setText("");
        newAssignmentError.setText("");
        newAssignmentSuccess.setText("");

        //Check for empty fields
        if(assignmentName.getText().equals("") || startDay.getValue() == null || endDay.getValue() == null){
            missingInfo();
        }
        
        else{
            //Get user-inputted start and end dates
            String start = startDay.getValue().toString();
            String end = endDay.getValue().toString();
            
            //Declare necessary variables
            long daysTilStart;  //days until assignment start date
            long daysDuration;  //duration of assignment (in days)
            String returnMsg = "";
            Date startDate = new Date();  //initialize start date
            Date endDate = new Date();  //initialize end date
            Date today = new Date();    //initialize current date

            //Parse string dates to Date types
            startDate = SDF1(start);
            endDate = SDF1(end);

            //Calculate duration of assignment and days until start
            daysDuration = calculateDuration(endDate, startDate);
            daysTilStart = calculateTilToday(startDate);

            //Print success or error messages
            if(daysDuration<0){
                assignmentInvalid();    //error
            }
            else if(today.after(endDate)){
                assignmentPassed();     //error
            }
            //Print success message
            else{
                assignmentSuccess();    //success
            }
        }
    }

    /* 
     * Parses a String value to a Date type
     * 
     * @author - Kris Vuong
     * @param ActionEvent event
     * @throws - IOException
     * @return - Date in yyyy-MM-dd format
     */
    private Date SDF1(String strDate){
        SimpleDateFormat simple = new SimpleDateFormat ("yyyy-MM-dd");
        Date date = new Date();
        //Parse String input dates into Date types
        try{
            date = simple.parse(strDate);
        }
        catch(Exception e){
        }
        return date;
    }

    /* 
     * Parses a String value to a Date type
     * 
     * @author - Kris Vuong
     * @param ActionEvent event
     * @throws - IOException
     * @return - Date in dd-MM-yyyy format
     */
    private Date SDF2(String strDate) throws ParseException{
        SimpleDateFormat simple = new SimpleDateFormat ("dd-MM-yyyy");
        Date date = new Date();
        //Parse String input dates into Date types
        try{
            date = simple.parse(strDate);
            //System.out.println(strDate);
            return date;
        }
        catch(Exception e){
            date = simple.parse("01-01-1980");
            return date;
        }
    }


    /* 
     * Prints error message and asterisks for empty fields (in option 2)
     * 
     * @author - Kris Vuong
     */
    private void missingInfo() {
        newAssignmentError.setText("Please enter information in all fields");
        
        if(assignmentName.getText().equals("")){
            astrix1.setText("*");
        }
        if(startDay.getValue() == null){
            astrix2.setText("*");
        }
        if(endDay.getValue() == null){
            astrix3.setText("*");
        }
    }

    /* 
     * Calculates the duration of the assignment in days (in option 2)
     * 
     * @author - Kris Vuong
     * @return - int containing the duration of the assigment in days
     */
    private int calculateDuration(Date endDate, Date startDate){
        long daysDuration = (endDate.getTime() - startDate.getTime())/86400000;  //.getTime() gives milliseconds since epoch (divide to get # of days)
        int days = (int)daysDuration;
        return days;
    }

    /* 
     * Calculates the number of days until the assignment begins (in option 2)
     * 
     * @author - Kris Vuong
     * @return - int containing the number of days until the assignment begins (negative int if assignment already started)
     */
    private int calculateTilToday (Date startDate){
        Date today = new Date();  //get present date
        long daysTilToday = (startDate.getTime() - today.getTime())/86400000;
        int days = (int)daysTilToday;
        return days;
    }

    /* 
     * Sets error message for invalid start/end date (in option 2)
     * 
     * @author - Kris Vuong
     */
    private void assignmentInvalid(){
        newAssignmentError.setText("The end date must come after the start date.");
        astrix2.setText("*");
        astrix3.setText("*");
    }

    /* 
     * Sets error message for invalid end date (in option 2)
     * 
     * @author - Kris Vuong
     */
    private void assignmentPassed(){
        newAssignmentError.setText("This assignment has already passed!");
        astrix3.setText("*");
    }

    /* 
     * Prints success message and writes info to CSV (in option 2)
     * 
     * @author - Michelle Chan
     */
    private void assignmentSuccess(){
        newAssignmentSuccess.setText("Saved succesfully!");
        
        Assignment assign1 = new Assignment();
        Calendar begin = Calendar.getInstance();
        Calendar due = Calendar.getInstance();
        begin.clear();
        due.clear();
        begin.set(startDay.getValue().getYear(), startDay.getValue().getMonthValue() - 1, startDay.getValue().getDayOfMonth());
        due.set(endDay.getValue().getYear(), endDay.getValue().getMonthValue() - 1, endDay.getValue().getDayOfMonth());
        
        assign1.setName(assignmentName.getText());
        assign1.setStartDate(begin);
        assign1.setEndDate(due);
        assign1.setTime("23:59");
        FileHandler.writeToCSV(assign1);
    }
    
    //Assignment info arrays needed by multiple methods
    String[] nameArr = new String[14];  //allocation = number of assignments the gantt chart can display at once
    String[] startArr = new String[14];
    String[] endArr = new String[14];

    /* 
     * Sets the width of each bar in the Gantt chart
     * 
     * @author - Kris Vuong
     * @throws IOException, ParseException
     */
    public void loadBars() throws IOException, ParseException{
        setAxisDates();

        rectangle1.setWidth(createBars(1, 1));
        rectangle1.setX(createBars(2, 1));
        
        rectangle2.setWidth(createBars(1, 2));
        rectangle2.setX(createBars(2, 2));
        
        rectangle3.setWidth(createBars(1, 3));
        rectangle3.setX(createBars(2, 3));
        
        rectangle4.setWidth(createBars(1, 4));
        rectangle4.setX(createBars(2, 4));

        rectangle5.setWidth(createBars(1,5));
        rectangle5.setX(createBars(2,5));

        rectangle6.setWidth(createBars(1,6));
        rectangle6.setX(createBars(2,6));

        rectangle7.setWidth(createBars(1,7));
        rectangle7.setX(createBars(2,7));

        rectangle8.setWidth(createBars(1,8));
        rectangle8.setX(createBars(2,8));

        rectangle9.setWidth(createBars(1,9));
        rectangle9.setX(createBars(2,9));

        rectangle10.setWidth(createBars(1,10));
        rectangle10.setX(createBars(2,10));

        rectangle11.setWidth(createBars(1,11));
        rectangle11.setX(createBars(2,11));

        rectangle12.setWidth(createBars(1,12));
        rectangle12.setX(createBars(2,12));

        rectangle13.setWidth(createBars(1,13));
        rectangle13.setX(createBars(2,13));
    }
    
    /* 
     * Sets the dates of the month along the bottom axis
     * 
     * @author - Kris Vuong
     */
    private void setAxisDates(){
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("dd");
        SimpleDateFormat monthOfYear = new SimpleDateFormat("MMMM");
        String date;

        day1.setText(dateOfMonth.format(today.getTime()));
        month1.setText(monthOfYear.format(today.getTime()));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day2.setText(date);

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day3.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day4.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day5.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day6.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day7.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day8.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day9.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day10.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day11.setText(String.valueOf(date));

        today.add(Calendar.DATE, 3);
        date = dateOfMonth.format(today.getTime());
        day12.setText(String.valueOf(date));
        month2.setText(monthOfYear.format(today.getTime()));
    }

    /* 
     * Displays assignment name, start date and end date when mouse hovers corresponding bar on Gantt chart
     * 
     * @author - Kris Vuong
     * @param MouseEvent event
     */
    public void showText1(MouseEvent event){
        ganttName.setText(nameArr[4]);
        ganttStart.setText(startArr[4]);
        ganttEnd.setText(endArr[4]);
    }
    public void showText2(MouseEvent event){
        ganttName.setText(nameArr[2]);
        ganttStart.setText(startArr[2]);
        ganttEnd.setText(endArr[2]);
    }
    public void showText3(MouseEvent event){
        ganttName.setText(nameArr[3]);
        ganttStart.setText(startArr[3]);
        ganttEnd.setText(endArr[3]);
    }
    public void showText4(MouseEvent event){
        ganttName.setText(nameArr[4]);
        ganttStart.setText(startArr[4]);
        ganttEnd.setText(endArr[4]);
    }
    public void showText5(MouseEvent event){
        ganttName.setText(nameArr[5]);
        ganttStart.setText(startArr[5]);
        ganttEnd.setText(endArr[5]);
    }
    public void showText6(MouseEvent event){
        ganttName.setText(nameArr[6]);
        ganttStart.setText(startArr[6]);
        ganttEnd.setText(endArr[6]);
    }
    public void showText7(MouseEvent event){
        ganttName.setText(nameArr[7]);
        ganttStart.setText(startArr[7]);
        ganttEnd.setText(endArr[7]);
    }
    public void showText8(MouseEvent event){
        ganttName.setText(nameArr[8]);
        ganttStart.setText(startArr[8]);
        ganttEnd.setText(endArr[8]);
    }
    public void showText9(MouseEvent event){
        ganttName.setText(nameArr[9]);
        ganttStart.setText(startArr[9]);
        ganttEnd.setText(endArr[9]);
    }
    public void showText10(MouseEvent event){
        ganttName.setText(nameArr[10]);
        ganttStart.setText(startArr[10]);
        ganttEnd.setText(endArr[10]);
    }
    public void showText11(MouseEvent event){
        ganttName.setText(nameArr[11]);
        ganttStart.setText(startArr[11]);
        ganttEnd.setText(endArr[11]);
    }
    public void showText12(MouseEvent event){
        ganttName.setText(nameArr[12]);
        ganttStart.setText(startArr[12]);
        ganttEnd.setText(endArr[12]);
    }
    public void showText13(MouseEvent event){
        ganttName.setText(nameArr[13]);
        ganttStart.setText(startArr[13]);
        ganttEnd.setText(endArr[13]);
    }

    /* 
     * Finds bar pixel length and x-position for each valid assigment to be displayed on the Gantt chart
     * 
     * @author - Kris Vuong
     * @param x - the pixel length is returned when x==1, and the x-position is returned for any other int
     * @param previous - the chronological order of the previous valid assignment to be displayed on the gantt chart (first assignment is 1, second assignment is 2...)
     * @throws IOException, ParseException
     * @return - double value of the bar pixel length OR the bar x-position
     */
    public double createBars(int x, int previous) throws IOException, ParseException{
        Scanner reader = new Scanner(new File(".\\assignment.csv"));
         
        //Set delimiter used in csv
        //reader.useDelimiter(",");

        int counter = 1;
        
            while (reader.hasNext()){
                String line = reader.next();
                String name = "";
                String startDay = "";
                String endDay = "";
                Date randomDay = SDF2("01-01-1980");  //arbitrary date

                //Find indices of delimiters (comma)
                int firstComma = line.indexOf(',');
                int secondComma = line.indexOf(',', firstComma+1);
                int thirdComma = line.indexOf(',', secondComma+1);

                //Substring the name, start and end of each assignment
                name = line.substring(0, firstComma);
                startDay = line.substring(firstComma+1, secondComma);
                endDay = line.substring(secondComma+1, thirdComma);

                //Parse string dates to Date type
                Date start = SDF2(startDay);
                Date end = SDF2(endDay);

                //Find days duration of each assignment
                int duration = calculateDuration(end, start);
                double dispDuration = duration*14.6666;  //days --> pixles (to scale)
                //Find days until start of each assignment
                int untilStart = calculateTilToday(start);
                double dispTilStart = untilStart*14.6666;  //days --> pixles (to scale)
                
                if(start.compareTo(randomDay) != 0 && end.compareTo(randomDay) != 0){  //if a date parse exception did not occur
                        if(untilStart<0 && untilStart + duration >0){
                            if(x == 1){
                                counter++;
                                if(counter == previous){
                                    nameArr[previous] = name;
                                    startArr[previous] = startDay;
                                    endArr[previous] = endDay;
                                    if(duration + untilStart > 33){
                                        return 483.9978;        //return bar that extends entire length of chart
                                    }
                                    return dispDuration + dispTilStart;
                                }
                            }
                            else{
                                counter++;
                                if(counter == previous){
                                    nameArr[previous] = name;
                                    startArr[previous] = startDay;
                                    endArr[previous] = endDay;
                                    return 0;
                                }
                            }
                        }
                        else if(untilStart > 0){
                            if(x == 1){
                                counter++;
                                if(counter == previous){
                                    nameArr[previous] = name;
                                    startArr[previous] = startDay;
                                    endArr[previous] = endDay;
                                    if(duration + untilStart > 33){
                                        return 469.3312-dispTilStart;
                                    }
                                    return dispDuration;
                                }
                            }
                            else{
                                counter++;
                                if(counter == previous){
                                    nameArr[previous] = name;
                                    startArr[previous] = startDay;
                                    endArr[previous] = endDay;
                                    return dispTilStart + 14.6666;
                                }
                            }
                        }
                        else if(untilStart == 0){
                            if(x == 1){
                                counter++;
                                if(counter == previous){
                                    nameArr[previous] = name;
                                    startArr[previous] = startDay;
                                    endArr[previous] = endDay;
                                    if(duration > 33){
                                        return 483.9978;
                                    }
                                    return dispDuration;
                                }
                            }
                            else{
                                counter++;
                                if(counter == previous){
                                    nameArr[previous] = name;
                                    startArr[previous] = startDay;
                                    endArr[previous] = endDay;
                                    return dispTilStart;
                                }
                            }
                        }
                }
            }
            return 0;
    }
}