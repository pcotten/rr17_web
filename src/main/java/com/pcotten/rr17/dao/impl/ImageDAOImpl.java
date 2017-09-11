package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.ImageDAO;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.model.User;

public class ImageDAOImpl extends JdbcDaoSupport implements ImageDAO{
	

	
	private static class UserRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Image image = new Image();
			image.setId(rs.getInt("id"));
			image.setImagePath(rs.getString("imagePath"));
			image.setRecipeId(rs.getInt("recipeId"));
			image.setUserId(rs.getInt("userId"));
			image.setText(rs.getString("text"));
			
			return image;
		}
		
	}
	
}


