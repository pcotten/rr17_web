package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Meal;

public interface MealService {

	public Meal createMeal(Meal meal, Integer userId) throws SQLException;
	
	public int updateMeal(Meal meal) throws SQLException;
	
	public int deleteMeal(Integer id, Integer userId) throws SQLException;

	public Meal getMealById(Integer id);

	public List<Meal> getMealPlanMeals(Integer id);

	boolean mealExists(Integer userId, Meal meal);
	
}
