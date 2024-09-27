package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.room;
import Model.roomType;
import Model.room_status;
import util.DBConnection;

public class roomStatusController {
	public static room_status getRoomStatusById(String roomStatusId) {
		String sql = "select * from room_status_table where room_status_id=?";
		try (Connection con = DBConnection.getConection();
				PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, roomStatusId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return new room_status(rs.getString(1), rs.getString(2));
			
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
