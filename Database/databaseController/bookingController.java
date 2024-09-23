package controller;

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
	public static boolean saveBooking(booking s) {
		String sql = "insert into booking values(?,?,?,?,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getBooking_id());
			psmt.setString(3, s.getRoom().getRoom_no());
			psmt.setString(2, s.getGuest().getGuest_id());
			psmt.setString(4, s.getBooking_date().toString());
			psmt.setString(5, s.getCheck_in().toString());
			psmt.setString(6, s.getCheck_out().toString());
			psmt.setInt(7, s.getStay_duration_nights());
			psmt.setInt(8, s.getStay_duration_days());
			psmt.setString(9, s.getBooking_status());
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
				String guestId = rs.getString(2);
				String roomNo = rs.getString(3);
				LocalDateTime bDate = LocalDateTime.parse(rs.getString(4));
				LocalDateTime cIn = LocalDateTime.parse(rs.getString(5));
				LocalDateTime cOut =LocalDateTime.parse(rs.getString(6));
				int durationNight = rs.getInt(7);
				int durationDay = rs.getInt(8);
				String bStatus = rs.getString(9);
				booking booked = new booking(id, new customer(guestId),
						new room(roomNo),bDate, cIn, cOut,durationNight,durationDay, bStatus);
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
				return new booking(rs.getString(1), new customer(rs.getString(2), null, null, null, null), 
						new room(rs.getString(3)), rs.getTimestamp(4).toLocalDateTime(),
						rs.getTimestamp(5).toLocalDateTime(), rs.getTimestamp(6).toLocalDateTime()
						,rs.getInt(7),rs.getInt(8)
						,rs.getString(9));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * getBooking(String input)
	 * SELECT * FROM `booking` WHERE booking_id LIKE `%input%` OR phone_no LIKE `%input%`
	 */
	
	public static void main(String[] args) {
		getBookingById("b001");
				
	}
}
