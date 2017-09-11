package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.MealDAO;
import com.pcotten.rr17.model.Meal;
import com.pcotten.rr17.model.User;

public class MealDAOImpl extends JdbcDaoSupport implements MealDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class MealRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Meal meal = new Meal();
			meal.setId(rs.getInt("id"));
			meal.setName(rs.getString("name"));
			meal.setOwnerId(rs.getInt("ownerId"));
			
			return meal;
		}
		
	}

	@Override
	public Meal getMeal(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meal createMealk(Meal meal, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateMeal(Meal meal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteMeal(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meal> getUserMeals(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meal> getMealPlanMeals(Integer mealPlanId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


