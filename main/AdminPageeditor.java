package Admin;

import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class AdminPageeditor  {

    @FXML
    private Button dashboard;

    @FXML
    private Button customer;

    @FXML
    private Button employee;

    @FXML
    private Button logout;

    @FXML
    private Button setting;

    @FXML
    private LineChart<?, ?> linechard;

    @FXML
    private PieChart piechard;
    
    @FXML
    private PieChart piechard1;

    @FXML
    private TableColumn<?, ?> reservation;

    @FXML
    private AnchorPane employeeschedule;


    @FXML
    void logouttomainpage(ActionEvent event) {
        // Implement your action here
    }
   
    @FXML
    void switchtocustomer(ActionEvent event) throws IOException {
        if(event.getSource()== customer) {
        	Parent root = FXMLLoader.load(getClass().getResource("customer.fxml"));
        	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        	Scene scene = new Scene(root);
        	stage.setScene(scene);
        	stage.show();
        }
    }

    @FXML
    void switchtodashboard(ActionEvent event) {
        // Implement your action here
    }

    @FXML
    void switchtosetting(ActionEvent event) throws IOException {
    	if(event.getSource() == setting) {
    		Parent root = FXMLLoader.load(getClass().getResource("setting.fxml"));
    		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }

    @FXML
    void switchtoemployee(ActionEvent event) throws IOException {
    	if(event.getSource()== employee) {
    		Parent root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml"));
    		Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.setTitle("Employee");
    		stage.show();
    	}
    	}
    

    	
    }
    


