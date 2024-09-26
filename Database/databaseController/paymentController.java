package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Model.booking;
import Model.payment;
import util.DBConnection;

public class paymentController {
	public static boolean savePayment(payment s) {
		String sql = "insert into payment values(?,?,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getPayment_id());
			psmt.setString(2, s.getBooking_id().getBooking_id());
			psmt.setDouble(3, s.getAmount());
			psmt.setString(4, s.getPayment_date().toString());
			psmt.setString(5, s.getPayment_method());
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<payment> getAllPayment(){
		String sql = "SELECT * FROM payment";
		ArrayList<payment> list = new ArrayList<>();
		
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				String paymentId = rs.getString(1);
				booking bookingId = bookingController.getBookingById(rs.getString(2));
				double amount = rs.getDouble(3);
				LocalDateTime paymentDate = LocalDateTime.parse(rs.getString(4));
				String paymentMethod = rs.getString(5);
				payment pay = new payment(paymentId, bookingId, amount, paymentDate, paymentMethod);
				list.add(pay);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean performPayment(payment s) {
		String sql = "call perform_payment(?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getBooking_id().getBooking_id());
			psmt.setDouble(2, s.getAmount());
			psmt.setString(3, s.getPayment_method());
			
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
}
