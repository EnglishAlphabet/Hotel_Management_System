package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import Model.booking;
import Model.customer;
import Model.room;
import Model.roomType;
import util.DBConnection;

public class bookingController {
	public static boolean addBooking(booking s) {
		String sql = "Call add_booking(?,?,?,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getBooking_id());
			psmt.setString(2, s.getRoom().getRoom_no());
			psmt.setString(3, s.getGuest().getGuest_id());
			psmt.setString(4, s.getCheck_in().toString());
			psmt.setInt(5, s.getStay_duration_nights());
			psmt.setInt(6, s.getStay_duration_hours());
	 
			int r = psmt.executeUpdate();
			con.commit();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage()+"Error occur");
			return false;
		}
	}
	public static ArrayList<booking> getAllBookings(){
		String sql = "SELECT * FROM booking";
		ArrayList<booking> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String guestId = rs.getString(4);
				String roomNo = rs.getString(2);
				roomType roomtype =roomTypeController.getRoomTypeById(rs.getString(3));
				LocalDateTime bDate = LocalDateTime.parse(rs.getString(5));
				LocalDateTime cIn = LocalDateTime.parse(rs.getString(6));
				LocalDateTime cOut =LocalDateTime.parse(rs.getString(7));
				LocalDateTime lastAccDate =LocalDateTime.parse(rs.getString(8));
				int durationNights = rs.getInt(9);
				int durationHours = rs.getInt(10);
				String bStatus = rs.getString(11);
				booking booked = new booking(id, new customer(guestId),
						new room(roomNo),roomtype,bDate, cIn, cOut,lastAccDate,durationNights
						,durationHours,bStatus);
				list.add(booked);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static booking getBookingById(String bookingId) {
		String sql = "select * from booking where booking_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, bookingId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new booking(rs.getString(1), new customer(rs.getString(4), null, null, null, null), 
						new room(rs.getString(2)),new roomType(rs.getString(3)), rs.getTimestamp(5).toLocalDateTime(),
						rs.getTimestamp(6).toLocalDateTime(), rs.getTimestamp(7).toLocalDateTime(),
						rs.getTimestamp(8).toLocalDateTime(),rs.getInt(9),rs.getInt(10)
						,rs.getString(11));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean cancelBookingAuto(booking s) {
		String sql = "Call cancel_booking_auto(?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getBooking_id());

			int r = psmt.executeUpdate();
			
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage()+"Error occur");
			return false;
		}
	}
	public static boolean cancelBookingManually(booking s) {
		String sql = "Call cancel_booking_manually(?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getBooking_id());
			psmt.setString(2, s.getRoom().getRoom_no());

			int r = psmt.executeUpdate();
			
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage()+"Error occur");
			return false;
		}
	}
	public static boolean ConfirmAllTheBookings(booking s) {
		String sql = "call comfirm_booking_all_rooms(?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getBooking_id());
			
			
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static boolean ConfirmBookingByRoomNo(booking s) {
		String sql = "call comfirm_booking_manually(?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getBooking_id());
			psmt.setString(2, s.getRoom().getRoom_no());
			
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		//addBooking(new booking("b007", new room("8"), new customer("c2"), LocalDateTime.of(2024, 12, 11, 12, 12), 7, 0));
			//cancelBookingAuto(new booking("b003"));	
		//cancelBookingManually(new booking("b002", new room("2")));
		//ConfirmAllTheBookings(new booking("b006"));
		//ConfirmBookingByRoomNo(new booking("b005", new room("5")));
	}
}
