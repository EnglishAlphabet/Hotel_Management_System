package Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class AddEmployeedark extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddEmployeedark.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("foremployeedark.css").toExternalForm());
            primaryStage.setTitle("Add Employee");
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
