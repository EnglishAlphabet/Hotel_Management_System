package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.food;
import Model.service;
import util.DBConnection;

public class serviceController {
	
	public static boolean addService(service s) {
		String sql = "insert into service values(?,?,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getService_id());
			psmt.setString(2, s.getService_name());
			psmt.setDouble(3, s.getPrice());
			psmt.setString(4, s.getDescription());
			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<service> getAllServices(){
		String sql = "SELECT * FROM service";
		ArrayList<service> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				double SPrice = rs.getDouble(3);
				String SDescription = rs.getString(4);
				service serve = new service(id, name, SPrice, SDescription);
				list.add(serve);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static service getServiceById(String serviceId) {
		String sql = "select * from service where service_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, serviceId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new service(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4));
			}
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean updateService(service s) {
		String sql = "update service set service_name=?, service_price=? ,description=? where service_id=?";
		try (Connection con = DBConnection.getConection();) {
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setString(1, s.getService_name());
			psmt.setDouble(2, s.getPrice());
			psmt.setString(3, s.getDescription());
			psmt.setString(4, s.getService_id());
			 psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
}
