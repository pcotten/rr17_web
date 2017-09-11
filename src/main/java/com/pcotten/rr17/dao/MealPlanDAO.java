package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.MealPlan;

public interface MealPlanDAO {

public MealPlan getMealPlan(Integer id);
	
	public MealPlan createMealk(MealPlan mealPlan, Integer userId);
	
	public Integer updateMealPlan(MealPlan mealPlan);
	
	public Integer deleteMealPlan(Integer id);
	
	public List<MealPlan> getUserMealPlans(Integer userId);
}
