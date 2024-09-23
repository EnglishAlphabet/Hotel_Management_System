package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.customer;
import Model.food;
import Model.room;
import Model.roomType;
import Model.room_status;
import util.DBConnection;

public class roomController {

	public static boolean saveRoom(room s) {
		String sql = "insert into room values(?,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getRoom_no());
			psmt.setString(2, s.getRoom_type().getRoom_type());
			psmt.setInt(3, s.getFloor());
			psmt.setString(4, s.getRoom_status().getRoom_status_id());
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<room> getAllRooms(){
		String sql = "SELECT * FROM room";
		ArrayList<room> list = new ArrayList<>();
		
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				String roomTypeId = rs.getString(1);
				roomType roomType = roomTypeController.getRoomTypeById(roomTypeId);
				int floor = rs.getInt(3);
				room_status status = roomStatusController.getRoomStatusById(rs.getString(4));
				room Room = new room(roomTypeId, roomType,floor, status);
				list.add(Room);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static room getRoomById(String roomNo) {
		String sql = "select * from room where room_no=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, roomNo);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new room(rs.getString(1),
						new roomType(rs.getString(2)),rs.getInt(3), new room_status(rs.getString(4), null));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean updateRoomStatus(room s) {
		String sql = "update room set room_status_id=? where room_no=? ";
		try (Connection con = DBConnection.getConection();) {
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, s.getRoom_status().getRoom_status_id());
			psmt.setString(2, s.getRoom_no());
			
			 psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static List<room> FilteredByRoomType(String roomTypeId) {
		String sql = "select * from room where room_type_id=?";
		ArrayList<room> filteredRooms = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, roomTypeId);
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				String roomId = rs.getString(1);
				roomType roomType = roomTypeController.getRoomTypeById(rs.getString(2));
				int floor = rs.getInt(3);
				room_status status = roomStatusController.getRoomStatusById(rs.getString(4));
				room Room = new room(roomId, roomType,floor, status);
				filteredRooms.add(Room);
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return filteredRooms;
	}
	public static void main(String[] args) {
		//System.out.println(getAllRooms().toString());
		//room r = new room("3", new roomType("r2"),"available");
		//saveRoom(r);
		//System.out.println(getRoomById("3"));
		/*try {
		List<room> filteredRooms =  roomController.FilteredByRoomType("r2");
		
		for (room room : filteredRooms) {
			System.out.println(room.toString());
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		*/
		updateRoomStatus(new room("5", new roomType("r2"), 2, new room_status("U", "Unavailable")));
	}
	
}
