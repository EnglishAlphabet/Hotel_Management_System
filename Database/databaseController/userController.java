package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Model.booking;
import Model.customer;
import Model.food;
import Model.order;
import Model.stock;
import Model.user;
import util.DBConnection;

public class userController {

	public static boolean addUser(user s) {
		String sql = "insert into user values(?,?,?,?,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
				PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getUser_id());
			psmt.setString(2, s.getUser_name());
			psmt.setString(3, s.getprivilage());
			psmt.setString(4, s.getPassword());
			psmt.setString(5, s.getStatus());
			psmt.setString(6, s.getLinTime().toString());
			psmt.setString(7, s.getLoutTime().toString());


			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<user> getAllUsers(){
		String sql = "SELECT * FROM user";
		ArrayList<user> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String username = rs.getString(2);
				String privilage = rs.getString(3);
				String password = rs.getString(4);
				String status = rs.getString(5);
				LocalDateTime linTime = rs.getTimestamp(6).toLocalDateTime();
				LocalDateTime loutTime = rs.getTimestamp(7).toLocalDateTime();
				user use = new user(id, username, password,privilage,status,linTime,loutTime);
				list.add(use);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean ifExists(String id) {
		String sql = "SELECT EXISTS(SELECT 1 FROM `user` WHERE user_id = ?)";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){

			psmt.setString(1, id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				return rs.getBoolean(1);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}
	public static user getUserById(String id) {
		String sql = "select * from user where user_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				String uID = rs.getString(1);
				String uName = rs.getString(2);
				String pw = rs.getString(4);
				String priv = rs.getString(3);
				String status = rs.getString(5);
				LocalDateTime linTime =rs.getTimestamp(6).toLocalDateTime();
				LocalDateTime loutTime =rs.getTimestamp(7).toLocalDateTime();
				return new user(uID,uName, priv,pw,status,linTime,loutTime);
			}
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getPasswordById(String id) {
		String hashedPassword = null;
		String sql = "select pass_word from user where user_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				hashedPassword = rs.getString(1);
				
			}	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return hashedPassword;
	}
	public static String getPrivById(String id) {
		
		String priv = "" ;
		String sql = "select privilege from user where user_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, id);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				priv = rs.getString(1);
				
			}	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return priv;
	}
	public static boolean updatePassword(String id , String pw ) {
		String sql = "update user set pass_word=? where user_id=?";
		try (Connection con = DBConnection.getConection();) {
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, pw);
			psmt.setString(2, id);
			psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getAllUsers());
		System.out.println(ifExists("A01"));
		System.out.println(getPasswordById("A01"));
	}

}
