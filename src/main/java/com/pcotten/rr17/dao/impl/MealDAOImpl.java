package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.pcotten.rr17.dao.MealDAO;
import com.pcotten.rr17.model.Meal;

@Component
public class MealDAOImpl extends JdbcDaoSupport implements MealDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	
	private static class MealRowMapper implements RowMapper<Meal> {

		@Override
		public Meal mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Meal meal = new Meal();
			meal.setId(rs.getInt("id"));
			meal.setName(rs.getString("name"));
			meal.setOwner(rs.getInt("owner"));
			
			return meal;
		}
	}

	
	@Override
	public Meal getMeal(Integer id) {
		Meal meal = (Meal) getJdbcTemplate().queryForObject(
				"SELECT * FROM meal WHERE id = ?", 
				new Object[] {id}, 
				new MealRowMapper());
		
		return meal;
	}

	
	@Override
	public Meal createMeal(Meal meal, Integer userId) {
		KeyHolder holder = new GeneratedKeyHolder();
		Integer id = getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO meal (name, owner) VALUES (?, ?);";
				PreparedStatement ps = con.prepareStatement(
					sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, meal.getName());
				ps.setInt(2, meal.getOwner());
				
				return ps;
			}
		}, holder);
		if (id != null) {
			meal.setId(id);
		}
		
		return meal;
	}

	
	@Override
	public Integer updateMeal(Meal meal) {
		Integer result = getJdbcTemplate().update("UPDATE meal SET name = ?, owner = ? WHERE id = ?",
				new Object[] {
						meal.getName(),
						meal.getOwner(),
						meal.getId()
				});
				
		return result;
	}

	
	@Override
	public Integer deleteMeal(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM meal WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	
	@Override
	public List<Meal> getUserMeals(Integer userId) {
		List<Meal> meals = new ArrayList<Meal>();
		meals = getJdbcTemplate().query(
				"SELECT * FROM meals_by_userid WHERE userId = ?",
				new Object[] {userId},
				new MealRowMapper());
		
		return meals;
	}

	
	@Override
	public List<Meal> getMealPlanMeals(Integer mealPlanId) {
		List<Meal> meals = new ArrayList<Meal>();
		meals = getJdbcTemplate().query(
				"SELECT * FROM meals_by_mealplanid WHERE mealPlanId = ?",
				new Object[] {mealPlanId},
				new MealRowMapper());
		
		return meals;
	}

	
	@Override
	public Integer removeMealFromUser(Integer mealId, Integer userId) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM meal WHERE mealId = ? AND userId = ?", 
				new Object[] {
						mealId,
						userId
				});
		
		return result;
	}

	@Override
	public Integer addRecipeToMeal(Integer mealId, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO recipe_meal (mealId, recipeId) VALUES (?, ?)", 
				new Object[] {
						mealId,
						recipeId
				});
		
		return result;
	}

	@Override
	public Integer linkMealToUser(Integer mealId, Integer userId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO meal_user (mealId, userId) VALUES (?, ?)", 
				new Object[] {
						mealId,
						userId
				});
		
		return result;
	}
	
}


