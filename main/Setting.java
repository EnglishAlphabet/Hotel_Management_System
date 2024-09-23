package Admin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Setting  extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("setting.fxml"));
	            Scene scene = new Scene(root);
	            primaryStage.setScene(scene);
	            scene.getStylesheets().add(getClass().getResource("setting.css").toExternalForm());
	            primaryStage.setTitle("Setting");
	            primaryStage.show();
	            primaryStage.setResizable(false);
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("Error loading FXML file: " + e.getMessage());
	        }
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
		
	}


