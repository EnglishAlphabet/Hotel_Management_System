package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class adminServiceView extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage st) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("adminService.fxml"));
	
		Scene sc = new Scene(root);
		sc.getStylesheets().add(getClass().getResource("/view/adminServiceViewCon.css").toExternalForm());
		st.setScene(sc);
		st.show();
	}
	
}
