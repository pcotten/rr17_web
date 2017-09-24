package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Meal;

public interface MealService {

	public Meal getMeal(Integer id);
	
	public Meal createMeal(Meal meal, Integer userId) throws SQLException;
	
	public boolean updateMeal(Meal meal) throws SQLException;
	
	public boolean deleteMeal(Integer id, Integer userId) throws SQLException;

	public List<Meal> getMeals(Integer userId);
	
	public List<Meal> getMealPlanMeals(Integer id);

	public boolean mealExists(Integer userId, Meal meal) throws SQLException;

	public boolean addRecipeToMeal(Integer mealId, Integer recipeId);

	public boolean removeRecipeFromMeal(Integer mealId, Integer recipeId);

}
