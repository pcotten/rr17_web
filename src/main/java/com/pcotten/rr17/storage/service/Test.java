package com.pcotten.rr17.storage.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.PantryService;
import com.pcotten.rr17.service.RecipeService;
import com.pcotten.rr17.service.UserService;
import com.pcotten.rr17.service.impl.RecipeServiceImpl;
import com.pcotten.rr17.service.impl.UserServiceImpl;

public class Test {

	
	
	public static void main(String[] args) {
		
		DatabaseManager dbManager = new DatabaseManager();
		UserService userService = new UserServiceImpl();
		RecipeService recipeService = new RecipeServiceImpl();

		
		//RETRIEVE ENTITIES TEST
//		Map<String, String> constraints = new HashMap<String, String>();
//		constraints.put("user.pantryId", "2");
//		List<Object> users = dbManager.retrieveEntities(constraints, User.class);
//		for (Object o : users) {
//			User user = (User) users.get(users.indexOf(o));
//			System.out.println(user.getFirstName() + " " + user.getLastName());
//		}
//	}
		
		
		User user = new User();
		int result = 0;
		
		// INSERT NEW USER TEST
		user.setFirstName("Charles");
		user.setLastName("Chappell");
		user.setAge(43);
		user.setUsername("cchappell");
		user.setPassword("password");
		user.setEmail("cchappell@gmail.com");
		user.setBio("Chuck's bio");
		user.setGender("M");
		user.setCity("Irving");
		user.setState("TX");
		user.setCountry("USA");
		
		String json = dbManager.mapToJSON(user);
		System.out.println(json);
////		user.setPantryCode("atM8-KtUd");
//
//		result = userService.insertNewUser(user);
//		if (result != 0) {
//			Map<String, String> constraints = new HashMap<String, String>();
//			constraints.put("username", SQLBuilder.toSQLString(user.getUsername()));
//			user = (User) dbManager.retrieveSingleEntity(constraints, User.class);
//			System.out.println("User " + user.getUsername() + " inserted with new pantry.  PantryCode: " + user.getPantryCode());
//		}
		
		
		// DELETE USER TEST
//		result = userService.deleteUser(35);
//		if (result != -1){
//			System.out.println("Sucssessfully deleted user 35");
//		}

		// CREATE RECIPE TEST
//		Integer userId = 1;
//		Recipe recipe = new Recipe();
//		recipe.setTitle("The Best Chocolate Cake");
//		recipe.setOwner("pcotten");
//		recipe.setDescription("This is the best chocolate cake recipe I have found to date");
//		recipe.setOvenTemp(350);
//		recipe.setAttributedTo("Unknown");
//		recipe.setNumberOfServings(12);
//		recipe.setCookTime("30 minutes");
//		recipe.setPrepTime("30 minutes");
//		recipe.getIngredients().put("salt", "1 teaspoon");
//		recipe.getIngredients().put("bakers unsweetened chocolate", "3 squares");
//		recipe.getIngredients().put("flour", "2 cups");
//		recipe.getIngredients().put("brown sugar", "2 1/4 cups");
//		recipe.getIngredients().put("water, boiling", "1 cup");
//		recipe.getInstructions().put(1, "Do this.");
//		recipe.getInstructions().put(2, "Do that.");
//		recipe.getInstructions().put(3, "Cook this.");
//		recipe.getInstructions().put(4, "Bake that.");
//		
//		RecipeService recipeService = new RecipeService();
//		recipeService.insertNewRecipe(recipe, userId);
		
		// CREATE MEAL TEST
//		Integer userId = 1;
//		Meal meal = new Meal();
//		meal.setName("Spaghetti with salad");
//		MealService mealService = new MealService();
//		mealService.insertNewMeal(meal, userId);
		
		
		// RETRIEVE FULL RECIPE TEST
//		Recipe recipe = recipeService.getRecipeByRecipeId(15);
		
		// DELETE RECIPE
		try {
			recipeService.deleteRecipe(22);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
