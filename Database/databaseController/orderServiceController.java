package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.food;
import Model.order;
import Model.order_food;
import Model.order_service;
import Model.room;
import Model.service;
import util.DBConnection;

public class orderServiceController {
	public static boolean saveServiceOrder(order_service s) {
		String sql = "insert into order_service values(? , ? ,? ,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getService_id().getService_id());
			psmt.setString(2, s.getOrder_id().getOrder_id());
			psmt.setString(3, s.getRoom_no().getRoom_no());
			psmt.setString(4, s.getService_id().getService_id());
			psmt.setInt(5, s.getService_times());
			psmt.setDouble(6, s.getService_total_price());
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
				order orderId = orderController.getOrderById(rs.getString(2));
				room roomNo = roomController.getRoomById(rs.getString(3));
				service serviceId = serviceController.getServiceById(rs.getString(4));
				int serviceTimes = rs.getInt(5);
				double StotalPrice = rs.getDouble(6);
				order_service orderedService = new order_service(orderServiceId, orderId, roomNo,
						serviceId, serviceTimes, StotalPrice);
				list.add(orderedService);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
