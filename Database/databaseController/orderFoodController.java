package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.booking;
import Model.charges_for_food;
import Model.food;
import Model.order;
import Model.order_food;
import Model.room;
import Model.roomType;
import Model.room_charges;
import util.DBConnection;

public class orderFoodController {
	
	public static boolean saveFoodOrder(order_food s) {
		String sql = "insert into order_food values(? , ? ,? ,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getOrder_food_id());
			psmt.setString(2, s.getBooking_id().getBooking_id());
			psmt.setString(3, s.getRoomNo().getRoom_no());
			psmt.setInt(4, s.getFood_id().getFood_id());
			psmt.setInt(5, s.getFood_quantity());
			psmt.setDouble(6, s.getFood_total_price());
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<order_food> getAllOrderedFood(){
		String sql = "SELECT * FROM order_food";
		ArrayList<order_food> list = new ArrayList<>();
		
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				String orderFoodId = rs.getString(1);
				booking bookingId = bookingController.getBookingById(rs.getString(2));
				room roomNo = roomController.getRoomById(rs.getString(3));
				food foodId = foodController.getFoodById(rs.getInt(4));
				int foodQuantity = rs.getInt(5);
				double ftotalPrice = rs.getDouble(6);
				order_food orderedFood = new order_food(orderFoodId, bookingId, roomNo, foodId, foodQuantity, ftotalPrice);
				list.add(orderedFood);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean saveFoodCharges(charges_for_food s) {
		String sql = "insert into food_charges values(? , ? )";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getBooking_id().getBooking_id());
			psmt.setDouble(2, s.getAllfood_total_charges());
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<charges_for_food> getAllChargesForFood(){
		String sql = "SELECT * FROM charges_for_food";
		ArrayList<charges_for_food> list = new ArrayList<>();
		
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				booking bId = bookingController.getBookingById(rs.getString(1));
				double allFoodCharges = rs.getDouble(2);
				
				charges_for_food AllChargesForFood = new charges_for_food(bId, allFoodCharges);
				list.add(AllChargesForFood);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static order_food getOrderFoodByOrderFoodId(String order_food_id) {
		String sql = "select * from order_food where order_food_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, order_food_id);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				return new order_food(rs.getString(1),bookingController.getBookingById(rs.getString(2))
						, roomController.getRoomById(rs.getString(3)), foodController.getFoodById(4),
						rs.getInt(5), rs.getDouble(6)) ;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static charges_for_food getFoodChargesByBookingId(String bookingId) {
		String sql = "select * from charges_for_food where booking_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, bookingId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new charges_for_food(bookingController.getBookingById(rs.getString(1)),rs.getDouble(2) );
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
