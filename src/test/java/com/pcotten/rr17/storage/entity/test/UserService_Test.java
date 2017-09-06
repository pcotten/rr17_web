package com.pcotten.rr17.storage.entity.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.ImageService;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.InstructionService;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.RecipeService;
import com.pcotten.rr17.service.UserService;
import com.pcotten.rr17.service.impl.CookbookServiceImpl;
import com.pcotten.rr17.service.impl.ImageServiceImpl;
import com.pcotten.rr17.service.impl.MealPlanServiceImpl;
import com.pcotten.rr17.service.impl.MealServiceImpl;
import com.pcotten.rr17.service.impl.RecipeServiceImpl;
import com.pcotten.rr17.service.impl.UserServiceImpl;
import com.pcotten.rr17.storage.service.DatabaseManager;

public class UserService_Test {
	
	User user;
	Recipe recipe;
	Meal meal;
	MealPlan mealPlan;
	Cookbook cookbook;
	Image profilePic;
	DatabaseManager manager;
	UserService userService;
	RecipeService recipeService;
	MealService mealService;
	MealPlanService mealPlanService;
	CookbookService cookbookService;
	ImageService imageService;
	InstructionService instructionService;
	IngredientService ingredientService;
	
	
	@Before
	public void init(){
		manager = new DatabaseManager();
		userService = new UserServiceImpl();
		recipeService = new RecipeServiceImpl();
		mealService = new MealServiceImpl();
		mealPlanService = new MealPlanServiceImpl();
		cookbookService = new CookbookServiceImpl();
		imageService = new ImageServiceImpl();
		
		user = new User();
		user.setUsername("testUser1");
		user.setPassword("testPass");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setAge(30);
		user.setBio("Test user bio");
		user.setEmail("testUser@reciperex.com");
		user.setCity("Nowhere");
		user.setState("NoState");
		user.setCountry("NoCountry");
		user.setGender("M");

		profilePic = new Image();
		profilePic.setImagePath("file:///c:/Path/To/Pic");
		profilePic.setText("Profile picture text");
		
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
		quantityMap = new HashMap<String, Object>();
		quantityMap.put("quantity", 3f);
		quantityMap.put("quantityUnit", "squares");
		recipe.getIngredients().put("bakers unsweetened chocolate", quantityMap);
		quantityMap = new HashMap<String, Object>();
		quantityMap.put("quantity", 2f);
		quantityMap.put("quantityUnit", "cups");
		recipe.getIngredients().put("flour", quantityMap);
		quantityMap = new HashMap<String, Object>();
		quantityMap.put("quantity", 2.25f);
		quantityMap.put("quantityUnit", "cups");
		recipe.getIngredients().put("brown sugar", quantityMap);
		quantityMap = new HashMap<String, Object>();
		quantityMap.put("quantity", 1f);
		quantityMap.put("quantityUnit", "cup");
		recipe.getIngredients().put("water, boiling", quantityMap);
		recipe.getInstructions().put(1, "Do this.");
		recipe.getInstructions().put(2, "Do that.");
		recipe.getInstructions().put(3, "Cook this.");
		recipe.getInstructions().put(4, "Bake that.");
		
		meal = new Meal();
		meal.setName("Test Meal");
		meal.setLastPrepared(LocalDate.now());
		meal.setRecipes(new ArrayList<Integer>());
		
		mealPlan = new MealPlan();
		mealPlan.setName("Test MealPlan");
		mealPlan.setMeals(new ArrayList<Integer>());
		
		cookbook = new Cookbook();
		cookbook.setTitle("Test Cookbook");
		
	}
	
	@Test
	public void UserService_CRUD() throws SQLException {
		int result = 0;
		
		// Test create operation
		user = userService.createUser(user);
		Assert.assertTrue(user.getId() != null); 
		user = userService.getUserByUsername(user.getUsername());
		
		// Set up internal references to entities for deletion test
		recipeService.insertNewRecipe(recipe, user.getId());

		meal.getRecipes().add(recipe.getId());
		mealService.createMeal(meal, user.getId());

		mealPlan.getMeals().add(meal.getId());
		mealPlanService.insertNewMealPlan(mealPlan, user.getId());

		cookbook.setCreatorId(user.getId());
		cookbook.getRecipes().add(recipe);
		cookbookService.createCookbook(cookbook, user.getId());

		profilePic.setRecipeId(null);
		profilePic.setUserId(user.getId());
		imageService.insertNewImage(profilePic);
		user.setProfilePic(profilePic);
		
		// Test update operation
		
		user.setBio("Changed bio for testUser");
		user.setAge(42);
		user.setEmail("someone@somewhere.com");
		
		userService.updateUser(user);
		Assert.assertTrue(result == 1);
		
		// Test delete operation
		result = -1;
		try {
		userService.deleteUser(user.getId());
//			result = userService.deleteUser(59);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(result != -1);
		
		// Check database for user
		Connection conn = manager.getConnection();
		
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) FROM user WHERE id = " + user.getId();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		Assert.assertTrue(rs.getInt("COUNT(*)") == 0);
		
	}

}
