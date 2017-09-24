package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mysql.cj.api.jdbc.Statement;
import com.pcotten.rr17.dao.MealPlanDAO;
import com.pcotten.rr17.model.MealPlan;

@Component
public class MealPlanDAOImpl extends JdbcDaoSupport implements MealPlanDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class MealPlanRowMapper implements RowMapper<MealPlan> {

		@Override
		public MealPlan mapRow(ResultSet rs, int row) throws SQLException {

			MealPlan mealPlan = new MealPlan();
			mealPlan.setId(rs.getInt("id"));
			mealPlan.setName(rs.getString("name"));
			mealPlan.setDescription(rs.getString("description"));
			mealPlan.setOwner(rs.getInt("ownerId"));
			
			return mealPlan;

		}
	}

	@Override
	public MealPlan getMealPlan(Integer id) {
		MealPlan mealPlan = (MealPlan) getJdbcTemplate().query(
				"SELECT * FROM mealplan WHERE id = ?", 
				new Object[] {id}, 
				new MealPlanRowMapper());
		
		return mealPlan;
	}

	@Override
	public MealPlan createMealPlan(MealPlan mealPlan, Integer userId) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO mealplan (name, description, owner) VALUES (?, ?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, mealPlan.getName());
				ps.setString(2,  mealPlan.getDescription());
				ps.setInt(3, userId);
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			mealPlan.setId(holder.getKey().intValue());
		}
		
		return mealPlan;
	}

	@Override
	public Integer updateMealPlan(MealPlan mealPlan) {
		Integer result = getJdbcTemplate().update(
				"UPDATE mealplan SET name = ?, description = ?, owner = ? WHERE id = ?", 
				new Object[] {
			mealPlan.getName(),
			mealPlan.getDescription(),
			mealPlan.getOwner(),
			mealPlan.getId()
		});
		
		return result;
	}

	@Override
	public Integer deleteMealPlan(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM mealplan WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<MealPlan> getUserMealPlans(Integer userId) {
		List<MealPlan> mealPlans = (List<MealPlan>) getJdbcTemplate().query(
				"SELECT * FROM mealplans_by_userid WHERE id = ?", 
				new Object[] {userId}, 
				new MealPlanRowMapper());
		
		return mealPlans;
	}

	@Override
	public Integer linkMealToMealPlan(Integer mealPlanId, Integer mealId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO meal_mealplan (mealPlanId, mealId) VALUES (?, ?)", 
				new Object[] {
						mealPlanId,
						mealId
				});
		
		return result;
	}

	@Override
	public Integer linkMealPlanToUser(Integer mealPlanId, Integer userId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO user_mealplan (mealPlanId, userId) VALUES (?, ?)", 
				new Object[] {
						mealPlanId,
						userId
				});
		
		return result;
	}

	@Override
	public Integer removeMealFromMealPlan(Integer mealPlanId, Integer mealId) {
			Integer result = getJdbcTemplate().update(
					"DELETE FROM meal_user WHERE mealPlanId = ? AND userId = ?", 
					new Object[] {
							mealPlanId,
							mealId
					});
			
			return result;
	}
	
}


