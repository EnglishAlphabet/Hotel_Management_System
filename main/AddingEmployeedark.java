package Admin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddingEmployeedark {

    @FXML
    private Button dashboard2;

    @FXML
    private Button customer2;

    @FXML
    private Button employee2;

    @FXML
    private Button logout2;

    @FXML
    private Button setting2;

    @FXML
    private TextField searchbar;

    @FXML
    private AnchorPane newemployee;

    @FXML
    private TextField addname;

    @FXML
    private TextField addage;

    @FXML
    private TextField addnrc;

    @FXML
    private TextField addemail;

    @FXML
    private TextField addphone;

    @FXML
    private Button submit;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> position;

    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private TableColumn<?, ?> gender;

    @FXML
    private TableColumn<?, ?> salary;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> phoneno;

    @FXML
    void addage(ActionEvent event) {

    }

    @FXML
    void addemail(ActionEvent event) {

    }

    @FXML
    void addname(ActionEvent event) {

    }

    @FXML
    void addnrc(ActionEvent event) {

    }

    @FXML
    void addphne(ActionEvent event) {

    }

    @FXML
    void logouttomainpage2(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void submit(ActionEvent event) {

    }

    @FXML
    void switchtocustomer2(ActionEvent event) throws IOException {
    	if(event.getSource() == customer2) {
    		Parent root = FXMLLoader.load(getClass().getResource("customerdark.fxml"));
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void switchtodashboard2(ActionEvent event) throws IOException {
    	if(event.getSource()== dashboard2) {
    		Parent root = FXMLLoader.load(getClass().getResource("AdminPagedark.fxml"));
    		Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setTitle("Employee");
    		stage.show();
    	}
    }
@FXML
    void switchtosetting2(ActionEvent event) throws IOException {
    	if(event.getSource() == setting2) {
    		Parent root = FXMLLoader.load(getClass().getResource("settingdark.fxml"));
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void switchtoemployee2(ActionEvent event) {

    }

    

}
