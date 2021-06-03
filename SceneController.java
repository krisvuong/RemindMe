import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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

    // Gant Chart Functions
    /*
     * private Button weekslb(String text) { Button button = new Button(); Label
     * labell = new Label(text); labell.setRotate(-90); button.setGraphic(new
     * Group(label));
     * 
     * button.setStyle( "-fx-base: white; " + "-fx-font-size: 12px; " ); return
     * button; }
     */

}