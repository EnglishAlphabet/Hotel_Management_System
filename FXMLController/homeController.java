/*
*
*Package name and others should be appropriately
*
*/

package beta.hotelver1.controller;


import beta.database.controller.room_controller;
import beta.models.room;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import java.util.List;

public class homeController {

    @FXML
    private TilePane dashboard; //change the name according to your fxml file
    //Was flowpane but tilepane works better from my testing
    
    @FXML
    public void initialize() {
        
        room_card card = new room_card();
        card.generate_card();
        
    }

    public class room_card{

        public void generate_card(){

            List<room> all_rooms = room_controller.getAllRooms();
            
            for(room r : all_rooms){
                
                Label room_no = new Label("Room NO:"+r.getRoom_no());
                Label room_type = new Label("Type: "+r.getRoomType().getRoom_type());
                Label room_floor = new Label("Floor: "+r.getFloor() + "");
                Label room_status = new Label("Status: "+r.getRoom_status().getRoom_status_id());
                
                VBox root_card = new VBox(room_no,room_type,room_floor,room_status);
                
                root_card.setStyle("-fx-background-color: #ADEFEE;"+
                        "-fx-padding: 10px;"+
                        "-fx-margin: 5px;"+
                        "-fx-background-radius: 1.25em;");
                
                dashboard.getChildren().addAll(root_card); //name may vary

            }



        }
    }
}
