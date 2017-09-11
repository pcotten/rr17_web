package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.MealPlanDAO;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.MealPlan;
import com.pcotten.rr17.model.User;

public class MealPlanDAOImpl extends JdbcDaoSupport implements MealPlanDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class MealPlanRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			MealPlan mealPlan = new MealPlan();
			mealPlan.setId(rs.getInt("id"));
			mealPlan.setName(rs.getString("name"));
			mealPlan.setDescription(rs.getString("description"));
			mealPlan.setOwnerId(rs.getInt("ownerId"));
			
			return mealPlan;

		}
		
	}

	@Override
	public MealPlan getMealPlan(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MealPlan createMealk(MealPlan mealPlan, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateMealPlan(MealPlan mealPlan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteMealPlan(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MealPlan> getUserMealPlans(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


