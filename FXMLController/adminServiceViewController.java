package view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import Model.order_service;
import Model.service;
import controller.orderController;
import controller.orderServiceController;
import controller.serviceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.DBConnection;


public class adminServiceViewController {
	
	@FXML
	private Button btnCreateService;
	@FXML
	private Button btnConfirm;
    @FXML
    private Button btnAdd ;
    @FXML
    private Button btnorder;
    @FXML
    private Button btnLeft;

    @FXML
    private Button btnRight;
    @FXML
    private Button btnOrderBatch;
    @FXML
    private ImageView imgView;

    @FXML
    private Label lblDiscription ;
    @FXML
    private Label lblPrice ;

    @FXML
    private Label lblTitle;
    
    @FXML
    private ScrollPane scrollPaneServices;
    
    @FXML
    private ScrollPane orderListScrollPane;
    
    @FXML
	private TilePane TilePaneOrderBatch;
    
    @FXML
    private TilePane tilepaneServices;

    @FXML
    private VBox orderListVbox;

    @FXML
    private Spinner<Integer> spinnerQuantity;

    @FXML
    private TextField txfRoomNo;

    @FXML
    private TextField txfServiceName;
  
    private List<service> services = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    private void initialize() {//Starting the scene 
    	TilePaneOrderBatch.setPrefColumns(2);  // Set 2 columns
        TilePaneOrderBatch.setPrefRows(2);
    	spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
			
    	initialzingOrderList();
    	loadServicesFromDatabase();
    	loadPopularServicesFromDatabase();
    	  if (!services.isEmpty()) {
              updateServiceDisplay();
          } else {
             lblTitle.setText("No services found");
              lblPrice.setText("");
          }
     
    }
    @FXML
    private void previousService() {//left arrow to go previous one
        if (currentIndex > 0) {
            currentIndex--;
            updateServiceDisplay();
        }
    }

