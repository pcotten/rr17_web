package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.impl.IngredientServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class IngredientService_Test {

	DatabaseManager manager;
	IngredientService ingredientService;
	Ingredient ingredient;

	@Before
	public void init(){
		manager = new DatabaseManager();
		ingredientService = new IngredientServiceImpl();
		ingredient = new Ingredient();
		
		ingredient.setName("TestIngredient");
		ingredient.setDescription("Test description");
		ingredient.setQuantity(3f);
		ingredient.setQuantityUnit("bits");
		
	}
	
	@Test
	public void IngredientService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		ingredient = ingredientService.createIngredient(ingredient);
		assertTrue(ingredient.getId() != null); 
		
		// Test read operations
		
		ingredient = ingredientService.getIngredient(ingredient.getId());
		
		// Test update operation
		
		ingredient.setDescription("Changed description");
		ingredient.setQuantity(2f);
		ingredient.setQuantityUnit("schnarfs");
		
		result = ingredientService.updateIngredient(ingredient);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = ingredientService.deleteIngredient(ingredient.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM ingredient WHERE id = " + ingredient.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}
}
