package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.booking;
import Model.order;
import Model.orderTotalCharges;
import util.DBConnection;

public class orderTotalChargesController {
	
	public static boolean SaveOrderCharge(orderTotalCharges s) {
		String sql = "insert into order_total_charges values(? , ? ,? )";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getOrder_id().getOrder_id());
			psmt.setDouble(2, s.getFood_total_charges());
			psmt.setDouble(3, s.getService_total_charges());
			
			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<orderTotalCharges> getAllOrderCharge(){
		String sql = "SELECT * FROM order_total_charges";
		ArrayList<orderTotalCharges> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				order id = orderController.getOrderById(rs.getString(1));
				double fTotalCharge = rs.getDouble(2);
				double sTotalCharge = rs.getDouble(3);
				orderTotalCharges orderedCharge = new orderTotalCharges(id, fTotalCharge , sTotalCharge);
				list.add(orderedCharge);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
