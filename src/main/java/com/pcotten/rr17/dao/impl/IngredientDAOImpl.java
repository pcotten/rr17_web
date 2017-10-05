package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.pcotten.rr17.model.Image;
import com.pcotten.rr17.model.Ingredient;

@Component
public class IngredientDAOImpl extends JdbcDaoSupport implements IngredientDAO{
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class PantryIngredientRowMapper implements RowMapper<Ingredient> {

		@Override
		public Ingredient mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Ingredient ingredient = new Ingredient();
			ingredient.setId(rs.getInt("ingredientId"));
			ingredient.setName(rs.getString("name"));
			if (rs.getString("description") != null) {
				ingredient.setDescription(rs.getString("description"));
			}
			ingredient.setPantryId(rs.getInt("pantryId"));
			ingredient.setQuantity(rs.getFloat("quantity"));
			if (rs.getString("quantityUnit") != null) {
				ingredient.setQuantityUnit(rs.getString("quantityUnit"));
			}
			if (rs.getString("quantityDisplay") != null) {
				ingredient.setQuantityDisplay(rs.getString("quantityDisplay"));
			}
			if (rs.getString("imagePath") != null) {
				Image image = new Image(rs.getString("imagePath"), rs.getString("imageText"), 
						ingredient.getId(), Image.Type.INGREDIENT);
				image.setId(rs.getInt("imageId"));
				ingredient.setImage(image);
			}
			
			return ingredient;
		}
		
	}
	
	private static class RecipeIngredientRowMapper implements RowMapper<Ingredient> {

		@Override
		public Ingredient mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Ingredient ingredient = new Ingredient();
			ingredient.setId(rs.getInt("ingredientId"));
			ingredient.setName(rs.getString("name"));
			if (rs.getString("description") != null) {
				ingredient.setDescription(rs.getString("description"));
			}
			ingredient.setPantryId(rs.getInt("pantryId"));
			ingredient.setQuantity(rs.getFloat("quantity"));
			if (rs.getString("quantityUnit") != null) {
				ingredient.setQuantityUnit(rs.getString("quantityUnit"));
			}
			if (rs.getString("quantityDisplay") != null) {
				ingredient.setQuantityDisplay(rs.getString("quantityDisplay"));
			}
			if (rs.getString("groupName") != null) {
				ingredient.setGroupIndex(rs.getInt("groupIndex"));
				ingredient.setGroupName(rs.getString("groupName"));
			}
//			if (rs.getString("imagePath") != null) {
//				Image image = new Image(rs.getString("imagePath"), rs.getString("imageText"), 
//						ingredient.getId(), Image.Type.INGREDIENT);
//				image.setId(rs.getInt("imageId"));
//				ingredient.setImage(image);
//			}
			
			return ingredient;
		}
		
	}
	
	private static class GenericIngredientMapper implements RowMapper<Ingredient> {

		@Override
		public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
			Ingredient ingredient = new Ingredient();
			ingredient.setId(rs.getInt("id"));
			ingredient.setName(rs.getString("name"));
			ingredient.setDescription(rs.getString("description"));
			
			return ingredient;
		}
		
	}

	@Override
	public Ingredient getIngredient(Integer id) {
		List<Ingredient> ingredients = (List<Ingredient>) getJdbcTemplate().query(
				"SELECT * FROM ingredient WHERE id =?", 
				new Object[] {id}, 
				new GenericIngredientMapper());
		
		return ingredients.get(0);
	}
	
	@Override
	public Ingredient getIngredientByName(String name) {
		List<Ingredient> ingredients = (List<Ingredient>) getJdbcTemplate().query(
				"SELECT * FROM ingredient WHERE name = ?", 
				new Object[] {name}, 
				new GenericIngredientMapper());
		
		return ingredients.get(0);
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
				new PantryIngredientRowMapper());
		
		return ingredients;
	}
	
	@Override
	public Integer createPantryIngredient(Ingredient ingredient, Integer pantryId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO pantry_ingredient (ingredientId, quantity, quantityUnit, quantityDisplay, pantryId) "
				+ "VALUES (?, ?, ?, ?, ?)", 
				new Object[] {
					ingredient.getId(),
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					ingredient.getQuantityDisplay(),
					pantryId
				});
		return result;
	}

	@Override
	public Integer updatePantryIngredient(Integer pantryId, Ingredient ingredient) {
		Integer result = getJdbcTemplate().update(
				"UPDATE pantry_ingredient SET quantity = ?, "
				+ "quantityUnit = ?, quantityDisplay = ? WHERE pantryId = ? AND ingredientId = ?", 
				new Object[] {
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					ingredient.getQuantityDisplay(),
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
	public Map<Integer, List<Ingredient>> getRecipeIngredients(Integer recipeId) {
		List<Ingredient> ingredients = (List<Ingredient>) getJdbcTemplate().query(
				"SELECT * FROM ingredients_by_recipeid WHERE recipeId = ?", 
				new Object[] {recipeId}, 
				new RecipeIngredientRowMapper());	
		Map<Integer, List<Ingredient>> ingredientGroupMap = new HashMap<Integer, List<Ingredient>>();
		for (Ingredient ingredient : ingredients) {
			if (ingredientGroupMap.get(ingredient.getGroupIndex()) == null) {
				ingredientGroupMap.put(ingredient.getGroupIndex(), new ArrayList<Ingredient>());
			}
			ingredientGroupMap.get(ingredient.getGroupIndex()).add(ingredient);	
		}
		return ingredientGroupMap;
	}

	@Override
	public Integer createRecipeIngredient(Ingredient ingredient, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"INSERT INTO ingredient_recipe (ingredientId, quantity, quantityUnit, "
				+ "quantityDisplay, groupIndex, groupName, recipeId) VALUES (?, ?, ?, ?, ?, ?, ?)", 
				new Object[] {
					ingredient.getId(),
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					ingredient.getQuantityDisplay(),
					ingredient.getGroupIndex(),
					ingredient.getGroupName(),
					recipeId
				});
		return result;
	}

	@Override
	public Integer updateRecipeIngredient(Ingredient ingredient, Integer recipeId) {
		Integer result = getJdbcTemplate().update(
				"UPDATE ingredient_recipe SET quantity = ?, quantityUnit = ?, "
				+ "quantityDisplay = ?, groupIndex = ?, groupName = ? "
				+ "WHERE recipeId = ? AND ingredientId = ?", 
				new Object[] {
					ingredient.getQuantity(),
					ingredient.getQuantityUnit(),
					ingredient.getQuantityDisplay(),
					ingredient.getGroupIndex(),
					ingredient.getGroupName(),
					recipeId,
					ingredient.getId(),
				});
		
		return result;
	}

	@Override
	public Integer removeIngredientFromRecipe(Integer recipeId, Integer ingredientId) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM ingredient_recipe WHERE recipeId = ? AND ingredientId = ?", 
				new Object[] {
					recipeId,
					ingredientId
				});
		
		return result;
	
	}
	
}


