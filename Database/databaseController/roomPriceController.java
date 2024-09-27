package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.roomType;
import Model.room_price;
import Model.service;
import util.DBConnection;

public class roomPriceController {
	public static ArrayList<room_price> getAllRoomPrice(){
		String sql = "SELECT * FROM room_price_table";
		ArrayList<room_price> list = new ArrayList<>();
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				double ppNight = rs.getDouble(2);
				double ppDay = rs.getDouble(3);
				room_price roomPrice = new room_price(roomTypeController.getRoomTypeById(id),ppNight , ppDay );
				list.add(roomPrice);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public static boolean updateRoomPrice(room_price s) {
		String sql = "update room_price_table set price_per_night=? , price_per_hour=? where room_type_id=?";
		try (Connection con = DBConnection.getConection();) {
			
			PreparedStatement psmt = con.prepareStatement(sql);
			
			psmt.setDouble(1, s.getPricePerNight());
			psmt.setDouble(2, s.getPricePerHour());
			psmt.setString(3, s.getId().getRoom_type());
			
			 psmt.executeUpdate();
			return true;
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public static void main(String[] args) {
		updateRoomPrice(new room_price(new roomType("r1"), 70000, 7000));
	}
}
