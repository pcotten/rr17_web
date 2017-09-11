package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.pcotten.rr17.dao.IngredientDAO;
import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.User;

public class IngredientDAOImpl extends JdbcDaoSupport implements IngredientDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class IngredientRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Ingredient ingredient = new Ingredient();
			ingredient.setId(rs.getInt("id"));
			ingredient.setName(rs.getString("name"));
			ingredient.setDescription(rs.getString("description"));
			ingredient.setQuantity(rs.getFloat("quantity"));
			ingredient.setQuantityUnit(rs.getString("quantityUnit"));
			
			return ingredient;
		}
		
	}

	@Override
	public Ingredient getIngredient(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ingredient createIngredient(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateIngredient(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteIngredient(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ingredient> getPantryIngredients(Integer pantryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ingredient> getRecipeIngredients(Integer recipeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


