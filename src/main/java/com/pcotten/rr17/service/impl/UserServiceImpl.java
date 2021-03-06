package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.SQLBuilder;
import com.pcotten.rr17.dao.UserDAO;
import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.Pantry;
import com.pcotten.rr17.model.User;
import com.pcotten.rr17.service.CategoryService;
import com.pcotten.rr17.service.CookbookService;
import com.pcotten.rr17.service.IngredientService;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.PantryService;
import com.pcotten.rr17.service.UserService;

@Component
public class UserServiceImpl implements UserService {
	
	@Inject
	DatabaseManager manager;
	@Inject
	CookbookService cookbookService;
	@Inject
	IngredientService ingredientService;
	@Inject
	MealPlanService mealPlanService;
	@Inject
	MealService mealService;
	@Inject
	PantryService pantryService;
	@Inject
	CategoryService categoryService;
	@Inject
	UserDAO userDAO;

	
	public UserServiceImpl(){
		
	}
	
	@Override
	public User getUserById(Integer userId) {
		
		return userDAO.getUser(userId);

	}
	
	@Override
	public User createUser(User user) throws SQLException{

		int r = 0;

		if (user.getPantryCode() == null){
			user.setPantryCode(pantryService.createPantry(new Pantry()).getPantryCode());
		}

		user = userDAO.createUser(user);

		if (user.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("User entity " + user.getUsername() + " successfully inserted into database with id " + user.getId());
		}
		else {
			System.out.println("Unable to complete user insert - failed to insert user entity");
			throw new SQLException();
		}
		
		return user;
	}
	



	@Override
	public boolean updateUser(User user) throws SQLException{
		
		int r = userDAO.updateUser(user);
		// check for success 
		if (r != 0){
			System.out.println("User " + user.getUsername() + " successfully updated");
			return true;
		}
		else {
			System.out.println("User " + user.getUsername() + " not updated");
			return false;
		}
	}
	
	@Override
	public boolean deleteUser(Integer id) throws SQLException{

		int result = -1;

		result = userDAO.deleteUser(id);
		// check for success
		if (result != -1){
			System.out.println("Successfully removed user " + id);
			return true;
		}
		else {
			System.out.println("Unable to remove user" + id);
			return false;
		}
	}

	@Override
	public List<Cookbook> getCookbooks(Integer userId) throws SQLException {
		
		List<Cookbook> cookbooks = cookbookService.getCookbooks(userId);
		
		return cookbooks;
	}

	@Override
	public Cookbook createCookbook(Integer userId, Cookbook cookbook) throws SQLException {

		cookbook = cookbookService.createCookbook(cookbook, userId);
		
		return cookbook;
	}

	@Override
	public List<Ingredient> getPantryIngredients(Integer userId) throws SQLException {
		// This should be delegated to the pantry service and pantryDAO
		
		List<Ingredient> ingredients = pantryService.getPantryIngredients(userId);

//		conn = manager.getConnection();
//		PreparedStatement pstmt = null;
//		Integer pantryId = pantryService.getPantryId(userId);
//		pstmt = conn.prepareStatement("SELECT * FROM ingredients_by_pantryid WHERE pantryId = ?");
//		pstmt.setInt(1, pantryId);
//		
//		ResultSet result = pstmt.executeQuery();
//		while (result.next()) {
//			Ingredient ingredient = new Ingredient();
//			ingredient.setId(result.getInt("ingredientId"));
//			ingredient.setName(result.getString("name"));
//			ingredient.setDescription(result.getString("description"));
//			ingredient.setQuantity(result.getFloat("quantity"));
//			ingredient.setQuantityUnit(result.getString("quantityUnit"));
//			ingredient.setCategories(ingredientService.getIngredientCategories(ingredient.getId()));
//			ingredients.add(ingredient);
//		}
		
		return ingredients;
	}

	@Override
	public boolean createPantryIngredient(Integer userId, Ingredient ingredient) {
		
		boolean success = pantryService.createPantryIngredient(ingredient, userId);
		
		return success;
	}

	@Override
	public boolean updatePantryIngredient(Integer userId, Integer ingredientId, Ingredient ingredient) throws SQLException {
		// This should be delegated to the pantry service
		
		return pantryService.updatePantryIngredient(userId, ingredient);

//		conn = manager.getConnection();
//		PreparedStatement pstmt = null;
//		
//		pstmt = conn.prepareStatement("UPDATE pantry_ingredient SET quantity = ?, quantityUnit = ? "
//				+ "WHERE pantryId = ? AND ingredientId = ?");
//		pstmt.setFloat(1, ingredient.getQuantity());
//		pstmt.setString(2, ingredient.getQuantityUnit());
//		pstmt.setInt(3, pantryService.getPantryId(userId));
//		pstmt.setInt(4, ingredient.getId());
//		
//		r = pstmt.executeUpdate();

	}

