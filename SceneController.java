import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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


