package Admin;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

public class settingsController {

    @FXML
    private Button dashboard;

    @FXML
    private Button guests;

    @FXML
    private Button bookings;

    @FXML
    private Button logout;

    @FXML
    private Button services;

    @FXML
    private Button setting;
    
    @FXML 
    private Button add;
    
    @FXML 
    private Button remove;
    
    @FXML
    private Button edit;

    private static final String PREFERENCE_NODE_NAME = "beta.hotel.preferences";
    private static final String THEME_KEY = "theme";
    private static final String USER_KEY = "user";
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USER = "root";
    private static final String PASS = ""; 
    private ObservableList<Employee> employeeList;

    private String theme;
    private String userID;
    private themeSettings ts= new themeSettings();


    @FXML
    private RadioButton light;

    @FXML
    private RadioButton dark;

    @FXML
    private Button path_button;

    @FXML
    private TableView<Employee> employeetable;

    @FXML
    private TableColumn<Employee, String> id;

    @FXML
    private TableColumn<Employee, String> name;

    @FXML
    private TableColumn<Employee, String> role;
    
    @FXML
    private TableColumn<Employee, String> status;

    @FXML
    private TableColumn<Employee, String> email;

    @FXML
    private TableColumn<Employee, String> phoneno;


    @FXML
    private Button audit_view;

    @FXML
    private Button cancel;

    @FXML
    private Button save;

    @FXML
    void dark(ActionEvent event) {

    }

    @FXML
    void light(ActionEvent event) {

    }

    @FXML
    void saveSettings(ActionEvent event) {
        saveWindowsSettings();
        Parent root = ((Node) event.getSource()).getScene().getRoot();
        ts.applySettings(root, "settings");
    }

    private void loadWindowsSettings(){
        Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);

        this.theme = userPref.get(THEME_KEY, "light");
        this.userID = userPref.get(USER_KEY, "Unauthorized");
    }

    private void saveWindowsSettings(){
        Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);

        if(dark.isSelected()) userPref.put(THEME_KEY, "dark");
        else if (light.isSelected()) userPref.put(THEME_KEY, "light"); {

        }

    }
    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {

//        try{
//            URL path = new File("src/main/resources/beta/hotelver1/dashboard.fxml").toURI().toURL();
//            Parent root = FXMLLoader.load(path);
//            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//            ts.applySettings(root, "dashboard");
//
//            stage.setResizable(true);
//            stage.getScene().setRoot(root);
//            stage.show();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    	System.out.println("NO dashboard");
    }

    @FXML
    void switchtobookings(ActionEvent event) {

    }
    @FXML
    void add(ActionEvent event) {
    	
    }
    @FXML
    void edit(ActionEvent event) {
    	
    }
    @FXML
    void remove(ActionEvent event) {
    	
    }

    @FXML
    void switchtorooms(ActionEvent event) {

    }

    @FXML
    void switchtoservices(ActionEvent event) throws IOException {
//        try{
//
//            URL path = new File("src/main/resources/beta/hotelver1/adminfood.fxml").toURI().toURL();
//
//            Parent root = FXMLLoader.load(path);
//            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//            ts.applySettings(root, "adminfood");
//
//            stage.setResizable(true);
//            stage.getScene().setRoot(root);
//            stage.show();
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    	System.out.println("NO services");
    }
    @FXML
    void initialize() {
        loadEmployeeData(); // Load employee data

        FilteredList<Employee> filteredData = new FilteredList<>(employeeList, p -> true);

        id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        role.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneno()));

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeetable.comparatorProperty());
        employeetable.setItems(sortedData);
       
    }

    private void loadEmployeeData() {
        employeeList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM employee")) {

            while (resultSet.next()) {
                String id = resultSet.getString("Id");
                String name = resultSet.getString("Name");
                String role = resultSet.getString("Role");
                String status = resultSet.getString("Status");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone No");

                employeeList.add(new Employee(id, name, role, status, email, phone));
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    }
