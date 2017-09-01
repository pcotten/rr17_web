package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Instruction;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.storage.entity.InstructionService;
import com.pcotten.rr17.storage.entity.RecipeService;
import com.pcotten.rr17.storage.entity.impl.InstructionServiceImpl;
import com.pcotten.rr17.storage.entity.impl.RecipeServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class InstructionService_Test {
	
	DatabaseManager manager;
	InstructionService instructionService;
	Instruction instruction;
	Recipe recipe;
	RecipeService recipeService;

	@Before
	public void init(){
		manager = new DatabaseManager();
		recipeService = new RecipeServiceImpl();
		
		recipe = new Recipe();
		recipe.setTitle("Test Recipe");
		recipe.setOwner("testUser1");
		recipe.setAttributedTo("unknown");
		recipe.setDescription("This is the best chocolate cake recipe I have found to date");
		recipe.setOvenTemp(350);
		recipe.setAttributedTo("Unknown");
		recipe.setNumberOfServings(12);
		recipe.setCookTime(30);
		recipe.setCookTimeUnit("minutes");
		recipe.setPrepTime(30);
		recipe.setPrepTimeUnit("minutes");
		Map<String, Object> quantityMap = new HashMap<String, Object>();
		quantityMap.put("quantity", 1f);
		quantityMap.put("quantityUnit", "teaspoon");
		recipe.getIngredients().put("salt", quantityMap);
		recipe.getInstructions().put(1, "Do this.");
		
		try {
			recipeService.insertNewRecipe(recipe, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		instructionService = new InstructionServiceImpl();
		instruction = new Instruction();
		
		instruction.setOrderIndex(1);
		instruction.setText("Test text");
		instruction.setRecipeId(recipe.getId());
		
	}
	
	@Test
	public void InstructionService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		instruction = instructionService.insertNewInstruction(instruction);
		assertTrue(instruction.getId() != null); 
		
		// Test read operations
		
		instruction = instructionService.getInstructionById(instruction.getId());
		
		// Test update operation
		
		instruction.setOrderIndex(2);
		instruction.setText("Changed Text");
		
		result = instructionService.updateInstruction(instruction);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = instructionService.deleteInstruction(instruction.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM instruction WHERE id = " + instruction.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}
	
	@After
	public void cleanup() throws SQLException{
		recipeService.deleteRecipe(recipe.getId());
	}

}
