package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "insert into users values(?,?,?)";
		try(Connection con = DBConnection.getConection(); 
				PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getUser_id());
			psmt.setString(3, s.getprivilage());
			psmt.setString(2, s.getPassword());


			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<user> getAllUsers(){
		String sql = "SELECT * FROM users";
		ArrayList<user> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String privilage = rs.getString(3);
				String password = rs.getString(2);
				user use = new user(id, privilage, password);
				list.add(use);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean ifExists(String id) {
		String sql = "SELECT EXISTS(SELECT 1 FROM `users` WHERE id = ?)";
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
		String sql = "select * from users where id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, id);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				String uID = rs.getString(1);
				String pw = rs.getString(2);
				String priv = rs.getString(3);
				return new user(uID,pw, priv);
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
		String sql = "select password from users where id=?";
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
		
		String priv = null;
		String sql = "select privilege from users where id=?";
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
		String sql = "update users set password=? where id=?";
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
