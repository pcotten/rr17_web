package com.pcotten.rr17.service;

import java.sql.SQLException;

import com.pcotten.rr17.model.Meal;

public interface MealService {

	public Meal insertNewMeal(Meal meal, Integer userId) throws SQLException;
	
	public int updateMeal(Meal meal) throws SQLException;
	
	public int deleteMeal(Integer id) throws SQLException;

	public Meal getMealById(Integer id);
	
}
