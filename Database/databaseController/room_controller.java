package beta.database.controller;
/*
* 
* Work in progress
* Bugs may be present
* 
* */
import beta.connection.DBConnection;
import beta.models.room;
import beta.models.room_status;
import beta.models.room_type;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class room_controller {

    public static List<room> getAllRooms(String filter){
        List<room> all_rooms = new ArrayList<>();
        String query = "SELECT * FROM room" + filter;
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

    public static List<room> getRoomByFloor(String f, String filter){
        List<room> all_rooms = new ArrayList<>();
        String query = "SELECT * FROM room WHERE floor = ?" + filter;
        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(query)){

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

    public static boolean addRoom(String room_no, String roomTypeId, int floor, String roomStatus){
        String sql = "INSERT INTO `room` VALUES(?, ?, ?, ?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(sql)){
            psmt.setString(1, room_no);
            psmt.setString(2, roomTypeId);
            psmt.setInt(3, floor);
            psmt.setString(4, roomStatus);

            return psmt.execute();

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {



    }
}
