package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.booking;
import Model.room;
import Model.roomType;
import Model.section;
import util.DBConnection;

public class sectionController {
	public static section getSectionById(String section) {
		String sql = "select * from roomtype where booking_id=? , room_no =?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, section);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new section(bookingController.getBookingById(rs.getString(1))
						, new room(rs.getString(2)), new roomType(rs.getString(3)),
						rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getDouble(7));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
