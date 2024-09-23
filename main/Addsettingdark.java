package Admin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Addsettingdark {

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
    private Button perfrence;

    @FXML
    private Button room;

    @FXML
    private Button currency;

    @FXML
    private Button editemployee;

    @FXML
    private AnchorPane perfrenceui;

    @FXML
    private RadioButton light;

    @FXML
    private RadioButton dark;

    @FXML
    private Button removeemployee;

    @FXML
    private Button addemployee;

    @FXML
    private Button changeposition;

    @FXML
    private Button changesalary;

    @FXML
    private TextField idinput;

    @FXML
    private Button enter;

    @FXML
    private TextField editname;

    @FXML
    private TextField editpassword;

    @FXML
    private TextField editid;

    @FXML
    private TextField editsalary;

    @FXML
    private TextField editposition;

    @FXML
    private RadioButton kyat;

    @FXML
    private RadioButton dollar;

    @FXML
    private RadioButton baht;

    @FXML
    private RadioButton yen;

    @FXML
    private Button toremoveemployee;

    @FXML
    private Button toeditemployee;

    @FXML
    private TextField roomid;

    @FXML
    private TextField editroomtype;

    @FXML
    private TextField editroomid;

    @FXML
    private TextField editroomprice;
    
    @FXML
    private AnchorPane editemployeeui;
    
    @FXML
    private AnchorPane editroomui;
    
    @FXML
    private AnchorPane currencyui;
    
    @FXML
    private AnchorPane idui;
    
    @FXML
    void addemployee(ActionEvent event) {
    	if(event.getSource()== addemployee) {
    		idui.setVisible(true);
    	}
    }

    @FXML
    void baht(ActionEvent event) {

    }

    @FXML
    void changeposition(ActionEvent event) {

    }

    @FXML
    void changesalary(ActionEvent event) {

    }

    @FXML
    void currecy(ActionEvent event) {
    	if(event.getSource()== currency) {
    		currencyui.setVisible(true);
    		perfrenceui.setVisible(false);
    		editroomui.setVisible(false);
    		editemployeeui.setVisible(false);
    		idui.setVisible(false);
    	}
    }

    @FXML
    void dark(ActionEvent event) {

    }

    @FXML
    void dollar(ActionEvent event) {

    }

    @FXML
    void editid(ActionEvent event) {

    }

    @FXML
    void editname(ActionEvent event) {

    }

    @FXML
    void editpassword(ActionEvent event) {

    }

    @FXML
    void editposition(ActionEvent event) {

    }

    @FXML
    void editroomid(ActionEvent event) {

    }

    @FXML
    void editroomprice(ActionEvent event) {

    }

    @FXML
    void editroomtype(ActionEvent event) {

    }

    @FXML
    void editsalary(ActionEvent event) {

    }

    @FXML
    void enter(ActionEvent event) {

    }

    @FXML
    void idinput(ActionEvent event) {

    }

    @FXML
    void kyat(ActionEvent event) {

    }

    @FXML
    void light(ActionEvent event) throws IOException {
    	 if(event.getSource()== light) {
    		  Parent root = FXMLLoader.load(getClass().getResource("setting.fxml"));
    			Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
    			Scene scene = new Scene(root);
    			stage.setScene(scene);
    			stage.setTitle("darksetting");
    			stage.show();
    	  }
    }

    @FXML
    void logouttomainpage3(ActionEvent event) {

    }
    @FXML
    void editemployee(ActionEvent event) {
    	if(event.getSource()== editemployee) {
    		editemployeeui.setVisible(true);
    		perfrenceui.setVisible(false);
    		editroomui.setVisible(false);
    		currencyui.setVisible(false);
    		
    	}
    }

    @FXML
    void perfrence(ActionEvent event) {
    	 	if(event.getSource()==perfrence) {
    	 		perfrenceui.setVisible(true);	
    	 		editemployeeui.setVisible(false);
    	 		editroomui.setVisible(false);
    	 		currencyui.setVisible(false);
    	 		idui.setVisible(false);
    	 	}
    	 
    }

    @FXML
    void removeemployee(ActionEvent event) {
    	if(event.getSource()== removeemployee) {
    		idui.setVisible(true);
    	}
    }

    @FXML
    void room(ActionEvent event) {
    	if(event.getSource()== room) {
    		editroomui.setVisible(true);
    		perfrenceui.setVisible(false);
    		editemployeeui.setVisible(false);
    		currencyui.setVisible(false);
    		idui.setVisible(false);
    	}
    }

    @FXML
    void roomid(ActionEvent event) {

    }
    
    void initialize() {
        perfrenceui.setVisible(false);
        editemployeeui.setVisible(false);
        editroomui.setVisible(false);
        currencyui.setVisible(false);
        idui.setVisible(false);
    }

    @FXML
    void switchtocustomer3(ActionEvent event) throws IOException {
    	if(event.getSource() == customer3) {
    		Parent root = FXMLLoader.load(getClass().getResource("customerdark.fxml"));
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void switchtodashboard3(ActionEvent event) throws IOException {
    	if(event.getSource()== dashboard3) {
    		Parent root = FXMLLoader.load(getClass().getResource("AdminPagedark.fxml"));
    		Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setTitle("Employee");
    		stage.show();
    	}
    }

    @FXML
    void switchtoemployee3(ActionEvent event) throws IOException {
    	if(event.getSource() == employee3) {
    		Parent root = FXMLLoader.load(getClass().getResource("AddEmployeedark.fxml"));
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void switchtosetting3(ActionEvent event) {

    }

    @FXML
    void toeditemployee(ActionEvent event) {

    }

    @FXML
    void toremoveemployee(ActionEvent event) {

    }

    @FXML
    void yen(ActionEvent event) {

    }

}
