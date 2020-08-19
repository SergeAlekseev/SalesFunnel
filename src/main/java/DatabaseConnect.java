import java.sql.*;
import java.util.Properties;

public class DatabaseConnect {

    private Connection connection;

    public DatabaseConnect(String dbName, String password) throws SQLException {
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            Properties info = new Properties();
            info.setProperty("user", "postgres");
            info.setProperty("password", password);
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/" + dbName, info);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        Statement stmt = connection.createStatement();
    }

    public ResultSet select(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    public int insert(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        int updateCount = stmt.executeUpdate(sql);
        stmt.close();
        connection.commit();
        return updateCount;
    }
}
