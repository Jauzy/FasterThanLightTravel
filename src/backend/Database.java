package backend;

import java.sql.*;

public class Database {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/tubespbo";
    static final String USER = "root";
    static final String PASS = "";
    
    public static String email = "";
    
    static Connection conn;
    public static Statement stmt;
    static ResultSet rs;
    
    public Database() throws SQLException{
        getConnection();
        stmt = conn.createStatement(); //buat objek statment
    }
    
    public void getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS); //buat connection  
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet query(String sql) throws SQLException{
        ResultSet s = stmt.executeQuery(sql); //execute query
        return s;
    }
    
    public void update(String sql) throws SQLException{
        stmt.executeUpdate(sql); //execute query
    }
}
