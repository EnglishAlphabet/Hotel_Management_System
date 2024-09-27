package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.customer;
import Model.room;
import Model.roomType;

import util.DBConnection;

public class roomTypeController {
	public static boolean saveRoomType(roomType s) {
		String sql = "insert into room values(? , ? )";
		try(Connection con = DBConnection.getConection(); 
			PreparedStatement psmt = con.prepareStatement(sql);){
			psmt.setString(1, s.getRoom_type());		
			psmt.setString(2, s.getDescription());
			int r = psmt.executeUpdate();
			return r>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<roomType> getAllRoomType(){
		String sql = "SELECT * FROM roomtype";
		ArrayList<roomType> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String roomType = rs.getString(1);				
				String roomdes = rs.getString(2);
				roomType rtype = new roomType(roomType,  roomdes);
				list.add(rtype);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static roomType getRoomTypeById(String roomtype) {
		String sql = "select * from roomtype where room_type_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, roomtype);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new roomType(rs.getString(1), rs.getString(2));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
