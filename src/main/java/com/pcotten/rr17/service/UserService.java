package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.User;

public interface UserService {

	public User createUser(User user) throws SQLException;
	
	public User getUserById(Integer userId);
	
	public User getUserByUsername(String username);
	
	public ResponseEntity<Void> updateUser(User user) throws SQLException;
	
	public ResponseEntity<Void> deleteUser(Integer id) throws SQLException;

	public ResponseEntity<List<Cookbook>> getCookbooks(Integer userId);

	public ResponseEntity<Cookbook> createCookbook(Integer userId, Cookbook cookbook);

	public ResponseEntity<List<Ingredient>> getPantryIngredients(Integer userId);

	public ResponseEntity<Ingredient> createPantryIngredient(Integer userId, Ingredient ingredient);

	public ResponseEntity<Void> updatePantryIngredient(Integer userId, Integer ingredientId, Ingredient ingredient);

	public ResponseEntity<Void> deletePantryIngredient(Integer userId, Integer ingredientId);

	public ResponseEntity<List<Meal>> getMeals(Integer userId);

	public ResponseEntity<Meal> createMeal(Integer userId, Meal meal);

	public ResponseEntity<Void> updateMeal(Integer userId, Integer mealId, Meal meal);

	public ResponseEntity<Meal> deleteMeal(Integer userId, Integer mealId);

	public ResponseEntity<List<MealPlan>> getMealPlans(Integer userId);

	public ResponseEntity<MealPlan> createMealPlan(Integer userId, MealPlan mealPlan);

	public ResponseEntity<MealPlan> updateMealPlan(Integer userId, Integer mealId, MealPlan mealplan);

	public ResponseEntity<MealPlan> deleteMealPlan(Integer userId, Integer mealPlanId);

	

	
	
}
