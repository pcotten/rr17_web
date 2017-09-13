package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Meal;

public interface MealDAO {

	public Meal getMeal(Integer id);
	
	public Meal createMeal(Meal meal, Integer userId);
	
	public Integer updateMeal(Meal meal);
	
	public Integer deleteMeal(Integer id);
	
	public List<Meal> getUserMeals(Integer userId);
	
	public List<Meal> getMealPlanMeals(Integer mealPlanId);
	
	public Integer removeMealFromUser(Integer mealId, Integer userId);

	public Integer addRecipeToMeal(Integer mealId, Integer recipeId);

	public Integer linkMealToUser(Integer mealId, Integer userId);
}
