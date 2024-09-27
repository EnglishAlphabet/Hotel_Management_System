public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://hotel-oversunset-sunset-hotel.c.aivencloud.com:24376/sunset_hotel_db";
        String username = "avnadmin";
        String password = "AVNS_RaPaAPRKW7lPQKNNO3x";

        return DriverManager.getConnection(url, username, password);

    }
