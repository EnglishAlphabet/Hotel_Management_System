public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "";
        String username = "";
        String password = "";

        return DriverManager.getConnection(url, username, password);

    }
