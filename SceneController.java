import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.Group;


public class SceneController{

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

    //Other necessary variables
    int daysToStart;
    int daysAssignmentDuration;

    // Main Scene Functions
    public void switchScene1(ActionEvent event) throws IOException {
        /*
        Alert alert2 = new Alert(AlertType.CONFIRMATION);
        alert2.setTitle("Return");
        alert2.setHeaderText("You haven't filled out all the required info yet!");
        alert2.setContentText("Are you sure you want to leave?");
        */
        String date = datePicker.getValue().toString();
        String time = timePick.getValue().toString();
        String name = txtField.getText();
        Alert alert = new Alert(AlertType.INFORMATION);
        // need to get the value of Assignment name and design "Minutes prior"
        alert.setContentText("assignment name:" + name + "\nDate:" + date + "\ntime: " + time + "\nMinutes prior");
        alert.setTitle("Thank you for using our program");
        alert.setHeaderText("Your Assignment has been submitted. \nThe Following information will be recorded");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

        //if(alert2.showAndWait().get() == ButtonType.OK){
            root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        //}
        }
    }
    
    public void switchScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("schedule.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ganttChart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

    public void switchScene4(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene5(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("newAssignment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchScene6(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("allReminders.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Schedule Scene FUnctions
    @FXML
    void selectDate(ActionEvent event) {
        //lbDate.setText(datePicker.getValue().toString());
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

    public long validationDate() {
        long diffDays = 0;
        String inputDate = "";
        Duration dateDifference = null;
        if (datePicker.getValue() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Re-enter your time after selecting the date");
            alert.setTitle("Select Date");
            alert.setHeaderText("Please select the date first");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

            }
        } else if (datePicker.getValue() != null) {
            inputDate = datePicker.getValue().toString();
            LocalDate inputDateCalc = LocalDate.parse(inputDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate currentDate = LocalDate.now();
            dateDifference = Duration.between(currentDate.atStartOfDay(), inputDateCalc.atStartOfDay());
            diffDays = (dateDifference.toMillis());

        }

        return diffDays;
    }

    public long validationTime() {
        String futureTime = (timePick.getValue().toString());
        LocalTime inputTimeCalc = LocalTime.parse(futureTime);
        LocalTime currentTime = LocalTime.now();
        Duration timeDifference = Duration.between(inputTimeCalc, currentTime);
        long timeDiff = (timeDifference.toMillis());
        if (timeDiff > 0 || timeDiff < 0) {
            timeDiff = timeDiff * -1;
        }
        return timeDiff;
    }

    private void clearDate() {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Please make sure to enter a valid date!");
        alert.setTitle("Error on date");
        alert.setHeaderText("The date you entered is in the past");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            datePicker.getEditor().clear();
        }
    }

    @FXML
    void selectTime(ActionEvent event) {
        Long diffDays = validationDate();
        long timeDiff = validationTime();
        Long timeDifference = diffDays + timeDiff;
        if (timeDifference < 0) {
            clearTime();
        }

    }

    private void clearTime() {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Please make sure to enter a valid Time after choosing your date");
        alert.setTitle("Error on Time");
        alert.setHeaderText("The time you entered is invalid for your inputted date!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        }
    }



    //New Assignment Option - validation and return duration
    public void assignmentDuration(ActionEvent event) throws IOException {
        //Reset error/success messages
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
            Date today = new Date();  //get present date

            //Create date and time format (template)
            SimpleDateFormat simple = new SimpleDateFormat ("yyyy-MM-dd");

            //Parse String input dates into Date types
            try{
                startDate = simple.parse(start);
                endDate = simple.parse(end);
            }
            catch(Exception e){
            }

            //Calculate total days until start, and duration of assignment
            daysDuration = calculateDuration(endDate, startDate);
            daysTilStart = calculateTilToday(startDate, today);

            //Print error if end date comes before start date
            if(daysDuration<0){
                newAssignmentError.setText("The end date must come after the start date.");
            }
            else if(today.after(endDate)){
                newAssignmentError.setText("This assignment has already passed!");
            }
            //Print success message
            else{
                newAssignmentError.setText("");
                newAssignmentSuccess.setText("Saved succesfully!");
                
                ///////////////////////////////////////////////////////////////////
                ////// CALL METHOD TO WRITE INFO INTO CSV  ////////////////////////
            }
        }
    }

    //Check for missing fields in newAssignment (option 2)
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

    //Calculate duration of assignment (option 2)
    private int calculateDuration(Date endDate, Date startDate){
        long daysDuration = (endDate.getTime() - startDate.getTime())/86400000;  //.getTime() gives milliseconds since July 1 1970 (divide to get # of days)
        int days = (int)daysDuration;
        return days;
    }

    //Calculate days until the assignment starts (option 2)
    private int calculateTilToday (Date startDate, Date today){
        long daysTilToday = (startDate.getTime() - today.getTime())/86400000;
        int days = (int)daysTilToday;
        return days;
    }


    //Gant Chart Functions
    /*private Button weekslb(String text) {
        Button button = new Button();
        Label labell = new Label(text);
        labell.setRotate(-90);
        button.setGraphic(new Group(label));

        button.setStyle(
            "-fx-base: white; " +
            "-fx-font-size: 12px; " 
        );
        return button;
    } */

}


