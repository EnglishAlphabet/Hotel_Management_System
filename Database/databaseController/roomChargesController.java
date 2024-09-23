package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.booking;

import Model.room;
import Model.roomType;
import Model.room_charges;
import util.DBConnection;

public class roomChargesController {
	
		public static boolean addRoomCharges(room_charges s) {
			String sql = "insert into room_charges values(? , ? ,?,? )";
			try(Connection con = DBConnection.getConection(); 
				PreparedStatement psmt = con.prepareStatement(sql);){
				psmt.setString(1, s.getBooking_id().getBooking_id());
				psmt.setString(2, s.getRoom_no().getRoom_no());
				psmt.setString(3, s.getRoom_type_id().getRoom_type());
				psmt.setDouble(4, s.getRoom_charges());
				
				 
				int r = psmt.executeUpdate();
				return r>0;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}
		public static ArrayList<room_charges> getAllroomCharges(){
			String sql = "SELECT * FROM room_charges";
			ArrayList<room_charges> list = new ArrayList<>();
			
			try (Connection con = DBConnection.getConection();
					PreparedStatement psmt = con.prepareStatement(sql)){
				ResultSet rs = psmt.executeQuery();
				
				while (rs.next()) {
					booking bookingId = bookingController.getBookingById(rs.getString(1));
					room roomNo = roomController.getRoomById(rs.getString(2));
					roomType roomTypeId = roomTypeController.getRoomTypeById(rs.getString(3));
					double roomCharges = rs.getDouble(4);
					room_charges Rcharges = new room_charges(bookingId, roomNo, roomTypeId, roomCharges);
					list.add(Rcharges);
				}
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		public static room_charges getRoomChargesById(String bookingId) {
			String sql = "select * from room_charges where booking_id=?";
			try (Connection con = DBConnection.getConection();
					PreparedStatement psmt = con.prepareStatement(sql)){
				psmt.setString(1, bookingId);
				ResultSet rs = psmt.executeQuery();
				if (rs.next()) {
					return new room_charges(bookingController.getBookingById(rs.getString(1)), 
							new room(rs.getString(2)), 
							new roomType(rs.getString(3)
									), rs.getDouble(4));
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}


