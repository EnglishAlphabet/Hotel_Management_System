package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Date;

public class BookingDetailsController {

    @FXML
    private Button BookingDetailsbtn;

    @FXML
    private TableView<BookingSubmit> bookingTableView; 

    @FXML
    private TableColumn<BookingSubmit, Integer> idColumn;

    @FXML
    private TableColumn<BookingSubmit, String> nameColumn;

    @FXML
    private TableColumn<BookingSubmit, String> phoneNumberColumn;

    @FXML
    private TableColumn<BookingSubmit, Double> depositColumn;

    @FXML
    private TableColumn<BookingSubmit, Integer> SingleRoomColumn;

    @FXML
    private TableColumn<BookingSubmit, Integer> DoubleRoomColumn;

    @FXML
    private TableColumn<BookingSubmit, Date> checkinDateColumn;

    @FXML
    private TableColumn<BookingSubmit, Date> lastaccpetedDateColumn;

    @FXML
    private TableColumn<BookingSubmit, Void> cancleColumn; 

    @FXML
    private TableColumn<BookingSubmit, Void> editColumn;   
    
    @FXML
    private TableColumn<BookingSubmit, Void> submitColumn;

    @FXML
    private Text StaffName;

    @FXML
    private Button LogOutbtn;

    @FXML
    private Button RoomDetailsbtn;

    @FXML
    private Button Servicesbtn;

    @FXML
    private Button Settingbtn;
      
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hotel_management_system"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 
    
    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("orderName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        depositColumn.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        checkinDateColumn.setCellValueFactory(new PropertyValueFactory<>("check_in_date"));
        lastaccpetedDateColumn.setCellValueFactory(new PropertyValueFactory<>("last_accepted_date"));
        addButtonToTable();
        bookingTableView.setItems(getBookingData());
        centerColumnData(idColumn);
        centerColumnData(nameColumn);
        centerColumnData(phoneNumberColumn);
        centerColumnData(depositColumn);
        centerColumnData(checkinDateColumn);
        centerColumnData(lastaccpetedDateColumn);
    }
    
    private <T> void centerColumnData(TableColumn<BookingSubmit, T> column) {
        column.setStyle("-fx-alignment: CENTER;");
    }
   
    private ObservableList<BookingSubmit> getBookingData() {
        return FXCollections.observableArrayList(
		/*
		 * new BookingSubmit(1,"John", "1234567890", Date.valueOf("2024-10-20"),
		 * Date.valueOf("2024-10-25"), 100.0), new BookingSubmit(2, "Smith",
		 * "0987654321", Date.valueOf("2024-10-21"), Date.valueOf("2024-10-26"), 200.0),
		 * new BookingSubmit(3, "Width", "0987654321", Date.valueOf("2024-10-21"),
		 * Date.valueOf("2024-10-26"), 200.0)
		 */
        );
    }
 
    private void addButtonToTable() {
   
        Callback<TableColumn<BookingSubmit, Void>, TableCell<BookingSubmit, Void>> editCellFactory = new Callback<>() {
            @Override
            public TableCell<BookingSubmit, Void> call(final TableColumn<BookingSubmit, Void> param) {
                final TableCell<BookingSubmit, Void> cell = new TableCell<>() {

                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction(event -> {
                            BookingSubmit data = getTableView().getItems().get(getIndex());
                            System.out.println("Editing " + data.getOrderName());
                            // Add your edit logic here
                        });
                        editButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-padding: 5;"); // Set default style
                        
                        // Adding hover effect
                        editButton.setOnMouseEntered(e -> editButton.setStyle("-fx-background-color: darkkhaki; -fx-text-fill: black; -fx-padding: 5;"));
                        editButton.setOnMouseExited(e -> editButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-padding: 5;"));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            editButton.prefWidthProperty().bind(getTableColumn().widthProperty());
                            setGraphic(editButton);
                            setStyle("-fx-alignment: CENTER;"); 
                        }
                    }
                };
                return cell;
            }
        };
        editColumn.setCellFactory(editCellFactory);
        Callback<TableColumn<BookingSubmit, Void>, TableCell<BookingSubmit, Void>> cancelCellFactory = new Callback<>() {
            @Override
            public TableCell<BookingSubmit, Void> call(final TableColumn<BookingSubmit, Void> param) {
                final TableCell<BookingSubmit, Void> cell = new TableCell<>() {

                    private final Button cancelButton = new Button("Cancel");

                    {
                        cancelButton.setOnAction(event -> {
                            BookingSubmit data = getTableView().getItems().get(getIndex());
                            System.out.println("Canceling " + data.getOrderName());
                            // Add your cancel logic here
                        });
                        cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 5;"); // Set default style
                        
                        // Adding hover effect
                        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-padding: 5;"));
                        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 5;"));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            cancelButton.prefWidthProperty().bind(getTableColumn().widthProperty());
                            setGraphic(cancelButton);
                            setStyle("-fx-alignment: CENTER;"); // Center the button in the column
                        }
                    }
                };
                return cell;
            }
        };
        cancleColumn.setCellFactory(cancelCellFactory);
        Callback<TableColumn<BookingSubmit, Void>, TableCell<BookingSubmit, Void>> submitCellFactory = new Callback<>() {
            @Override
            public TableCell<BookingSubmit, Void> call(final TableColumn<BookingSubmit, Void> param) {
                final TableCell<BookingSubmit, Void> cell = new TableCell<>() {

                    private final Button submitButton = new Button("Submit");

                    {
                        submitButton.setOnAction(event -> {
                            BookingSubmit data = getTableView().getItems().get(getIndex());
                            System.out.println("Submitting " + data.getOrderName());
                            // Add your submit logic here
                        });
                        submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-padding: 5;"); 
                        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white; -fx-padding: 5;"));
                        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-padding: 5;"));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            submitButton.prefWidthProperty().bind(getTableColumn().widthProperty());
                         
                            setGraphic(submitButton);
                            setStyle("-fx-alignment: CENTER;"); 
                        }
                    }
                };
                return cell;
            }
        };
        submitColumn.setCellFactory(submitCellFactory);
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
	    Parent root = FXMLLoader.load(getClass().getResource("staffpage.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Booking Details Page");
	    Image logo = new Image(getClass().getResourceAsStream("/Icon/logo1.png"));
	    stage.getIcons().add(logo);
	    stage.show();
	
	}
	
	@FXML
	void SwitchToServices(ActionEvent event) throws IOException {
	    Parent root = FXMLLoader.load(getClass().getResource("Services.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Booking Details Page");
	    Image logo = new Image(getClass().getResourceAsStream("/Icon/logo1.png"));
	    stage.getIcons().add(logo);
	    stage.show();
	
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
	
	
	
	
	

}
