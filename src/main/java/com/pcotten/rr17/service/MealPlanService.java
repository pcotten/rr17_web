package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.MealPlan;

public interface MealPlanService {

	public MealPlan createMealPlan(MealPlan mealPlan, Integer userId) throws SQLException;
	
	public Integer updateMealPlan(MealPlan mealPlan) throws SQLException;
	
	public Integer deleteMealPlan(Integer id) throws SQLException;

	public MealPlan getMealPlan(Integer id);

	boolean mealPlanExists(Integer userId, MealPlan mealPlan) throws SQLException;

	public List<MealPlan> getMealPlans(Integer userId);
	
}
