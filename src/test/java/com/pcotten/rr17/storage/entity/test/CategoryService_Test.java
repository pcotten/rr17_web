package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.service.CategoryService;
import com.pcotten.rr17.service.impl.CategoryServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class CategoryService_Test {

	DatabaseManager manager;
	Category category;
	CategoryService categoryService = new CategoryServiceImpl();

	@Before
	public void init(){
		manager = new DatabaseManager();
		category = new Category();
		category.setName("TestCategory");
	}
	
	@Test
	public void CategoryService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		category = categoryService.createCategory(category);
		assertTrue(category.getId() != null);
		
		category = categoryService.getCategoryByName(category.getName());
		
		// Test update operation
		
		category.setDescription("Changed description");
		
		result = categoryService.updateCategory(category);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = categoryService.deleteCategory(category.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM category WHERE id = " + category.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}


}
