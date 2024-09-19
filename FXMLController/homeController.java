package beta.hotelver1.controller;
//Package name and others may vary

import beta.database.controller.room_controller;
import beta.models.room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HelloController {

    @FXML
    private HBox room_floor_nav;

    @FXML
    private TilePane dashboard;

    @FXML
    public void initialize() {

        room_card card = new room_card();
        card.generate_all_card();
        Button allF = new Button("All");
        allF.setOnAction(e->card.generate_all_card());
        room_floor_nav.getChildren().add(allF);
    }

    public class room_card{

        HashMap keys = new HashMap();

        public VBox create_room_card(room r){
            Label room_no = new Label("Room NO:" + r.getRoom_no());
            Label room_type = new Label("Type: " + r.getRoomType().getRoom_type());
            Label room_floor = new Label("Floor: " + r.getFloor() + "");
            Label room_status = new Label("Status: " + r.getRoom_status().getRoom_status_id());
            VBox root_card = new VBox(room_no, room_type, room_floor, room_status);
            root_card.setStyle("-fx-background-color: #ADEFEE;" +
                    "-fx-padding: 10px;" +
                    "-fx-margin: 5px;" +
                    "-fx-background-radius: 1.25em;");
            return root_card;
        }
        public void generate_all_card(){

            List<room> all_rooms = room_controller.getAllRooms();


            for(room r : all_rooms) {

                if (!keys.containsKey(r.getFloor())) {
                    keys.put(r.getFloor(), true);
                    Button f = new Button("Floor: " + r.getFloor());
                    f.setUserData(r.getFloor());
                    f.setOnAction(e->searchByFloor(f.getUserData()));
                    room_floor_nav.getChildren().add(f);
                }
                dashboard.getChildren().addAll(create_room_card(r));
            }

        }

        public void searchByFloor(Object e){
            List<room> rooms_by_floor = room_controller.getRoomByFloor(e.toString());
            dashboard.getChildren().clear();
            for(room r : rooms_by_floor){
                dashboard.getChildren().addAll(create_room_card(r));

            }
        }
    }
}
