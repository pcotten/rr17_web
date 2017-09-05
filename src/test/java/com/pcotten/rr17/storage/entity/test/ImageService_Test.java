package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.service.ImageService;
import com.pcotten.rr17.service.impl.ImageServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class ImageService_Test {

	DatabaseManager manager;
	ImageService imageService;
	Image image;

	@Before
	public void init(){
		manager = new DatabaseManager();
		imageService = new ImageServiceImpl();
		image = new Image();
		
		image.setImagePath("C:/Path/To/Image");
		image.setText("Test Text");
		image.setUserId(1);
	}

		
	
	@Test
	public void ImageService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		image = imageService.insertNewImage(image);
		assertTrue(image.getId() != null); 
		
		// Test read operations
		
		image = imageService.getImageById(image.getId());
		
		// Test update operation
		
		image.setText("Changed text");
		image.setImagePath("C:/Some/Other/Path");
		
		result = imageService.updateImage(image);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = imageService.deleteImage(image.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM image WHERE id = " + image.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
	}
}