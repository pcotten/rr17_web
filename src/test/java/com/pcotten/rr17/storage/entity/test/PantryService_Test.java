package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.service.PantryService;
import com.pcotten.rr17.service.impl.PantryServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class PantryService_Test {

	DatabaseManager manager = new DatabaseManager();
	Pantry pantry;
	PantryService pantryService;
	
	@Before
	public void init(){
		pantry = new Pantry();
		pantryService = new PantryServiceImpl();
	}
	
	@Test
	public void PantryService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		pantry = pantryService.createPantry(pantry);
		assertTrue(pantry.getPantryCode() != null);
		
		String code = pantry.getPantryCode();

		// Test read operation
		
		pantry = pantryService.getPantryById(pantry.getId());
		assertTrue(pantry.getPantryCode().equals(code));
		
		// Test update operation
		
		pantry.setDescription("Changed pantry description");
		
		result = pantryService.updatePantry(pantry);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = pantryService.deletePantry(pantry.getId());
//			result = pantryService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for pantry
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM pantry WHERE id = " + pantry.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}

}
