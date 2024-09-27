package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.booking;
import Model.booking_charges;

import util.DBConnection;

public class bookingChargesController {
	public static boolean addBookingCharges(booking_charges s) {
		String sql = "insert into booking_charges values(? , ? ,?,?,?,? )";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getBooking_id().getBooking_id());
			psmt.setDouble(2, s.getTotal_room_charges());
			psmt.setDouble(3, s.getDeposit());
			psmt.setDouble(4, s.getTotal_order_charges());
			psmt.setDouble(5, s.getTotal_booking_charges());
			psmt.setDouble(6, s.getRemainingAmount());

			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<booking_charges> getAllBookingCharges(){
		String sql = "SELECT * FROM booking_charges";
		ArrayList<booking_charges> list = new ArrayList<>();
		
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				booking bookingId = bookingController.getBookingById(rs.getString(1));
				double tRoomCharges = rs.getDouble(2);
				double deposit = rs.getDouble(3);
				double tOrderCharges = rs.getDouble(4);
				double tBookingCharges = rs.getDouble(5);
				double remainAmount = rs.getDouble(6);
				booking_charges charges = new booking_charges(bookingId, tRoomCharges,deposit, tOrderCharges, tBookingCharges,remainAmount);
				list.add(charges);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static booking_charges getBookingChargesById(String bookingId) {
		String sql = "select * from booking_charges where booking_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, bookingId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new booking_charges(bookingController.getBookingById(rs.getString(1)), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4),rs.getDouble(5),rs.getDouble(6));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
