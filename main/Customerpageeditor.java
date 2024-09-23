package Admin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Customerpageeditor {

    @FXML
    private Button dashboard3;

    @FXML
    private Button customer3;

    @FXML
    private Button employee3;

    @FXML
    private Button logout3;

    @FXML
    private Button setting3;

    @FXML
    void logouttomainpage3(ActionEvent event) {

    }

    @FXML
    void switchtocustomer3(ActionEvent event) {

    }

    @FXML
    void switchtodashboard3(ActionEvent event) throws IOException {
    	if(event.getSource()== dashboard3) {
    		Parent root = FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setTitle("Customer");
    		stage.show();
    	}
    }

    @FXML
    void switchtoemployee3(ActionEvent event) throws IOException{
    	if(event.getSource()== employee3) {
    		Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
    		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void switchtosetting3(ActionEvent event) throws IOException {
    	if(event.getSource() == setting3) {
    		Parent root = FXMLLoader.load(getClass().getResource("setting.fxml"));
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

}
