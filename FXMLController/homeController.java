package beta.hotelver1.controller;

/*
* 
* WIP
* 
* BUGs ,uncleaned codes, confusing variable names may be present
* 
* Added Retrieving Images from database
* Added Checkbox to set filters 
* 
* 
* */

import beta.database.controller.image_controller;
import beta.database.controller.room_controller;
import beta.models.room;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class HelloController {

    @FXML
    private HBox room_floor_nav;

    @FXML
    private HBox room_choices;

    @FXML
    private TilePane dashboard;

    @FXML
    private ImageView profile;

    CheckBox r1 = new CheckBox("Type: r1");
    CheckBox r2 = new CheckBox("Type: r2");

    @FXML
    public void initialize() {

        room_card card = new room_card();
        card.generate_all_card();
        Button allF = new Button("All");
        allF.setOnAction(e->card.generate_all_card());
        room_floor_nav.getChildren().add(allF);
        room_choices.getChildren().addAll(r1,r2);

        //set profile img
        Blob b = image_controller.getImageByID("1");
        try{
            byte imgByte[]=b.getBytes(1,(int)b.length());
            Image img = new Image(new ByteArrayInputStream(imgByte));
            profile.setImage(img);
        }catch(SQLException e){

        }

    }

    public class room_card{

        HashMap keys = new HashMap();

        public VBox create_room_card(room r){
            Label room_no = new Label("Room NO:" + r.getRoom_no());
            Label room_type = new Label("Type: " + r.getRoomType().getRoom_type());
            Label room_floor = new Label("Floor: " + r.getFloor());
            Label room_status = new Label("Status: " + r.getRoom_status().getRoom_status_id());
            VBox root_card = new VBox(room_no, room_type, room_floor, room_status);
            String color = r.getRoom_status().getRoom_status_id().equals("A")?
                    "-fx-background-color: #DFEDDE;":
                    "-fx-background-color: #A4C56F;" ;

            root_card.setStyle(
                    "-fx-padding: 10px;"
                    + "-fx-margin: 5px;"
                    + "-fx-background-radius: 1.25em;"
                    + color);

            return root_card;
        }
        public void generate_all_card(){

            //Adding Filter
            String filter = "";

            if(r1.isSelected() && !r2.isSelected()){
                filter=" WHERE room_type_id  = 'r1'";
            }else if(!r1.isSelected() && r2.isSelected()){
                filter = " WHERE room_type_id  = 'r2'";
            }


            List<room> all_rooms = room_controller.getAllRooms(filter);

            dashboard.getChildren().clear();

            for(room r : all_rooms) {

                if (!keys.containsKey(r.getFloor())) {
                    keys.put(r.getFloor(), true);
                    Button f = new Button("Floor: " + r.getFloor());
                    f.setUserData(r.getFloor());
                    f.setOnAction(e-> searchByFilter(f.getUserData()));
                    room_floor_nav.getChildren().add(f);
                }

                dashboard.getChildren().addAll(create_room_card(r));
            }

        }

        public void searchByFilter(Object e){
            
            //Adding Filter
            
            String filter = "";

            if(r1.isSelected() && !r2.isSelected()){
                filter="AND room_type_id = 'r1'";
            }else if(!r1.isSelected() && r2.isSelected()){
                filter = "AND room_type_id = 'r2'";
            }
            List<room> filtered_rooms = room_controller.getRoomByFloor(e.toString(), filter);

            dashboard.getChildren().clear();

            for(room r : filtered_rooms){
                dashboard.getChildren().addAll(create_room_card(r));

            }

        }
    }
}