    @FXML
    private void nextService() {//right arrow to go next one
        if (currentIndex < services.size()-1 ) {
            currentIndex++;
            updateServiceDisplay();
        }
    }
    private void loadPopularServicesFromDatabase() {//get popular service from database , we can limit to 1 / 2 
        String sql = "SELECT s.service_id, s.service_name, s.service_price, s.description, s.Image_path,  SUM(CAST(os.service_times AS UNSIGNED)) AS total_orders\r\n" + 
        		"FROM service s\r\n" + 
        		"JOIN order_service os ON s.service_id = os.service_id\r\n" + 
        		"GROUP BY s.service_id, s.service_name, s.service_price, s.description, s.Image_path\r\n" + 
        		"ORDER BY total_orders DESC";
        try (Connection con = DBConnection.getConection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String serviceName = rs.getString(2);
                double price = rs.getDouble(3);
                String des = rs.getString(4);
                Blob img = rs.getBlob(5);
                services.add(new service(serviceName, price, des,img));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateServiceDisplay() {//updating 
    	 if (!services.isEmpty() && currentIndex >= 0 && currentIndex < services.size()) {
             service currentService = services.get(currentIndex);
             lblTitle.setText(currentService.getService_name());
             lblPrice.setText("$" + currentService.getPrice());
             lblDiscription.setText(currentService.getDescription());
             Blob imageBlob = currentService.getImage(); // Assuming getImage() returns Blob
             if (imageBlob != null) {
                 try {
                     // Convert Blob to byte array and then to Image
                     byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                     Image image = new Image(new ByteArrayInputStream(imageBytes));
                     
                     // Set the ImageView with the Image
                     imgView.setImage(image);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             } else {
                 // Clear the ImageView if no image is present
                 imgView.setImage(null);
             }
         } else {
             lblTitle.setText("No service available");
             lblPrice.setText("");
             lblDiscription.setText("");
             imgView.setImage(null);
         }
     }
    public void loadServicesFromDatabase() {//getting all services along with images but there are some changes needed.It is not flexible right now
    	
        try {
            Connection connection =DBConnection.getConection();
            String query = "SELECT service_id,service_name, service_price,description, Image_path FROM service ORDER BY service_id ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            
            while (resultSet.next()) {
            	String serviceId = resultSet.getString(1);
                String serviceName = resultSet.getString(2);
                String servicePrice = resultSet.getString(3);
                String description = resultSet.getString(4);
                Blob imageBlob = resultSet.getBlob(5);
                
                // Convert Blob to Image
                InputStream inputStream = imageBlob.getBinaryStream();
                Image image = new Image(inputStream);

                createService(serviceId, serviceName, description, Double.parseDouble(servicePrice), image);

                // Remember to close inputStream for Blob after use
                inputStream.close();
            
            }

            // Close the connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createService(String serviceId, String serviceName, String description, double price, Image image) {
        // Existing createService logic
        HBox hbox = new HBox(10);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        
        VBox vbox = new VBox(5);
        Label idLabel = new Label("Service ID: " + serviceId);
        Label nameLabel = new Label("Service Name: " + serviceName);
        Label descriptionLabel = new Label("Description: " + description);
        Label priceLabel = new Label("Price: $" + price);
        
        idLabel.setStyle("-fx-text-fill:white");
        nameLabel.setStyle("-fx-text-fill:white");
        descriptionLabel.setStyle("-fx-text-fill:white");
        priceLabel.setStyle("-fx-text-fill:white");
        hbox.setStyle("-fx-background-color:black;" + "-fx-text-fill:white;");
        
        vbox.getChildren().addAll(idLabel, nameLabel, descriptionLabel, priceLabel);
        hbox.getChildren().addAll(imageView, vbox);
        
        // Add the HBox to TilePane
        
        tilepaneServices.getChildren().add(hbox);
        
    }
    @FXML
    private void handleOrderAction() {
    	  orderListVbox.getChildren().clear();
    	if (btnorder.getText().equals("Order Batch")) {
            executeOrderBatch();
        } else {
        String roomNo = txfRoomNo.getText();
        int serviceId = Integer.parseInt(txfServiceName.getText());
        int quantity = spinnerQuantity.getValue();

        // Save the order to the database
        saveOrderToDatabase(roomNo, serviceId, quantity);

        // Add a new service order pane to the VBox
        createServiceOrderPane(serviceId, roomNo, quantity);
        }
    }
    private void executeOrderBatch() {
        for (javafx.scene.Node node : TilePaneOrderBatch.getChildren()) {
            if (node instanceof VBox) {
                VBox formBox = (VBox) node;

                TextField roomNoField = (TextField) formBox.getChildren().get(0);
                TextField serviceIdField = (TextField) formBox.getChildren().get(1);
                Spinner<Integer> quantitySpinner = (Spinner<Integer>) formBox.getChildren().get(2);
                

                String roomNo = roomNoField.getText();
                int serviceId = Integer.parseInt(serviceIdField.getText());
                int quantity = quantitySpinner.getValue();

                // Execute the batch order (saving each order to the database)
                saveOrderToDatabase(roomNo, serviceId, quantity);
                
                // Add each batch order result to the order list
                createServiceOrderPane(serviceId, roomNo, quantity);
            }
        }

        // Clear the TilePane after batch processing
        TilePaneOrderBatch.getChildren().clear();

        // Reset the order button text back to "Order"
        btnorder.setText("Order");
    }
    
    private void createServiceOrderPane(int serviceId, String roomNo, int quantity) {
        HBox serviceOrderPane = new HBox(50);  // Horizontal layout for order details
        VBox Tosep = new VBox(10);
        Label serviceLabel = new Label("Service id:" + serviceId);
        Label roomLabel = new Label("Room: " + roomNo);
        Label quantityLabel = new Label("Quantity: " + quantity);
        
        Separator sep = new Separator();
 
        
        serviceLabel.setStyle("-fx-text-fill:white");
        roomLabel.setStyle("-fx-text-fill:white");
        quantityLabel.setStyle("-fx-text-fill:white");
        serviceOrderPane.setStyle("-fx-background-color: #141638;" + "-fx-padding: 5px 0px 5px 10px;"
        						+ "-fx-background-radius:8px");
        
        sep.setPrefWidth(100); // Set preferred width for horizontal separator
        sep.setStyle("-fx-background-grey: white;" + 
                     "-fx-border-color:grey;" + // Border color
                     "-fx-border-width: 0;");    // Border width
        
        serviceOrderPane.getChildren().addAll(serviceLabel, roomLabel, quantityLabel);
        Tosep.getChildren().addAll(serviceOrderPane,sep);
        // Add the pane to the VBox
        orderListVbox.getChildren().add(0,Tosep);
    }
    
    public static boolean saveOrderToDatabase(String roomNo, int serviceId, int quantity) {

	    String sql = "select booking_id from booking where room_no=?";
	    try (Connection con = DBConnection.getConection(); 
	        PreparedStatement psmt = con.prepareStatement(sql)) {

	        psmt.setString(1, roomNo);
	        ResultSet rs = psmt.executeQuery();

	        if (rs.next()) {
	            // Debug print to check if booking_id is retrieved correctly
	            String bkId = rs.getString("booking_id");
	            System.out.println("Booking ID: " + bkId); // Debug line

	            String query = "INSERT INTO order_service(booking_id, room_no, service_id, service_times) VALUES (?, ?, ?, ?)";

	            PreparedStatement statement = con.prepareStatement(query);
	            statement.setString(1, bkId);
	            statement.setString(2, roomNo);
	            statement.setInt(3, serviceId);
	            statement.setInt(4, quantity);

	            // Debug print to check if the insert is successful
	            int r = statement.executeUpdate();
	            System.out.println("Rows affected: " + r); // Debug line

	            return r > 0; // Return true if insertion was successful
	        } else {
	            System.out.println("No booking ID found for room number: " + roomNo); // Debug line
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
    
       public void initialzingOrderList() {
    	   String query = "SELECT service_id , room_no,service_times FROM order_service ORDER BY order_service_id desc";
           try {
        	   Connection con = DBConnection.getConection();
               PreparedStatement statement = con.prepareStatement(query);
               ResultSet resultSet = statement.executeQuery(query);

               while (resultSet.next()) {
                   int serviceId = resultSet.getInt(1);
                   String roomNo = resultSet.getString(2);
                   int quantity = resultSet.getInt(3);

                   // Create service panes for existing orders
                   createServiceOrderPane(serviceId, roomNo, quantity);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           
       }
       @FXML
       private void handleAddButtonClick() {
    	   orderListVbox.getChildren().clear();
    	    if (TilePaneOrderBatch.getChildren().size() >= 4) {
    	    	new Alert(AlertType.WARNING, "Maximum Batch Order is 4", ButtonType.OK).showAndWait();
    	        return; // Limit to 4 items (2x2 grid)
    	    }

    	    // Create a VBox (or Pane) to hold form elements (Room No, Service ID, Quantity)
    	    VBox formBox = new VBox(10); // VBox to group elements within the TilePane

    	    // Create TextField for Room No
    	    TextField roomNoField = new TextField();
    	    roomNoField.setPromptText("Enter Room No");

    	    // Create TextField for Service ID
    	    TextField serviceIdField = new TextField();
    	    serviceIdField.setPromptText("Service Id");

    	    // Create Spinner for Quantity
    	    Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1); // Min 1, Max 100, Initial 1
    	   

    	    // Add the form elements to the VBox
    	    formBox.getChildren().addAll(roomNoField, serviceIdField, quantitySpinner);

    	    // Add the VBox (form) to the TilePane
    	    TilePaneOrderBatch.getChildren().add(formBox);
    	    btnorder.setText("Order Batch");
    	}
       @FXML
       private void handleCreateServiceButtonClick() {
           // Create a new stage (pop-up window)
    	// Create a new stage for the popup
    	    Stage popupStage = new Stage();
    	    popupStage.initModality(Modality.APPLICATION_MODAL);

    	    // Create VBox for input fields
    	    VBox vbox = new VBox(10);
    	    
    	    TextField serviceIdField = new TextField();
    	    serviceIdField.setPromptText("Service ID");
    	    
    	    TextField serviceNameField = new TextField();
    	    serviceNameField.setPromptText("Service Name");

    	    TextField descriptionField = new TextField();
    	    descriptionField.setPromptText("Service Description");

    	    TextField priceField = new TextField();
    	    priceField.setPromptText("Service Price");

    	    // FileChooser to select the image
    	    Button btnSelectImage = new Button("Select Image");
    	    Label selectedFileLabel = new Label();
    	    final File[] selectedFile = new File[1];

    	    btnSelectImage.setOnAction(e -> {
    	        FileChooser fileChooser = new FileChooser();
    	        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    	        File file = fileChooser.showOpenDialog(popupStage);
    	        if (file != null) {
    	            selectedFileLabel.setText(file.getName());
    	            selectedFile[0] = file; // Store selected file for later use
    	        }
    	    });
    	    vbox.setStyle("-fx-background-color:black");
    	    

    	    Button btnSave = new Button("Save");
    	    btnSave.setOnAction(e -> {
    	        String serviceId = serviceIdField.getText();
    	        String serviceName = serviceNameField.getText();
    	        String description = descriptionField.getText();
    	        double price = Double.parseDouble(priceField.getText());

    	        // Save service and image to the database
    	        saveServiceToDatabase(serviceId, serviceName, description, price, selectedFile[0]);

    	        popupStage.close(); // Close the popup after saving
    	    });

    	    vbox.getChildren().addAll(serviceIdField, serviceNameField, descriptionField, priceField, btnSelectImage, selectedFileLabel, btnSave);
    	    Scene scene = new Scene(vbox, 300, 275);
    	    popupStage.setScene(scene);
    	    popupStage.showAndWait();
    	}
       private void saveServiceToDatabase(String serviceId, String serviceName, String description, double price, File imageFile) {
    	    String query = "INSERT INTO service (service_id, service_name, service_price, description, Image_path) VALUES (?, ?, ?, ?, ?)";

    	    try (Connection connection = DBConnection.getConection();
    	         PreparedStatement statement = connection.prepareStatement(query);
    	         FileInputStream fis = new FileInputStream(imageFile)) {

    	        statement.setString(1, serviceId);
    	        statement.setString(2, serviceName);
    	        statement.setDouble(3, price);
    	        statement.setString(4, description);

    	        // Set image as BLOB
    	        statement.setBlob(5, fis);

    	        int rowsAffected = statement.executeUpdate();
    	        if (rowsAffected > 0) {
    	        	new Alert(AlertType.INFORMATION, "Service Saved Successfully", ButtonType.OK).showAndWait();
    	            tilepaneServices.getChildren().clear();
    	            loadServicesFromDatabase();
    	        }

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}
    }




