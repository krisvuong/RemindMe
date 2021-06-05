import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class SceneController {

    // Main Scene Variables
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Button exitBtn;

    @FXML
    private ColorPicker color;

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
    private TextField txtField;
    @FXML
    private TextField txtField1;
    // New Assignment Variables
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

    // Gantt Chart Variables
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
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method recives all of the user's inputted values,
     *               calculates the total Duration (current time till the user's
     *               inputted time), sets a pop up notification based on the user's
     *               time (using a timerTask) in order to remind the user, and sends
     *               all of the user input to the reminder.csv file.
     *
     *               <p>
     * @peram none
     * @return: none
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
            // the user (long timeDuration. This variable stores value in milliseconds)
            // minus
            // number of minutes in advance the user would
            // like the pop up to occur (convert this value to milliseconds too).
            long totalDuration = timeDuration - (minAheadCalc * 60000);
            // If the entered "minutes in advance" has a value higher than variable
            // "timeDuration", display an error pop up.
            // Since duration can't be in negatives, and clear the input field.
            if (totalDuration < 0) {
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.setTitle("Input Time Wrong");
                alert2.setHeaderText("The number you've enter for 'Minutes in Advance' or your time input is invalid!");
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

                    /**
                     * @authod: Michelle lau
                     */
                    // goes back to the main menu once the user presses "OK" for the final sumbit.
                    root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    /**
                     * @author: Michelle Chan
                     */
                    // Sends the values (assignment name, start date, end date, and time)
                    // into the Reminder.csv file, using michelle chan's code.
                    Reminder reminder = new Reminder();
                    reminder.setName(name);
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(datePicker.getValue().getYear(), datePicker.getValue().getMonthValue() - 1,
                            datePicker.getValue().getDayOfMonth());
                    reminder.setEndDate(calendar);
                    reminder.setStartDate(Calendar.getInstance());
                    reminder.setTime(time);
                    FileHandler.writeToCSV(reminder);

                    // This else feature allows the user to edit any changes of the inputs before
                    // final submit
                } else {
                }
            }
        }

    }

    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method sends a notification to the user based on the time
     *               he/she wants to be reminded.
     *
     *               <p>
     * @peram none
     * @return: none
     */
    class RemindTask extends TimerTask {
        String name = txtField.getText();
        String minAhead = txtField1.getText();

        public void run() {
            try {
                // Initialize SystemTray
                SystemTray tray = SystemTray.getSystemTray();

                // This will generate a pixilated icon for the notification
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                // initialize the TrayIcon
                TrayIcon trayIcon = new TrayIcon(image, "RemindMe");
                // allows system resize the image if needed
                trayIcon.setImageAutoSize(true);
                // Set tooltip text for the tray icon
                trayIcon.setToolTip("RemindMe Notification");
                tray.add(trayIcon);

                // Display info notification:
                trayIcon.displayMessage(name, "Is due in " + minAhead + " Minutes", MessageType.INFO);

            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }

    public void switchScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("schedule.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene3(ActionEvent event) throws IOException {
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

    /**
     * @author: Teja
     * @description: this method would be called when the user clicks "all
     *               Reminders". this method then calls a method called
     *               "chartMarker", which is located in a different class called
     *               "allRemindersFile()"
     * @param event
     * @throws IOException
     */
    public void switchScene6(ActionEvent event) throws IOException {
        new allRemindersFile().chartMaker();

    }

    // Schedule Scene FUnctions
    /**
     * @author: Teja
     * @Description: calls an method called validationDate() and stores the value
     *               into a variable with a data type long called "diffDays". If the
     *               date which is inputted is in the past it would call another
     *               method called "clearDate()"
     * @param event
     * 
     */
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
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method validates if the user entered a date of not. After
     *               confirming the user has entered a valid date, it would find the
     *               duration between the current date and the date inputted by the
     *               user, convert that duration value into milliseconds and return
     *               the value (which would be used for the timerTask)
     *
     *               <p>
     * @peram none
     * @return: diffDays
     */
    public long validationDate() {
        long diffDays = 0;
        String inputDate = "";
        Duration dateDifference = null;

        // calculates the duration between the
        // current date, and the date entered by the user. If the datePicker value is
        // null, the
        // program displays an error
        if (datePicker.getValue() != null) {
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

    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method calculates the duration between the currentTime and
     *               the inputted time of the user and converts the value in
     *               milliseconds (which would then be used for the the timeTasker
     *               calculation )
     *
     *               <p>
     * @peram none
     * @return: timeDiff
     */
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

    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method sends a pop up error idicating that the user's
     *               inputted date is in the past, thus it isn't valid. it then
     *               clears the text area, for the user to input a valid date.
     *
     */
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

    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method called 2 validation methods, validaitonDate() and
     *               validationTime() and stores the values in the variables with
     *               the data type long. It then adds the 2 values to get the
     *               timeDuration and returns it.
     *
     *               <p>
     * @peram none
     * @return: timeDuration
     */
    public long timeDifference() {
        // Calls validation time and Validation date, in order to receive the validated
        // values
        Long diffDays = validationDate();
        long timeDiff = validationTime();
        // adds both the values, in order to find the combined duration
        Long timeDuration = diffDays + timeDiff;
        return timeDuration;
    }

    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method calls another method called "timeDifference()" and
     *               stores it's value in a variable with the data type long called
     *               timeDuration. If the duration is negative, this method calls
     *               anther bethod called "clearTime()"
     *
     * @param: event
     * @return: none
     */
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

    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method sends an error pop up if the user entered a time
     *               which is in the past.
     */
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
     * Handles new assignment option from main menu (option 2)
     * 
     * @author - Kris Vuong
     * 
     * @param ActionEvent event (user presses SUBMIT)
     * 
     * @throws IOException
     */
    public void assignmentDuration(ActionEvent event) throws IOException {
        // Reset previous error and success messages
        astrix1.setText("");
        astrix2.setText("");
        astrix3.setText("");
        newAssignmentError.setText("");
        newAssignmentSuccess.setText("");

        // Check for empty fields
        if (assignmentName.getText().equals("") || startDay.getValue() == null || endDay.getValue() == null) {
            missingInfo(); // sets error message
        }

        else {
            // Get user-inputted start and end dates
            String start = startDay.getValue().toString();
            String end = endDay.getValue().toString();

            // Declare necessary variables
            long daysDuration; // duration of assignment (in days)
            Date startDate = new Date();// initialize start date
            Date endDate = new Date(); // initialize end date
            Date today = new Date(); // initialize current date

            // Parse and format string dates to Date types
            startDate = SDF1(start);
            endDate = SDF1(end);

            // Calculate duration of assignment
            daysDuration = calculateDuration(endDate, startDate); // returns double with number of days

            // Set success or error messages
            if (daysDuration < 0) {
                assignmentInvalid(); // error (if the due date comes before the start date)
            } else if (today.after(endDate)) {
                assignmentPassed(); // error (if the due date already passed)
            }
            // Print success message
            else {
                assignmentSuccess(); // success
            }
        }
    }

    /*
     * Sets error message and asterisks for empty fields (in option 2)
     * 
     * @author - Kris Vuong
     */
    private void missingInfo() {
        // Set error message
        newAssignmentError.setText("Please enter information in all fields");

        // Add red asterisk next to empty fields
        if (assignmentName.getText().equals("")) {
            astrix1.setText("*");
        }
        if (startDay.getValue() == null) {
            astrix2.setText("*");
        }
        if (endDay.getValue() == null) {
            astrix3.setText("*");
        }
    }

    /*
     * Parses a String value to a Date type
     * 
     * @author - Kris Vuong
     * 
     * @param ActionEvent event
     * 
     * @throws - IOException
     * 
     * @return - Date in yyyy-MM-dd format
     */
    private Date SDF1(String strDate) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd"); // create Date format
        Date date = new Date(); // initialize date

        // Parse String input dates into Date types
        try {
            date = simple.parse(strDate);
        } catch (Exception e) {
        }
        return date;
    }

    /*
     * Parses a String value to a Date type
     * 
     * @author - Kris Vuong
     * 
     * @param ActionEvent event
     * 
     * @throws - IOException
     * 
     * @return - Date in dd-MM-yyyy format
     */
    private Date SDF2(String strDate) throws ParseException {
        SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy"); // create Date format
        Date date = new Date(); // initialize date

        // Parse String input dates into Date types
        try {
            date = simple.parse(strDate);
            return date;
        } catch (Exception e) {
            date = simple.parse("01-01-1980"); // returns arbitrary date if error occurs (this is checked and skipped if
                                               // it occurs)
            return date;
        }
    }

    /*
     * Calculates the duration of the assignment in days (in option 2)
     * 
     * @author - Kris Vuong
     * 
     * @return - int containing the duration of the assigment in days
     */
    private int calculateDuration(Date endDate, Date startDate) {
        long daysDuration = (endDate.getTime() - startDate.getTime()) / 86400000; // getTime() gives milliseconds since
                                                                                  // epoch (divide to get # of days)
        int days = (int) daysDuration;
        return days;
    }

    /*
     * Calculates the number of days until the assignment begins (in option 2)
     * 
     * @author - Kris Vuong
     * 
     * @return - int containing the number of days until the assignment begins
     * (negative int if assignment already started)
     */
    private int calculateTilToday(Date startDate) {
        Date today = new Date(); // get present date
        long daysTilToday = (startDate.getTime() - today.getTime()) / 86400000;
        int days = (int) daysTilToday;
        return days;
    }

    /*
     * Sets error message for invalid start/end date (in option 2)
     * 
     * @author - Kris Vuong
     */
    private void assignmentInvalid() {
        newAssignmentError.setText("The end date must come after the start date."); // set error message
        astrix2.setText("*"); // set asterisks next to invalid fields
        astrix3.setText("*");
    }

    /*
     * Sets error message for invalid end date (in option 2)
     * 
     * @author - Kris Vuong
     */
    private void assignmentPassed() {
        newAssignmentError.setText("This assignment has already passed!"); // set error message
        astrix3.setText("*"); // set asterisk next to invalid due date
    }

    /*
     * Prints success message and writes info to CSV (in option 2)
     * 
     * @author - Michelle Chan
     */
    private void assignmentSuccess() {
        newAssignmentSuccess.setText("Saved succesfully!"); // set success message

        // Write all assignment information to .\\assignment.csv
        Assignment assign1 = new Assignment();
        Calendar begin = Calendar.getInstance();
        Calendar due = Calendar.getInstance();
        begin.clear();
        due.clear();
        begin.set(startDay.getValue().getYear(), startDay.getValue().getMonthValue() - 1,
                startDay.getValue().getDayOfMonth());
        due.set(endDay.getValue().getYear(), endDay.getValue().getMonthValue() - 1, endDay.getValue().getDayOfMonth());

        assign1.setName(assignmentName.getText());
        assign1.setStartDate(begin);
        assign1.setEndDate(due);
        assign1.setTime("23:59");
        FileHandler.writeToCSV(assign1);
    }

    // Assignment informaton arrays needed by multiple methods
    String[] nameArr = new String[14]; // allocation = number of assignments the gantt chart can display at once
    String[] startArr = new String[14];
    String[] endArr = new String[14];

    /*
     * Sets the width of each bar in the Gantt chart (when user presses RELOAD)
     * 
     * @author - Kris Vuong
     * 
     * @throws IOException, ParseException
     */
    public void loadBars() throws IOException, ParseException {
        // Sets dates on the bottom axis, starting from current date
        setAxisDates();

        // Set width of bar (duration of assignment) and the displacement from the
        // vertical axis (ex. if assignment starts in 5 days)
        rectangle1.setWidth(createBars(1, 1)); // pass "1" to receive width of bar
        rectangle1.setX(createBars(2, 1)); // pass "2" to receive displacement of bar

        rectangle2.setWidth(createBars(1, 2));
        rectangle2.setX(createBars(2, 2));

        rectangle3.setWidth(createBars(1, 3));
        rectangle3.setX(createBars(2, 3));

        rectangle4.setWidth(createBars(1, 4));
        rectangle4.setX(createBars(2, 4));

        rectangle5.setWidth(createBars(1, 5));
        rectangle5.setX(createBars(2, 5));

        rectangle6.setWidth(createBars(1, 6));
        rectangle6.setX(createBars(2, 6));

        rectangle7.setWidth(createBars(1, 7));
        rectangle7.setX(createBars(2, 7));

        rectangle8.setWidth(createBars(1, 8));
        rectangle8.setX(createBars(2, 8));

        rectangle9.setWidth(createBars(1, 9));
        rectangle9.setX(createBars(2, 9));

        rectangle10.setWidth(createBars(1, 10));
        rectangle10.setX(createBars(2, 10));

        rectangle11.setWidth(createBars(1, 11));
        rectangle11.setX(createBars(2, 11));

        rectangle12.setWidth(createBars(1, 12));
        rectangle12.setX(createBars(2, 12));

        rectangle13.setWidth(createBars(1, 13));
        rectangle13.setX(createBars(2, 13));
    }

    /*
     * Sets the dates of the month along the bottom axis
     * 
     * @author - Kris Vuong
     */
    private void setAxisDates() {
        Calendar today = Calendar.getInstance(); // get current date
        SimpleDateFormat dateOfMonth = new SimpleDateFormat("dd"); // format to only display date (ex. July 27 2020 -->
                                                                   // "27")
        SimpleDateFormat monthOfYear = new SimpleDateFormat("MMMM"); // format to only display month (ex. July 27 2020
                                                                     // --> "July")
        String date;

        day1.setText(dateOfMonth.format(today.getTime())); // set first date as current date
        month1.setText(monthOfYear.format(today.getTime())); // set starting month as current month

        // Set dates as 3 days later than previous date
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
     * Displays assignment information when mouse hovers over corresponding bar on
     * Gantt chart
     * 
     * @author - Kris Vuong
     * 
     * @param MouseEvent event
     */
    public void showText1(MouseEvent event) {
        ganttName.setText(nameArr[1]);
        ganttStart.setText(startArr[1]);
        ganttEnd.setText(endArr[1]);
    }

    public void showText2(MouseEvent event) {
        ganttName.setText(nameArr[2]);
        ganttStart.setText(startArr[2]);
        ganttEnd.setText(endArr[2]);
    }

    public void showText3(MouseEvent event) {
        ganttName.setText(nameArr[3]);
        ganttStart.setText(startArr[3]);
        ganttEnd.setText(endArr[3]);
    }

    public void showText4(MouseEvent event) {
        ganttName.setText(nameArr[4]);
        ganttStart.setText(startArr[4]);
        ganttEnd.setText(endArr[4]);
    }

    public void showText5(MouseEvent event) {
        ganttName.setText(nameArr[5]);
        ganttStart.setText(startArr[5]);
        ganttEnd.setText(endArr[5]);
    }

    public void showText6(MouseEvent event) {
        ganttName.setText(nameArr[6]);
        ganttStart.setText(startArr[6]);
        ganttEnd.setText(endArr[6]);
    }

    public void showText7(MouseEvent event) {
        ganttName.setText(nameArr[7]);
        ganttStart.setText(startArr[7]);
        ganttEnd.setText(endArr[7]);
    }

    public void showText8(MouseEvent event) {
        ganttName.setText(nameArr[8]);
        ganttStart.setText(startArr[8]);
        ganttEnd.setText(endArr[8]);
    }

    public void showText9(MouseEvent event) {
        ganttName.setText(nameArr[9]);
        ganttStart.setText(startArr[9]);
        ganttEnd.setText(endArr[9]);
    }

    public void showText10(MouseEvent event) {
        ganttName.setText(nameArr[10]);
        ganttStart.setText(startArr[10]);
        ganttEnd.setText(endArr[10]);
    }

    public void showText11(MouseEvent event) {
        ganttName.setText(nameArr[11]);
        ganttStart.setText(startArr[11]);
        ganttEnd.setText(endArr[11]);
    }

    public void showText12(MouseEvent event) {
        ganttName.setText(nameArr[12]);
        ganttStart.setText(startArr[12]);
        ganttEnd.setText(endArr[12]);
    }

    public void showText13(MouseEvent event) {
        ganttName.setText(nameArr[13]);
        ganttStart.setText(startArr[13]);
        ganttEnd.setText(endArr[13]);
    }

    /*
     * Finds bar pixel length and displacement from vertical axis for each valid
     * assigment to be displayed on the Gantt chart
     * 
     * @author - Kris Vuong
     * 
     * @param x - the pixel length is returned when x==1, and the x-position is
     * returned for any other int
     * 
     * @param previous - the chronological order of the previous valid assignment to
     * be displayed on the gantt chart (first assignment is 1, second assignment is
     * 2...)
     * 
     * @throws IOException, ParseException
     * 
     * @return - double value of the bar pixel length OR the bar x-position
     */
    public double createBars(int x, int previous) throws IOException, ParseException {
        String fileName = ".\\assignment.csv";
        Scanner reader = new Scanner(new File(fileName)); // scan through assignments file

        // Records number of assignments displayed on the gantt chart (excludles invalid
        // assignments, ex. due date has already passed)
        int counter = 1;

        // Reads each row until end of file
        while (reader.hasNext()) {
            // Declare variables
            String line = reader.next(); // store current line of text as a String
            line = line.replaceAll("\\s+","");
            String name = "";
            String startDay = "";
            String endDay = "";
            int duration; // number of days duration
            double dispDuration; // scaled to number of pixels
            int untilStart; // number of days until start
            double dispTilStart; // scaled to number of pixels
            Date randomDay = SDF2("01-01-1980"); // arbitrary date (used for checking for errors -- these cases are
                                                 // ignored)

            // Find indices of delimiters (comma)
            int firstComma = line.indexOf(',', 0);
            int secondComma = line.indexOf(',', firstComma + 1);
            int thirdComma = line.indexOf(',', secondComma + 1);

            // Substring the name, start and end of each assignment
            name = line.substring(0, firstComma);
            startDay = line.substring(firstComma + 1, secondComma);
            endDay = line.substring(secondComma + 1, thirdComma);

            // Parse string dates to Date type
            Date start = SDF2(startDay);
            Date end = SDF2(endDay);

            // Find days duration of each assignment
            duration = calculateDuration(end, start);
            dispDuration = duration * 14.6666; // days --> pixles (to scale)

            // Find days until start of each assignment
            untilStart = calculateTilToday(start);
            dispTilStart = untilStart * 14.6666; // days --> pixles (to scale)

            // If parse exception did not occur
            if (start.compareTo(randomDay) != 0 && end.compareTo(randomDay) != 0) {

                // if the start is in the past, but the due date is in the future
                if (untilStart < 0 && untilStart + duration > 0) {

                    // return the duration
                    if (x == 1) {
                        counter++;
                        if (counter == previous) {
                            nameArr[previous] = name;
                            startArr[previous] = startDay;
                            endArr[previous] = endDay;
                            if (duration + untilStart > 33) {
                                return 483.9978; // return bar that extends entire length of chart
                            }
                            return dispDuration + dispTilStart;
                        }
                    }
                    // return the displacement from vertical axis
                    else {
                        counter++;
                        if (counter == previous) {
                            nameArr[previous] = name;
                            startArr[previous] = startDay;
                            endArr[previous] = endDay;
                            return 0;
                        }
                    }
                }

                // if the start is in the future
                else if (untilStart > 0) {

                    // return the duration
                    if (x == 1) {
                        counter++;
                        if (counter == previous) {
                            nameArr[previous] = name;
                            startArr[previous] = startDay;
                            endArr[previous] = endDay;
                            if (duration + untilStart > 33) {
                                return 469.3312 - dispTilStart; // format to fit the gantt chart
                            }
                            return dispDuration;
                        }
                    }

                    // return the displacement form the vertical axis
                    else {
                        counter++;
                        if (counter == previous) {
                            nameArr[previous] = name;
                            startArr[previous] = startDay;
                            endArr[previous] = endDay;
                            return dispTilStart + 14.6666;
                        }
                    }
                }

                // if the start is today
                else if (untilStart == 0) {

                    // return the duration
                    if (x == 1) {
                        counter++;
                        if (counter == previous) {
                            nameArr[previous] = name;
                            startArr[previous] = startDay;
                            endArr[previous] = endDay;
                            if (duration > 33) {
                                return 483.9978; // format to fit the gantt chart
                            }
                            return dispDuration;
                        }
                    }

                    // return the displacement from the vertical axis
                    else {
                        counter++;
                        if (counter == previous) {
                            nameArr[previous] = name;
                            startArr[previous] = startDay;
                            endArr[previous] = endDay;
                            return dispTilStart;
                        }
                    }
                }
            }
        }
        return 0; // arbitrary return value
    }

}
