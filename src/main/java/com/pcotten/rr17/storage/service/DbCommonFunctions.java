package com.pcotten.rr17.storage.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DbCommonFunctions {
	
	static DatabaseConfig config = new DatabaseConfig();
	static DatabaseManager manager = new DatabaseManager();
	
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;
	static String sql = null;
	
	public static int deleteEntity(String table, Integer id) throws SQLException{
		int result = -1;

		conn = manager.getConnection();
		pstmt = conn.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
		pstmt.setInt(1, id);
		result = pstmt.executeUpdate();
		
		return result;
	}
	
}
