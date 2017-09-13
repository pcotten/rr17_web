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
import com.pcotten.rr17.dao.IngredientDAO;
import com.pcotten.rr17.model.Ingredient;

@Component
public class IngredientDAOImpl extends JdbcDaoSupport implements IngredientDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class IngredientRowMapper implements RowMapper<Ingredient> {

		@Override
		public Ingredient mapRow(ResultSet rs, int row) throws SQLException {
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
		Ingredient ingredient = (Ingredient) getJdbcTemplate().query(
				"SELECT * FROM ingredient WHERE id =?", 
				new Object[] {id}, 
				new IngredientRowMapper());
		
		return ingredient;
	}
	
	@Override
	public Ingredient getIngredientByName(String name) {
		Ingredient ingredient = (Ingredient) getJdbcTemplate().query(
				"SELECT * FROM ingredient WHERE name =?", 
				new Object[] {name}, 
				new IngredientRowMapper());
		
		return ingredient;
	}

	@Override
	public Ingredient createIngredient(Ingredient ingredient) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO ingredient (name, description) VALUES (?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, ingredient.getName());
				ps.setString(2, ingredient.getDescription());
				return ps;
			}
			
		}, holder);
		if (holder.getKey() != null) {
			ingredient.setId(holder.getKey().intValue());
		}
		
		return ingredient;
	}

	@Override
	public Integer updateIngredient(Ingredient ingredient) {
		Integer result = getJdbcTemplate().update(
				"UPDATE ingredient SET name = ?, description = ? WHERE id = ?", 
				new Object[] {
					ingredient.getName(),
					ingredient.getDescription(),
					ingredient.getId(),
				});
		
		return result;
	}

	@Override
	public Integer deleteIngredient(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM ingredient WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<Ingredient> getPantryIngredients(Integer pantryId) {
		List<Ingredient> ingredients = (List<Ingredient>) getJdbcTemplate().query(
				"SELECT * FROM ingredients_by_pantryid WHERE pantryId = ?", 
				new Object[] {pantryId}, 
				new IngredientRowMapper());
		
		return ingredients;
	}
	
	@Override
	public Integer createPantryIngredient(Ingredient ingredient, Integer pantryId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO pantry_ingredient (name, description, quantity, quantityUnit, pantryId) "
				+ "VALUES (?, ?, ?, ?, ?)", 
				new Object[] {
					ingredient.getName(),
					ingredient.getDescription(),
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					pantryId
				});
		return result;
	}

	@Override
	public Integer updatePantryIngredient(Integer pantryId, Ingredient ingredient) {
		Integer result = getJdbcTemplate().update(
				"UPDATE pantry_ingredient SET name = ?, description = ?, quantity = ?, "
				+ "quantityUnit = ? WHERE pantryId = ? AND ingredientId = ?", 
				new Object[] {
					ingredient.getName(),
					ingredient.getDescription(),
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					pantryId,
					ingredient.getId(),
				});
		
		return result;
	}

	@Override
	public Integer deletePantryIngredient(Integer pantryId, Integer ingredientId) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM pantry_ingredient WHERE pantryId = ? AND ingredientId = ?", 
				new Object[] {
					pantryId,
					ingredientId
				});
		
		return result;
	}

	@Override
	public List<Ingredient> getRecipeIngredients(Integer recipeId) {
		List<Ingredient> ingredients = (List<Ingredient>) getJdbcTemplate().query(
				"SELECT * FROM ingredients_by_recipeid WHERE recipeId = ?", 
				new Object[] {recipeId}, 
				new IngredientRowMapper());
		
		return ingredients;
	}

	@Override
	public Integer createRecipeIngredient(Ingredient ingredient, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO ingredient_recipe (name, description, quantity, quantityUnit, recipeId) "
				+ "VALUES (?, ?, ?, ?, ?)", 
				new Object[] {
					ingredient.getName(),
					ingredient.getDescription(),
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					recipeId
				});
		return result;
	}

	@Override
	public Integer updateRecipeIngredient(Ingredient ingredient, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"UPDATE ingredient_recipe SET name = ?, description = ?, quantity = ?, "
				+ "quantityUnit = ? WHERE recipeId = ? AND ingredientId = ?", 
				new Object[] {
					ingredient.getName(),
					ingredient.getDescription(),
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					recipeId,
					ingredient.getId(),
				});
		
		return result;
	}

	@Override
	public Integer deleteRecipeIngredient(Integer recipeId, Integer ingredientId) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM ingredient_recipe WHERE recipeId = ? AND ingredientId = ?", 
				new Object[] {
					recipeId,
					ingredientId
				});
		
		return result;
	
	}
	
}


