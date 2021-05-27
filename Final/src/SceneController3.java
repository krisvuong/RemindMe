import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class SceneController3 implements Initializable{
    
    @FXML
    private ChoiceBox<String> timePick;
    private String[] time = {"12:00","13:00","14:00"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        timePick.getItems().addAll(time);
        
    }

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lbDate;

    @FXML
    void selectDate(ActionEvent event) {
        lbDate.setText(datePicker.getValue().toString());
    }

    
    @FXML
    private Label label;
}
