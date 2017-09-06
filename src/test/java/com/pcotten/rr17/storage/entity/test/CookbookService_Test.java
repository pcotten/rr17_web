package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.impl.CookbookServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class CookbookService_Test {

	DatabaseManager manager;
	CookbookService cookbookService;
	Cookbook cookbook;

	@Before
	public void init(){
		manager = new DatabaseManager();
		cookbookService = new CookbookServiceImpl();
		cookbook = new Cookbook();
		
		cookbook.setTitle("Test Cookbook");
		cookbook.setCreatorId(1);
		cookbook.setRecipes(new ArrayList<Recipe>());
	}

		
	
	@Test
	public void CookbookService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		cookbook = cookbookService.createCookbook(cookbook, 1);
		assertTrue(cookbook.getId() != null); 
		
		// Test read operations
		
		cookbook = cookbookService.getCookbookById(cookbook.getId());
		
		// Test update operation
		
		cookbook.setTitle("Changed title");
		cookbook.setCategories(new ArrayList<Category>());
		
		result = cookbookService.updateCookbook(cookbook);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = cookbookService.deleteCookbook(cookbook.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM cookbook WHERE id = " + cookbook.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
	}
}
