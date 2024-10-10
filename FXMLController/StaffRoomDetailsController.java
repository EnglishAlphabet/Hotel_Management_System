package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StaffRoomDetailsController {

	@FXML
	private Button BookingDetailsbtn;

	@FXML
	private TextField FirstName;

	@FXML
	private TextField LastName;

	@FXML
	private Button LogOutbtn;

	@FXML
	private Text NumberOfAR;

	@FXML
	private Text NumberOfBR;

	@FXML
	private AnchorPane RoomShowBody;

	@FXML
	private Button RoomDetailsbtn;

	@FXML
	private Button Servicesbtn;

	@FXML
	private Button Settingbtn;

	@FXML
	private Text StaffName;

	@FXML
	private DatePicker ToCheckWithCheckOutDate;

	@FXML
	private TextField deposit;

	@FXML
	private TextField duration;

	@FXML
	private Text floorText; // Renamed to avoid conflict

	@FXML
	private TextField idORnrc;

	@FXML
	private TextField phoneNumber;

	@FXML
	private Text roomID;

	@FXML
	private Text roomPrice;

	@FXML
	private Text roomType;

	@FXML
	private TextField OrderFirstName;

	@FXML
	private TextField LastName1;

	@FXML
	private TextField phoneNumber1;

	@FXML
	private TextField total;

	@FXML
	private TextField deposit1;

	@FXML
	private TextField searchField;

	@FXML
	private CheckBox singleRoomCheckBox; // Fixed the import issue

	@FXML
	private CheckBox doubleRoomCheckBox; // Fixed the import issue

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_management_system";//add your database_url
	private static final String DB_USER = "root";//add your user name
	private static final String DB_PASSWORD = "";//add your password

	@FXML
	public void initialize() {
		AddingRooms("",0);
		updateRoomCounts();
	}
	// Method to add rooms to the grid
    public void AddingRooms(String roomIdToSearch, int floorFilter) {
        RoomShowBody.getChildren().clear();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            // Build the query with filters
            String query = "SELECT room_id, room_type, floor, status, price FROM rooms WHERE 1=1";
            if (roomIdToSearch != null && !roomIdToSearch.isEmpty()) {
                query += " AND room_id = ?";
            }
            if (floorFilter != 0) {
                query += " AND floor = ?";
            }
            if (singleRoomCheckBox.isSelected() && !doubleRoomCheckBox.isSelected()) {
                query += " AND room_type = 'Single'";
            } else if (doubleRoomCheckBox.isSelected() && !singleRoomCheckBox.isSelected()) {
                query += " AND room_type = 'Double'";
            }

            PreparedStatement stmt = conn.prepareStatement(query);

            // Set parameters for roomId and floor
            int index = 1;
            if (roomIdToSearch != null && !roomIdToSearch.isEmpty()) {
                stmt.setString(index++, roomIdToSearch);
            }
            if (floorFilter != 0) {
                stmt.setInt(index, floorFilter);
            }

            ResultSet rs = stmt.executeQuery();
            GridPane roomGridPane = new GridPane();
            roomGridPane.setHgap(20);
            roomGridPane.setVgap(20);

            int row = 0, col = 0;

            while (rs.next()) {
                String roomId = rs.getString("room_id");
                String roomType = rs.getString("room_type");
                String floor = rs.getString("floor");
                String status = rs.getString("status");
                String price = rs.getString("price");

                Button roomButton = new Button("Room " + roomId);
                roomButton.setPrefSize(100, 50);
                
                String commonStyles = "-fx-background-radius: 20;" +
                                      "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.75), 10, 0.0, 5, 5);";
                
                @SuppressWarnings("unused")
				String focusedStyles = "-fx-background-color: #7b7d7d;" + 
                                       "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 1.0), 15, 0.0, 5, 5);";

                // Color coding based on room status
                switch (status) {
                    case "Occupied":
                        roomButton.setStyle(commonStyles + "-fx-background-color: red;");
                        break;
                    case "Available":
                        roomButton.setStyle(commonStyles + "-fx-background-color: forestgreen;");
                        break;
                    case "Booked":
                        roomButton.setStyle(commonStyles + "-fx-background-color: yellow;");
                        break;
                }   
                roomButton.setOnAction(e -> {
                    roomID.setText(roomId);
                    this.roomType.setText(roomType);
                    floorText.setText(floor);
                    roomPrice.setText(price);
                });

                roomGridPane.add(roomButton, col, row);
                col++;
                if (col == 7) {  // Max 7 buttons per row
                    col = 0;
                    row++;
                }
            }

            RoomShowBody.getChildren().add(roomGridPane);
            AnchorPane.setTopAnchor(roomGridPane, 20.0);
            AnchorPane.setLeftAnchor(roomGridPane, 20.0);
            AnchorPane.setRightAnchor(roomGridPane, 20.0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SearchRoom(ActionEvent event) {
        String enteredRoomId = searchField.getText().trim();
        AddingRooms(enteredRoomId, 0);  // Show filtered rooms based on input
    }

    // Method to fetch room counts from the database
    private void updateRoomCounts() {
        getRoomCount("Available", NumberOfAR);
        getRoomCount("Booked", NumberOfBR);
    }

    // Generic method to get room count by status
    private void getRoomCount(String status, Text countText) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) AS room_count FROM rooms WHERE status = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("room_count");
                countText.setText(String.valueOf(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void RoomTypeAction(ActionEvent event) {
        AddingRooms("", 0);  // Call the AddingRooms method to update the room list based on the selected filters
    }
    
    
    @FXML
    void FloorAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        int floor = 0;
        switch (source.getText()) {
            case "Floor1": floor = 1; break;
            case "Floor2": floor = 2; break;
            case "Floor3": floor = 3; break;
            case "Floor4": floor = 4; break;
            case "All": floor = 0; break;
        }
        AddingRooms("", floor);
    }
    
   
	@FXML
	void BookingAction(ActionEvent event) {

	}

	@FXML
	void ChecktheRoomsWithTheCKOutDate(ActionEvent event) {

	}
	@FXML
	void SubmitAction(ActionEvent event) {

	}

	@FXML
	void SwitchToBookingDetails(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("BookingPart.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setTitle("Booking Details Page");
		Image logo = new Image(getClass().getResourceAsStream("/Icon/logo1.png"));
		stage.getIcons().add(logo);
		stage.show();
	}
	@FXML
	void SwitchToLogInPage(ActionEvent event) {

	}

	@FXML
	void SwitchToRoomDetails(ActionEvent event) throws IOException {
		// Load the new FXML file (BookingPart.fxml)
		Parent root = FXMLLoader.load(getClass().getResource("staffpage.fxml"));

		// Get the current stage (window)
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		// Set the new scene to the current stage
		stage.setScene(new Scene(root));

		// Set the title and icon for the new scene
		stage.setTitle("Booking Details Page");
		Image logo = new Image(getClass().getResourceAsStream("/Icon/logo1.png"));
		stage.getIcons().add(logo);

		// Optionally, you can maximize or change window properties here
		stage.show(); // Display the new scene

	}

	@FXML
	void SwitchToServices(ActionEvent event) throws IOException {
		// Load the new FXML file (BookingPart.fxml)
		Parent root = FXMLLoader.load(getClass().getResource("Services.fxml"));

		// Get the current stage (window)
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		// Set the new scene to the current stage
		stage.setScene(new Scene(root));

		// Set the title and icon for the new scene
		stage.setTitle("Booking Details Page");
		Image logo = new Image(getClass().getResourceAsStream("/Icon/logo1.png"));
		stage.getIcons().add(logo);

		// Optionally, you can maximize or change window properties here
		stage.show(); // Display the new scene

	}
    @SuppressWarnings("unused")
	private void AddingCustomer_Details() {
    	
    }
    @SuppressWarnings("unused")
	private void AddingBooking_Details() {
    	
    }
    
    
    
    
    
	@FXML
	void SwitchToSetting(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Setting.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.setTitle("Booking Details Page");
		Image logo = new Image(getClass().getResourceAsStream("/Icon/logo1.png"));
		stage.getIcons().add(logo);
		stage.show();
	}
	@FXML
	void ResetAction(ActionEvent event) {
		FirstName.setText("");
		LastName.setText("");
		idORnrc.setText("");
		phoneNumber.setText("");
		duration.setText("");
		deposit.setText("");
		OrderFirstName.setText("");
		LastName1.setText("");
		phoneNumber1.setText("");
		total.setText("");
		deposit1.setText("");
	}
}
