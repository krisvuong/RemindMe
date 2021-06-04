/*
 * Date: June 4, 2021
 * Names: Kris, Teja, Michelle Chan, Michelle Lau 
 * Teacher: Mr. Ho
 * Description: Final Project - RemindMe
 * 
 * */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml")); 
            Scene scene = new Scene(root); 
            stage.setTitle("RemindMe"); 
            stage.setScene(scene); 
            stage.show(); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
