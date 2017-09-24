package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.MealPlanDAO;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.service.MealPlanService;
import com.pcotten.rr17.service.MealService;

@Component
public class MealPlanServiceImpl implements MealPlanService{

	@Inject
	DatabaseManager manager;
	@Inject
	MealPlanDAO mealPlanDAO;
	@Inject
	MealService mealService;
	
	public MealPlanServiceImpl(){
		
	}
	
	@Override
	public MealPlan getMealPlan(Integer id) {

		MealPlan mealPlan = mealPlanDAO.getMealPlan(id);
		
		return mealPlan;
	}
	
	@Override
	public MealPlan createMealPlan(MealPlan mealPlan, Integer userId) throws SQLException{

		mealPlan = mealPlanDAO.createMealPlan(mealPlan, userId);
		
		if (mealPlan.getId() != null){
			System.out.println("MealPlan successfully created with id " + mealPlan.getId());
		}
		else {
			System.out.println("Failed to create mealPlan");
		}
		
		for (Meal meal : mealPlan.getMeals()) {
			mealPlanDAO.linkMealToMealPlan(mealPlan.getId(), meal.getId());
		}
		
		mealPlanDAO.linkMealPlanToUser(mealPlan.getId(), userId);
		
		return mealPlan;
	}

	@Override
	public boolean updateMealPlan(MealPlan mealPlan) throws SQLException{

		boolean success = false;
		int result = mealPlanDAO.updateMealPlan(mealPlan);
		
		if (result != 0){
			System.out.println("MealPlan successfully updated in database");
			success = true;
		}
		else {
			System.out.println("Failed to update MealPlan");
		}
		
		return success;
	}
	
	@Override
	public boolean deleteMealPlan(Integer id) throws SQLException{
		
		boolean success = false;
		int result = -1;

		result = mealPlanDAO.deleteMealPlan(id);
		
		if (result != -1){
			System.out.println("Successfully deleted MealPlan with id " + id);
			success = true;
		}
		else {
			System.out.println("Failed to delete MealPlan with id " + id);
		}
		
		return success;
	}
	
	
	@Override
	public boolean mealPlanExists(Integer userId, MealPlan mealPlan) throws SQLException {
		
		Connection conn = manager.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM mealplans_by_userid "
				+ "WHERE name = ? AND userid = ?");
		ps.setString(1, mealPlan.getName());
		ps.setInt(2, userId);

		return manager.isExists(ps);
	
	}


	@Override
	public List<MealPlan> getMealPlans(Integer userId) {
		
		List<MealPlan> mealPlans = mealPlanDAO.getUserMealPlans(userId);
		
		return mealPlans;
	}

	@Override
	public List<Meal> getMealPlanMeals(Integer mealPlanId) {

		List<Meal> meals = mealService.getMealPlanMeals(mealPlanId);
		
		return meals;
	}

	@Override
	public Meal getMealPlanMeal(Integer mealPlanId, Integer mealId) {
		
		Meal meal = mealService.getMeal(mealId);
		
		return meal;
	}

	@Override
	public boolean addMealToMealPlan(Integer mealPlanId, Integer mealId) {

		Integer result = mealPlanDAO.linkMealToMealPlan(mealPlanId, mealId);
		
		if (result > 0) {
			System.out.println("Successfully added meal to mealplan");
			return true;
		}
		else {
			System.out.println("Failed to add meal to mealplan");
			return false;
		}
	}

	@Override
	public boolean removeMealFromMealPlan(Integer mealPlanId, Integer mealId) {
		
		Integer result = mealPlanDAO.removeMealFromMealPlan(mealPlanId, mealId);
		
		if (result > 0) {
			System.out.println("Successfully added meal to mealplan");
			return true;
		}
		else {
			System.out.println("Failed to add meal to mealplan");
			return false;
		}
	}
}
