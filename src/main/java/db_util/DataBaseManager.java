package db_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {

	public static Connection getDBConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(DriverDetails.DRIVER);
			con = DriverManager.getConnection(DriverDetails.URL, DriverDetails.USERID, DriverDetails.PASSWORD);
		} catch (Exception e) {
			System.out.println("Error establishing DB connection : " + e.getMessage());
			throw new Exception("Error connecting to the db!");
		}
		return con;
	}

	public static void closeDBConnection(Connection con) throws Exception {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Error closing DB connection : " + e.getMessage());
				throw new Exception("Error closing DB connection");
			}
		}
	}

}
