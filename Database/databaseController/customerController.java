package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import Model.customer;
import Model.roomType;
import util.DBConnection;

public class customerController {

	public static boolean saveCustomer(customer s) {
		String sql = "insert into customer values(? , ? ,? ,?,?)";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getGuest_id());
			psmt.setString(2, s.getGuest_name());
			psmt.setString(3, s.getPhone_no());
			psmt.setString(4, s.getIdentity_card());
			psmt.setString(5, s.getEmail());
			 
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<customer> getAllCustomers(){
		String sql = "SELECT * FROM customer";
		ArrayList<customer> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String phNo = rs.getString(3);
				String idCard = rs.getString(4);
				String email = rs.getString(5);
				customer cus = new customer(id, name, phNo, idCard, email);
				list.add(cus);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static customer getCustomerById(String guestId) {
		String sql = "select * from customer where guest_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, guestId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
			}
			
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		//customer c1 = new customer("c3", "gg", "09484847387", "727fbnfj", "iudshfsefh");
		//saveCustomer(c1);
	}
}
