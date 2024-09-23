package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.booking;
import Model.food;
import Model.order;
import util.DBConnection;

public class orderController {
	public static boolean addOrder(order s) {
		String sql = "insert into order values(? , ? ,? ,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getOrder_id());
			psmt.setString(2, s.getBooking_id().getBooking_id());
			psmt.setString(3, s.getOrder_description());
			psmt.setDouble(4, s.getTotal_price());
			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<order> getAllOrder(){
		String sql = "SELECT * FROM order";
		ArrayList<order> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				booking bId = bookingController.getBookingById(rs.getString(2));
				String orderDes = rs.getString(3);
				double orderTotalPrice = rs.getDouble(4);
				order ordered = new order(id, bId, orderDes, orderTotalPrice);
				list.add(ordered);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static order getOrderById(String orderId) {
		String sql = "select * from order where order_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, orderId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new order(rs.getString(1), 
				 	   bookingController.getBookingById(rs.getString(2)), 
				 	   rs.getString(3),rs.getDouble(4));
			
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
