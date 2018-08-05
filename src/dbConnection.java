import java.sql.*;
import javax.swing.*;

public class dbConnection {
	
	public static Connection returnConnection() throws SQLException {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:///libms","root","shija123");
			return con;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in the connection.");
			return null;
		} 
	}
}