	@Override
	public boolean deletePantryIngredient(Integer userId, Integer ingredientId) throws SQLException {
		// This should be delegated to the pantry service
		
		return pantryService.deletePantryIngredient(userId, ingredientId);
		
//		conn = manager.getConnection();
//		PreparedStatement pstmt = null;
//		
//		pstmt = conn.prepareStatement("DELETE FROM pantry_ingredient WHERE pantryId = ? AND ingredientId = ?");
//		pstmt.setInt(1, pantryService.getPantryId(userId));
//		pstmt.setInt(2, ingredientId);
//		
//		r = pstmt.executeUpdate();
//			

	}
	
	@Override
	public List<Category> getPantryCategories(Integer userId) throws SQLException {
		
		List<Category> categories = categoryService.getPantryCategories(userId);
		
		return categories;
	}

	@Override
	public List<Meal> getMeals(Integer userId) throws SQLException {
		
		List<Meal> meals = mealService.getMeals(userId);

//		conn = manager.getConnection();
//		PreparedStatement pstmt = null;
//		pstmt = conn.prepareStatement("SELECT * FROM meals_by_userid WHERE userid = ?");
//		pstmt.setInt(1, userId);
//		
//		ResultSet result = pstmt.executeQuery();
//		while (result.next()) {
//			Meal meal = new Meal();
//			meal.setId(result.getInt("mealId"));
//			meal.setName(result.getString("name"));
//			Date date = result.getDate("lastPrepared");
//			meal.setLastPrepared(date.toLocalDate());
//			meals.add(meal);
//		}
		
		return meals;
	}

	@Override
	public Meal createMeal(Integer userId, Meal meal) throws SQLException {

		meal = mealService.createMeal(meal, userId);

		return meal;
	}

	@Override
	public boolean updateMeal(Integer userId, Integer mealId, Meal meal) throws SQLException {

		meal.setId(mealId);
		boolean success = mealService.updateMeal(meal);

		return success;
	}

	@Override
	public boolean deleteMeal(Integer userId, Integer mealId) throws SQLException {
		
		boolean success = mealService.deleteMeal(mealId, userId);

		return success;
	}

	@Override
	public List<MealPlan> getMealPlans(Integer userId) throws SQLException {
		// delegate to MealPlanService
		
		List<MealPlan> mealPlans = mealPlanService.getMealPlans(userId);

//		conn = manager.getConnection();
//		PreparedStatement pstmt = null;
//		pstmt = conn.prepareStatement("SELECT * FROM mealplans_by_userid WHERE userid = ?");
//		pstmt.setInt(1, userId);
//		
//		ResultSet result = pstmt.executeQuery();
//		while (result.next()) {
//			MealPlan mealPlan = new MealPlan();
//			mealPlan.setId(result.getInt("mealPlanId"));
//			mealPlan.setName(result.getString("name"));
//			mealPlan.setOwner(result.getInt("ownerId"));
//			mealPlan.setMeals(mealService.getMealPlanMeals(mealPlan.getId()));
//			mealPlans.add(mealPlan);
//		}
		
		return mealPlans;
	}

	@Override
	public MealPlan createMealPlan(Integer userId, MealPlan mealPlan) throws SQLException {

		mealPlan = mealPlanService.createMealPlan(mealPlan, userId);

		return mealPlan;
	}

	@Override
	public boolean updateMealPlan(Integer userId, Integer mealPlanId, MealPlan mealPlan) throws SQLException {
		
		mealPlan.setId(mealPlanId);
		boolean success = mealPlanService.updateMealPlan(mealPlan);

		if (success){
			System.out.println("MealPlan successfully updated");
			return true;
		}
		else {
			System.out.println("MealPlan not updated");
			return false;
		}
	}

	@Override
	public boolean deleteMealPlan(Integer userId, Integer mealPlanId) throws SQLException {
		
		boolean success = mealPlanService.deleteMealPlan(mealPlanId);

		if (success) {
			System.out.println("Ingredient " + mealPlanId + " deleted from pantry.");
			return true;
		}
		else {
			System.out.println("Failed to delete " + mealPlanId + " ingredient from pantry");
			return false;
		}
	}

	@Override
	public boolean userExists(User user) throws SQLException {
		Connection conn = manager.getConnection();
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("SELECT count(*) FROM user WHERE username = ?");
		pstmt.setString(1, user.getUsername());

		return manager.isExists(pstmt);
	}

	@Override
	public User getUserByUsername(String username){
		
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("username", SQLBuilder.toSQLString(username));
		
		return (User) manager.retrieveSingleEntity(constraints, User.class);
	}







	
	
	
}
