package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.storage.service.SQLBuilder;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.service.ImageService;

@Component
public class ImageServiceImpl implements ImageService{

	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public ImageServiceImpl(){
		
	}
	
	
	public Image insertNewImage(Image image) throws SQLException{

		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("INSERT INTO image (imagePath , text, recipeId, userId) "
					+ "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, SQLBuilder.toSQLString(image.getImagePath()));
		pstmt.setString(2, SQLBuilder.toSQLString(image.getText()));
		if (image.getRecipeId() != null){
			pstmt.setInt(3, image.getRecipeId());
		}
		else pstmt.setNull(3, java.sql.Types.INTEGER);
		if (image.getUserId() != null){
			pstmt.setInt(4, image.getUserId());
		}
		else pstmt.setNull(4, java.sql.Types.INTEGER);
		
		r = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			image.setId(id);
		}
		if (r != 0){
			System.out.println("Image at " + image.getImagePath() + " successfully inserted into database");
		}
		else {
			System.out.println("Image not created");
		}
		
		return image;
	}


	public int updateImage(Image image) throws SQLException {

		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("UPDATE image SET imagePath = ?, text = ?, recipeId = ?, "
				+ "userId = ? WHERE id = ?");
		pstmt.setString(1, SQLBuilder.toSQLString(image.getImagePath()));
		pstmt.setString(2, SQLBuilder.toSQLString(image.getText()));
		pstmt.setInt(3, image.getId());
		if (image.getRecipeId() != null){
			pstmt.setInt(3, image.getRecipeId());
		}
		else pstmt.setNull(3, java.sql.Types.INTEGER);
		if (image.getUserId() != null){
			pstmt.setInt(4, image.getUserId());
		}
		else pstmt.setNull(4, java.sql.Types.INTEGER);
		pstmt.setInt(5, image.getId());
		
		r = pstmt.executeUpdate();
		if (r != 0){
			System.out.println("Image at " + image.getImagePath() + " successfully updated in database");
		}
		else {
			System.out.println("Image not updated");
		}
			
		return r;
	}


	public int deleteImage(Integer id) throws SQLException {
		
		int result = -1;

		result = DbCommonFunctions.deleteEntity("image", id);
		if (result != -1){
			System.out.println("Successfully removed image with id " + id);
		}
		else {
			System.out.println("Unable to remove image entity with id " + id);
		}
		
		return result;
	}


	public Image getImageById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Image) manager.retrieveSingleEntity(constraints, Image.class);
	}
	
}
