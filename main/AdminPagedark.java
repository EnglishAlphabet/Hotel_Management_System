package Admin;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class AdminPagedark extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AdminPagedark.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("AdminPagedark.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch(Exception e) {
			throw new Exception(e);
		}
		
	}
	

	public static void main(String[] args) {
		launch(args);
	}

	
	}
 

