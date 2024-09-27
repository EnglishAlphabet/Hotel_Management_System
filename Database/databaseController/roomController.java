package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;

import Model.booking;
import Model.customer;
import Model.food;
import Model.room;
import Model.roomType;
import Model.room_status;
import util.DBConnection;

public class roomController {

	public static boolean addRoom(room s) {
		String sql = "call add_room(?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getRoom_no());
			psmt.setString(2, s.getRoom_type().getRoom_type());
			psmt.setInt(3, s.getFloor());
			
			
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
	public static boolean searchAvailableRoomsWithinDate(booking s) {
		String sql = "call search_available_rooms_within_date(?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1,s.getCheck_in().toString());
			psmt.setString(2, s.getCheck_out().toString());
			
			
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static boolean searchCustomerRoomByNRC(customer s) {
		String sql = "call search_booking(?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getIdentity_card());
	
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static boolean searchCustomerByRoomNo(room s) {
		String sql = "call search_by_roomNo(?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getRoom_no());
	
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static boolean getRoomByRoomType(roomType s) {
		String sql = "call search_room_type(?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getDescription());
	
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
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
		//updateRoomStatus(new room("5", new roomType("r2"), 2, new room_status("U", "Unavailable")));
		//room r = new room("9", new roomType("r1"), 3);
		//addRoom(r);
		booking b = new booking(LocalDateTime.of(2024, 12, 18, 12, 12),
				LocalDateTime.of(2024, 12, 30, 12, 12));
		searchAvailableRoomsWithinDate(b);
		System.out.println(b.getRoom());
		
	}
	
}
