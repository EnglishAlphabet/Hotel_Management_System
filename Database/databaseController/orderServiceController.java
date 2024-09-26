package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.CallableStatement;

import Model.booking;
import Model.food;
import Model.order;
import Model.order_food;
import Model.order_service;
import Model.room;
import Model.service;
import util.DBConnection;

public class orderServiceController {
	public static boolean orderService(order_service s) {
		
		String sql = "call order_service(? , ? ,? ,?,?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getService_id().getService_id());
			psmt.setString(2, s.getBooking_id().getBooking_id());
			psmt.setString(3, s.getRoom_no().getRoom_no());
			psmt.setString(4, s.getService_id().getService_id());
			psmt.setInt(5, s.getService_times());
		
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<order_service> getAllOrderedService(){
		String sql = "SELECT * FROM order_food";
		ArrayList<order_service> list = new ArrayList<>();
		
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				String orderServiceId = rs.getString(1);
				booking bookingId = bookingController.getBookingById(rs.getString(2));
				room roomNo = roomController.getRoomById(rs.getString(3));
				service serviceId = serviceController.getServiceById(rs.getString(4));
				int serviceTimes = rs.getInt(5);
				double servicecharges = rs.getDouble(6);
				order_service orderedService = new order_service(orderServiceId, bookingId, roomNo,
						serviceId, serviceTimes, servicecharges);
				list.add(orderedService);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
