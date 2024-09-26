package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.booking;
import Model.customer;
import Model.food;
import Model.room;
import Model.roomType;
import Model.room_status;
import util.DBConnection;

public class foodController {
	public static boolean addFood(food s) {
		String sql = "insert into food values(? , ? ,? ,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setInt(1, s.getFood_id());
			psmt.setString(2, s.getFood_name());
			psmt.setDouble(3, s.getFood_price());
			psmt.setString(4, s.getfood_category());
			psmt.setString(5, s.getStock_status());
			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<food> getAllFood(){
		String sql = "SELECT * FROM food";
		ArrayList<food> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				double fPrice = rs.getDouble(3);
				String foodCategory = rs.getString(4);
				String stockStatus =  rs.getString(5);
				food meal = new food(id, name, fPrice, foodCategory , stockStatus);
				list.add(meal);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static food getFoodById(int foodId) {
		String sql = "select * from food where food_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setInt(1, foodId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new food(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4) , rs.getString(5));
			}	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean updateMenu(food s) {
		String sql = "update food set food_name=?, food_price=? ,food_category=? , stock_status=? where food_id=?";
		try (Connection con = DBConnection.getConection();) {
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, s.getFood_name());
			psmt.setDouble(2, s.getFood_price());
			psmt.setString(3, s.getfood_category());
			psmt.setString(4, s.getStock_status());
			psmt.setInt(4, s.getFood_id());
			 psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static List<food> getFoodByFoodCategory(String foodCategory) {
		String sql = "select * from food where food_category=?";
		List<food> FoodByCategory = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, foodCategory);
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				int foodId = rs.getInt(1);
				String foodName = rs.getString(2);
				double foodPrice =rs.getDouble(3);
				String foodcategory = rs.getString(4);
				String stockstatus = rs.getString(5);
				food food = new food(foodId, foodName, foodPrice, foodcategory, stockstatus);
				FoodByCategory.add(food);
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return FoodByCategory;
	}
	public static boolean DeleteMenuiItem(food s) {
		String sql = "call delete_food_menu(?)";
		try(Connection con = DBConnection.getConection(); 
			CallableStatement psmt = (CallableStatement) con.prepareCall(sql);){
			psmt.setString(1, s.getFood_name());
			
			
			
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		//food f1 = new food("f1", "coke", 15.5, "Drink");
		//addFood(f1);
		//food f3 = new food(1, "apple", 20.50, "fruit");
		//updateMenu(f3);
		
		/*try {
			List<food> foodbycategory =  foodController.getFoodByFoodCategory("snack");
			
			for (food food : foodbycategory) {
				System.out.println(food.toString());
			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}*/
		DeleteMenuiItem(new food("sushi"));
	}
}
