package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.PantryDAO;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.model.User;

public class PantryDAOImpl extends JdbcDaoSupport implements PantryDAO {
	

	
	private static class UserRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Pantry pantry = new Pantry();
			pantry.setId(rs.getInt("id"));
			pantry.setPantryCode(rs.getString("pantryCode"));
			pantry.setDescription(rs.getString("description"));
			
			return pantry;
		}
		
	}
	
}


