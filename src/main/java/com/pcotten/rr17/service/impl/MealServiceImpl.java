package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.dao.MealDAO;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.service.MealService;
import com.pcotten.rr17.service.RecipeService;

@Component
public class MealServiceImpl implements MealService {

	@Inject
	DatabaseManager manager;
	@Inject
	MealDAO mealDAO;
	@Inject
	RecipeService recipeService;
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	Map<String, String> constraints = new HashMap<String, String>();
	
	public MealServiceImpl(){
		
	}

	public Meal createMeal(Meal meal, Integer userId) throws SQLException{

		meal = mealDAO.createMeal(meal, userId);
		if (meal.getId() != null){
			System.out.println("Meal successfully created with id " + meal.getId());
		}
		else {
			System.out.println("Failed to create meal");
			throw new SQLException();
		}
		for (Recipe recipe : meal.getRecipes()) {			
			mealDAO.addRecipeToMeal(meal.getId(), recipe.getId());
		}
		
		mealDAO.linkMealToUser(meal.getId(), userId);

		return meal;
	}

	
	public Integer updateMeal(Meal meal) throws SQLException{
		
		// add owner check
		
		int result = mealDAO.updateMeal(meal);
		
		if (result > 0){
			System.out.println("Meal entity successfully updated in database");
		}
		else {
			System.out.println("Meal update failedy");
		}
		return result;
	}
	
	
	public Integer deleteMeal(Integer id, Integer userId) throws SQLException{
		
		int result = -1;
		Integer ownerId = getMealOwner(id);
		if (ownerId.equals(userId)) {
			result = mealDAO.deleteMeal(ownerId);
			if (result != -1){
				System.out.println("Successfully removed meal with id " + id);
			}
			else {
				System.out.println("Unable to remove recipe meal with id " + id);
			}
		}
		else {
			conn = manager.getConnection();
			
			pstmt = conn.prepareStatement("DELETE FROM meal_user WHERE userId = ? AND mealId = ?");
			pstmt.setInt(1, userId);
			pstmt.setInt(2, id);
			
			result = pstmt.executeUpdate();
		}
		return result;
	}

	private Integer getMealOwner(Integer id) {
		
		Meal meal = mealDAO.getMeal(id);
		
		return meal.getOwner();
	}

	public Meal getMeal(Integer id) {
		
		Meal meal = mealDAO.getMeal(id);
		
		return meal;
	}

	@Override
	public List<Meal> getMealPlanMeals(Integer mealPlanId) {
		
		List<Meal> meals = mealDAO.getMealPlanMeals(mealPlanId);
		
		return meals;
	}

	public List<Recipe> getMealRecipes(Integer id) {
		
		List<Recipe> recipes = recipeService.getMealRecipes(id);
		
		return recipes;
	}
	
	@Override
	public boolean mealExists(Integer userId, Meal meal) throws SQLException {
		
		conn = manager.getConnection();
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("SELECT count(*) FROM meals_by_userid "
				+ "WHERE name = ? AND userid = ?");
		pstmt.setString(1, meal.getName());
		pstmt.setInt(2, userId);

		return manager.isExists(pstmt);
	
	}

	@Override
	public List<Meal> getMeals(Integer userId) {

		List<Meal> meals = mealDAO.getUserMeals(userId);
		
		return meals;
	}
}
