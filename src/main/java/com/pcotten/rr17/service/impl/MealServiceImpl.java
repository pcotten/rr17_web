package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.service.MealService;

@Component
public class MealServiceImpl implements MealService {

	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	Map<String, String> constraints = new HashMap<String, String>();
	
	public MealServiceImpl(){
		
	}

	public Meal insertNewMeal(Meal meal, Integer userId) throws SQLException{

		int r = 0;
		
		conn = manager.getConnection();
		meal = insertMealEntity(meal);
		if (meal.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("Meal entity " + meal.getName() + " successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete meal insert - failed to insert meal entity");
			throw new SQLException();
		}
						
		r = linkRecipesToMeal(meal);
		if (r != 0){
			System.out.println("Meal recipe successfully linked in database");
		}
		else {
			System.out.println("Unable to complete meal insert - failed to link recipes");
			throw new SQLException();
		}
		
		r = linkMealToUser(meal, userId);
		if (r != 0){
			System.out.println("Meal successfully linked to user in database");
		}
		else {
			System.out.println("Unable to complete meal insert - failed to link meal to user");
			throw new SQLException();
		}

		return meal;
	}
	
	
	public Meal insertMealEntity(Meal meal) throws SQLException{
		
		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("INSERT INTO meal (name, lastPrepared)"
				+ "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, meal.getName());
		pstmt.setObject(2, meal.getLastPrepared());
		r = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			meal.setId(id);
		}
		if (r != 0 && meal.getId() != null){
			System.out.println("Meal entity successfully inserted into database");
		}
		else {
			System.out.println("Meal creation failed - unable to insert meal entity");
			throw new SQLException();
		}
		
		return meal;
	}

	private int linkRecipesToMeal(Meal meal) throws SQLException {
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		if (!meal.getRecipes().isEmpty()){
			for (Integer i : meal.getRecipes()){
				
				pstmt = conn.prepareStatement("INSERT INTO recipe_meal (recipeId, mealId) "
						+ "VALUES (?, ?)");
				pstmt.setInt(1, i);
				pstmt.setInt(2, meal.getId());
	
				result = pstmt.executeUpdate();
				
			}
		}
		else return 1;
		return result;
	}
	
	
	private int linkMealToUser(Meal meal, Integer userId) throws SQLException {
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		
		pstmt = conn.prepareStatement("INSERT INTO meal_user (mealId, userId) VALUES (?, ?)");
		pstmt.setInt(1, meal.getId());
		pstmt.setInt(2, userId);

		result = pstmt.executeUpdate();
			
		return result;
	}
	
	public int updateMeal(Meal meal) throws SQLException{
		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("UPDATE meal SET name = ?, lastPrepared = ? WHERE id = ?");
		pstmt.setString(1, meal.getName());
		pstmt.setObject(2, meal.getLastPrepared());
		pstmt.setInt(3, meal.getId());
		
		r = pstmt.executeUpdate();
	
		if (r != 0 && meal.getId() != null){
			System.out.println("Meal entity successfully updated in database");
		}
		else {
			System.out.println("Meal update failedy");
			throw new SQLException();
		}
		return r;
	}
	
	
	public int deleteMeal(Integer id) throws SQLException{
		int result = -1;

		result = DbCommonFunctions.deleteEntity("meal", id);
		if (result != -1){
			System.out.println("Successfully removed meal with id " + id);
		}
		else {
			System.out.println("Unable to remove recipe meal with id " + id);
		}
		
		return result;
	}

	public Meal getMealById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (Meal) manager.retrieveSingleEntity(constraints, Meal.class);
	}
}
