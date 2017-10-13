package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.User;

public interface UserService {

	public User getUserById(Integer userId);
	
	public User createUser(User user) throws SQLException;
	
	public boolean updateUser(User user) throws SQLException;
	
	public boolean deleteUser(Integer id) throws SQLException;

	public List<Cookbook> getCookbooks(Integer userId) throws SQLException;

	public Cookbook createCookbook(Integer userId, Cookbook cookbook) throws SQLException;

	public List<Ingredient> getPantryIngredients(Integer userId) throws SQLException;

	public boolean createPantryIngredient(Integer userId, Ingredient ingredient);

	public boolean updatePantryIngredient(Integer userId, Integer ingredientId, Ingredient ingredient) throws SQLException;

	public boolean deletePantryIngredient(Integer userId, Integer ingredientId) throws SQLException;

	public List<Category> getPantryCategories(Integer userId) throws SQLException;

	public List<Meal> getMeals(Integer userId) throws SQLException ;

	public Meal createMeal(Integer userId, Meal meal) throws SQLException;

	public boolean updateMeal(Integer userId, Integer mealId, Meal meal) throws SQLException;

	public boolean deleteMeal(Integer userId, Integer mealId) throws SQLException;

	public List<MealPlan> getMealPlans(Integer userId) throws SQLException;

	public MealPlan createMealPlan(Integer userId, MealPlan mealPlan) throws SQLException;

	public boolean updateMealPlan(Integer userId, Integer mealId, MealPlan mealPlan) throws SQLException;

	public boolean deleteMealPlan(Integer userId, Integer mealPlanId) throws SQLException;

	public boolean userExists(User user) throws SQLException;

	public User getUserByUsername(String username);

	

	

	
	
}
