package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.User;

public interface UserService {

	public User createUser(User user) throws SQLException;
	
	public User getUserById(Integer userId);
	
	public User getUserByUsername(String username);
	
	public boolean updateUser(User user) throws SQLException;
	
	public boolean deleteUser(Integer id) throws SQLException;

	public List<Cookbook> getCookbooks(Integer userId);

	public Cookbook createCookbook(Integer userId, Cookbook cookbook);

	public List<Ingredient> getPantryIngredients(Integer userId);

	public Ingredient createPantryIngredient(Integer userId, Ingredient ingredient);

	public boolean updatePantryIngredient(Integer userId, Integer ingredientId, Ingredient ingredient);

	public boolean deletePantryIngredient(Integer userId, Integer ingredientId);

	public List<Meal> getMeals(Integer userId);

	public Meal createMeal(Integer userId, Meal meal);

	public boolean updateMeal(Integer userId, Integer mealId, Meal meal);

	public boolean deleteMeal(Integer userId, Integer mealId);

	public List<MealPlan> getMealPlans(Integer userId);

	public MealPlan createMealPlan(Integer userId, MealPlan mealPlan);

	public boolean updateMealPlan(Integer userId, Integer mealId, MealPlan mealPlan);

	public boolean deleteMealPlan(Integer userId, Integer mealPlanId);

	public boolean userExists(User user);


	

	

	
	
}
