package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.food;
import Model.stock;
import util.DBConnection;

public class stockController {
	public static boolean addStock(stock s) {
		String sql = "insert into stock values(? , ?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setInt(1, s.getFood_id().getFood_id());
			psmt.setInt(2, s.getCurrent_stock());
			
			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<stock> getAllStock(){
		String sql = "SELECT * FROM stock";
		ArrayList<stock> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				food id = foodController.getFoodById(rs.getInt(1));
				int curStock = rs.getInt(2);
				
				stock allStock = new stock(id, curStock);
				list.add(allStock);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static stock getStockByFoodId(int foodId) {
		String sql = "select * from stock where food_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setInt(1, foodId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new stock(foodController.getFoodById(rs.getInt(1)), rs.getInt(2));
			}
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean updateStock(stock s) {
		String sql = "update stock set current_stock=? where food_id=?";
		try (Connection con = DBConnection.getConection();) {
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setInt(1, s.getCurrent_stock());
			psmt.setInt(2, s.getFood_id().getFood_id());
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		System.out.println(getAllStock());
		updateStock(new stock(new food(7, "steak", 10000, "Main Dish", "Avaliable"), 30));
	}

}
