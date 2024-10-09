package Admin;

import javafx.application.Platform;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.prefs.Preferences;

public class settingsController {

	@FXML
	private AnchorPane toremoveemployee;
	@FXML
	private TextField idField;
	@FXML
	private TextField nameField;
	@FXML
	private TextField roleField;
	@FXML
	private TextField statusField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phonenoField;

	@FXML
	private Button cancel1;
	@FXML
	private Button delete;
	@FXML
	private Button save;
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
	@FXML
	private Button audit_view;
	@FXML
	private Button cancel;
	@FXML
	private Button refresh;

	@FXML
	void dark(ActionEvent event) {

	}

	@FXML
	void light(ActionEvent event) {

	}
	@FXML
	void id2(ActionEvent event) {

	}
	@FXML
	void name2(ActionEvent event) {

	}
	@FXML
	void role2(ActionEvent event) {

	}
	@FXML
	void status2(ActionEvent event) {

	}
	@FXML
	void email2(ActionEvent event) {

	}
	@FXML
	void phoneno2(ActionEvent event) {

	}

	@FXML
	void switchtobookings(ActionEvent event) {

	}
	@FXML
	void edit(ActionEvent event) {

	} 	   

	@FXML
	void switchtorooms(ActionEvent event) {

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
	}    @FXML
	void add(ActionEvent event) {
		if(event.getSource()==add) {
			manageemployee.setVisible(true);
		}
	}



	@FXML
	private HBox manageemployee;

	private static final String PREFERENCE_NODE_NAME = "beta.hotel.preferences";
	private static final String THEME_KEY = "theme";
	private static final String USER_KEY = "user";

	private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel";
	private static final String USER = "root";
	private static final String PASS = "";
	private ObservableList<Employee> employeeList;

	private themeSettings ts = new themeSettings();

	@FXML
	private RadioButton light;
	@FXML
	private RadioButton dark;

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
	void saveSettings(ActionEvent event) {
		// saveWindowsSettings();
		addemployee();

		// Debug prints

	}
	@FXML
	void cancel1(ActionEvent event) {
		toremoveemployee.setVisible(false); // Hide confirmation pane
	}

	private void loadWindowsSettings() {
		Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);
		String theme = userPref.get(THEME_KEY, "light");
		String userID = userPref.get(USER_KEY, "Unauthorized");
		// Load additional settings if needed
	}

	private void saveWindowsSettings() {
		Preferences userPref = Preferences.userRoot().node(PREFERENCE_NODE_NAME);
		if (dark.isSelected()) userPref.put(THEME_KEY, "dark");
		else if (light.isSelected()) userPref.put(THEME_KEY, "light");
	}

	@FXML
	void initialize() {
		loadEmployeeData(); // Load employee data
		setUpTableColumns();
		addemployee();
		saveSettings(null);

		// Additional initialization if needed
	}

	private void setUpTableColumns() {
		id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
		name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		role.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
		status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
		email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		phoneno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneno()));

		FilteredList<Employee> filteredData = new FilteredList<>(employeeList, p -> true);
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
	private void addemployee() {
		System.out.println("Saving settings...");
		String id = idField.getText();
		String name = nameField.getText();
		String role = roleField.getText();
		String status = statusField.getText();
		String email = emailField.getText();
		String phoneno = phonenoField.getText();

		// Check for empty fields
		if (id.isEmpty() || name.isEmpty() || role.isEmpty() || email.isEmpty() || phoneno.isEmpty()) {
			System.err.println("Please fill in all fields.");
			return;
		}

		// Insert into the database
		try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
			String sql = "INSERT INTO employee (Id, Name, Role, Status, Email, `Phone No`) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, id);
				preparedStatement.setString(2, name);
				preparedStatement.setString(3, role);
				preparedStatement.setString(4, status);
				preparedStatement.setString(5, email);
				preparedStatement.setString(6, phoneno);

				int rowsAffected = preparedStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Employee data saved successfully!");
					loadEmployeeData();  // Refresh the employee list
				} else {
					System.out.println("Failed to save employee data.");
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	@FXML
	void refresh(ActionEvent event) {
		try {
			// Get the current stage
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Load the new FXML file
			Parent newRoot = FXMLLoader.load(getClass().getResource("settings.fxml")); // Update the path

			// Set the new scene
			stage.setScene(new Scene(newRoot));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading FXML: " + e.getMessage());
		}
	}

	private void deleteEmployee(String id) {
		String sql = "DELETE FROM employee WHERE Id = ?"; // Ensure "Id" matches your DB schema

		if (id == null || id.isEmpty()) {
			System.err.println("Invalid ID: " + id);
			return;
		}

		try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, id);

			System.out.println("Executing SQL: " + preparedStatement.toString());

			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Employee deleted successfully!");
				loadEmployeeData(); // Refresh the employee list after deletion
			} else {
				System.out.println("No employee found with ID: " + id);
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	@FXML
	void delete(ActionEvent event) {
		Employee selectedEmployee = employeetable.getSelectionModel().getSelectedItem();

		if (selectedEmployee != null) {
			System.out.println("Deleting employee with ID: " + selectedEmployee.getId()); // Debug print
			deleteEmployee(selectedEmployee.getId());
		} else {
			System.err.println("No employee selected for deletion.");
		}
	}

	@FXML
	void remove(ActionEvent event) {
		toremoveemployee.setVisible(true); 
	}

}
