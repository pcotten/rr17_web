package com.pcotten.rr17.storage.entity.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.RecipeService;
import com.pcotten.rr17.service.impl.MealPlanServiceImpl;
import com.pcotten.rr17.service.impl.MealServiceImpl;
import com.pcotten.rr17.service.impl.RecipeServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class MealPlanService_Test {

	Recipe recipe;
	Meal meal;
	MealPlan mealPlan;
	DatabaseManager manager;
	RecipeService recipeService;
	MealService mealService;
	MealPlanService mealPlanService;

	
	@Before
	public void init(){
		manager = new DatabaseManager();
		recipeService = new RecipeServiceImpl();
		mealService = new MealServiceImpl();
		mealPlanService = new MealPlanServiceImpl();

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
		recipe.getInstructions().put(1, "Do this.");

		
		meal = new Meal();
		meal.setName("Test Meal");
		meal.setLastPrepared(LocalDate.now());
		meal.setRecipes(new ArrayList<Integer>());
		
		mealPlan = new MealPlan();
		mealPlan.setName("Test MealPlan");
		mealPlan.setDescription("Test MealPlan Description");

	}
	
	@Test
	public void MealPlanService_CRUD() throws SQLException {
		int result = 0;
		
		// Set up recipe for meal
		recipe = recipeService.insertNewRecipe(recipe, 1);
		meal.getRecipes().add(recipe.getId());
		
		meal = mealService.createMeal(meal, 1);
		assertTrue(meal.getId() != null); 
		
		// Test create operation
		
		mealPlan = mealPlanService.createMealPlan(mealPlan, 1);
		assertTrue(mealPlan.getId() != null);
		
		// Test read operations
		
		mealPlan = mealPlanService.getMealPlan(mealPlan.getId());
		
		
		// Test update operation
		
		mealPlan.setDescription("Changed mealPlan description");
		meal.setName("New meal name");
		
		result = mealService.updateMeal(meal);
		assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
			result = mealPlanService.deleteMealPlan(mealPlan.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM mealPlan WHERE id = " + mealPlan.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}
	
	@After
	public void cleanup() throws SQLException{
		recipeService.deleteRecipe(recipe.getId());
		mealService.deleteMeal(meal.getId());
	}

}
