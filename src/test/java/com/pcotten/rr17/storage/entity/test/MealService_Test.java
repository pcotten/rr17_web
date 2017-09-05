package com.pcotten.rr17.storage.entity.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.RecipeService;
import com.pcotten.rr17.service.impl.MealServiceImpl;
import com.pcotten.rr17.service.impl.RecipeServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class MealService_Test {

	
	Recipe recipe;
	Meal meal;
	DatabaseManager manager;
	RecipeService recipeService;
	MealService mealService;

	
	@Before
	public void init(){
		manager = new DatabaseManager();
		recipeService = new RecipeServiceImpl();
		mealService = new MealServiceImpl();

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
		
		meal = new Meal();
		meal.setName("Test Meal");
		meal.setLastPrepared(LocalDate.now());
		meal.setRecipes(new ArrayList<Integer>());

	}
	
	@Test
	public void MealService_CRUD() throws SQLException {
		int result = 0;
		
		// Set up recipe for meal
		recipe = recipeService.insertNewRecipe(recipe, 1);
		meal.getRecipes().add(recipe.getId());
		
		// Test create operation
		meal = mealService.insertNewMeal(meal, 1);
		Assert.assertTrue(meal.getId() != null); 
		
		// Test read operations
		
		meal = mealService.getMealById(meal.getId());
		
		
		// Test update operation
		
		meal.setLastPrepared(LocalDate.now().minusDays(3));
		meal.setName("New meal name");
		
		result = mealService.updateMeal(meal);
		Assert.assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = mealService.deleteMeal(meal.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM meal WHERE id = " + meal.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		Assert.assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}
	
	@After
	public void cleanup() throws SQLException{
		recipeService.deleteRecipe(recipe.getId());
	}
}
