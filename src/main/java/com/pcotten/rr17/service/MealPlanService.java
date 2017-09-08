package com.pcotten.rr17.service;

import java.sql.SQLException;

import com.pcotten.rr17.model.MealPlan;

public interface MealPlanService {

	public MealPlan createMealPlan(MealPlan mealPlan, Integer userId) throws SQLException;
	
	public int updateMealPlan(MealPlan mealPlan) throws SQLException;
	
	public int deleteMealPlan(Integer id) throws SQLException;

	public MealPlan getMealPlanById(Integer id);

	boolean mealPlanExists(Integer userId, MealPlan mealPlan);
	
}
