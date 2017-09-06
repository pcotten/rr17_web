package com.pcotten.rr17.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pcotten.rr17.storage.service.DatabaseConfig;
import com.pcotten.rr17.storage.service.DatabaseManager;
import com.pcotten.rr17.storage.service.DbCommonFunctions;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.service.MealPlanService;

@Component
public class MealPlanServiceImpl implements MealPlanService{

	DatabaseConfig config = new DatabaseConfig();
	DatabaseManager manager = new DatabaseManager();
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	public MealPlanServiceImpl(){
		
	}
	
	
	public MealPlan insertNewMealPlan(MealPlan mealPlan, Integer userId) throws SQLException{

		int r = 0;
		
			conn = manager.getConnection();

		mealPlan = insertMealEntity(mealPlan);
		if (mealPlan.getId() != null){
			r = 1;
		}
		if (r != 0){
			System.out.println("MealPlan entity " + mealPlan.getName() + " successfully inserted into database");
		}
		else {
			System.out.println("Unable to complete mealPlan insert - failed to insert mealPlan entity");
			throw new SQLException();
		}
						
		r = linkMealsToMealPlan(mealPlan);
		if (r != 0){
			System.out.println("MealPlan meals successfully linked in database");
		}
		else {
			System.out.println("Unable to complete mealPlan insert - failed to link meals");
			throw new SQLException();
		}
		
		r = linkMealPlanToUser(mealPlan, userId);
		if (r != 0){
			System.out.println("MealPlan successfully linked to user in database");
		}
		else {
			System.out.println("Unable to complete mealPlan insert - failed to link meal to user");
			throw new SQLException();
		}
				
		return mealPlan;
	}

	
	private MealPlan insertMealEntity(MealPlan mealPlan) throws SQLException {
		
		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("INSERT INTO mealplan (name)"
				+ "VALUES (?);", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, mealPlan.getName());
		r = pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()){
			Integer id = Integer.valueOf(rs.getString("GENERATED_KEY"));
			mealPlan.setId(id);
		}
		if (r != 0 && mealPlan.getId() != null){
			System.out.println("MealPlan entity successfully inserted into database");
		}
		else {
			System.out.println("MealPlan creation failed - unable to insert mealPlan entity");
			throw new SQLException();
		}
		
		return mealPlan;
	}
	
	
	private int linkMealsToMealPlan(MealPlan mealPlan) throws SQLException {
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		if (!mealPlan.getMeals().isEmpty()){
			for (Meal meal : mealPlan.getMeals()){
				pstmt = conn.prepareStatement("INSERT INTO meal_mealplan (mealPlanId, mealId) VALUES (?, ?)");
				pstmt.setInt(1, mealPlan.getId());
				pstmt.setInt(2, meal.getId());
	
				result = pstmt.executeUpdate();
				
			}
		}
		else return 1;
		return result;
	}

	
	private int linkMealPlanToUser(MealPlan mealPlan, Integer userId) throws SQLException {
		
		int result = 0;
		
		if (conn.isClosed()){
			conn = manager.getConnection();
		}
		
		pstmt = conn.prepareStatement("INSERT INTO user_mealplan (userId, mealPlanId) VALUES (?, ?)");
		pstmt.setInt(1, userId);
		pstmt.setInt(2, mealPlan.getId());

		result = pstmt.executeUpdate();
			
		return result;
	}


	public int updateMealPlan(MealPlan mealPlan) throws SQLException{

		conn = manager.getConnection();
		int r = 0;
		pstmt = conn.prepareStatement("UPDATE mealplan SET name = ? WHERE id = ?");
		pstmt.setString(1, mealPlan.getName());
		pstmt.setInt(2, mealPlan.getId());
		r = pstmt.executeUpdate();

		if (r != 0 && mealPlan.getId() != null){
			System.out.println("MealPlan entity successfully updated in database");
		}
		else {
			System.out.println("MealPlan update failed");
			throw new SQLException();
		}
		
		return r;
	}
	
	public int deleteMealPlan(Integer id) throws SQLException{
		int result = -1;

		result = DbCommonFunctions.deleteEntity("mealplan", id);
		if (result != -1){
			System.out.println("Successfully removed mealPlan with id " + id);
		}
		else {
			System.out.println("Unable to remove mealPlan entity with id " + id);
		}
		
		return result;
	}


	public MealPlan getMealPlanById(Integer id) {
		Map<String, String> constraints = new HashMap<String, String>();
		constraints.put("id", id.toString());
		
		return (MealPlan) manager.retrieveSingleEntity(constraints, MealPlan.class);
	}
}
