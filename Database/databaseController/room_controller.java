package beta.database.controller;

import beta.connection.DBConnection;
import beta.models.room;
import beta.models.room_status;
import beta.models.room_type;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class room_controller {

    public static List<room> getAllRooms(){
        List<room> all_rooms = new ArrayList<>();
        String query = "SELECT * FROM room";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement psmt = con.prepareStatement(query);){

            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                String room_no = rs.getString(1);
                String room_type = rs.getString(2);
                int floor = rs.getInt(3);
                String room_status = rs.getString(4);

                room r = new room(room_no,new room_type(room_type,null), floor, new room_status(room_status,null));
                all_rooms.add(r);
            }

            return all_rooms;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<room> getRoomByFloor(String f){
        List<room> all_rooms = new ArrayList<>();
        String query = "SELECT * FROM room WHERE floor = ?";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(query);){

            psmt.setString(1, f);
            ResultSet rs = psmt.executeQuery();
            while(rs.next()){
                String room_no = rs.getString(1);
                String room_type = rs.getString(2);
                int floor = rs.getInt(3);
                String room_status = rs.getString(4);

                room r = new room(room_no,new room_type(room_type,null), floor, new room_status(room_status,null));
                all_rooms.add(r);
            }

            return all_rooms;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getAllRooms());
    }
}
