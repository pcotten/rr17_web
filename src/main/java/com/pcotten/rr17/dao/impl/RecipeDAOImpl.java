package com.pcotten.rr17.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.pcotten.rr17.dao.RecipeDAO;
import com.pcotten.rr17.model.Recipe;

@Component
public class RecipeDAOImpl extends JdbcDaoSupport implements RecipeDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class RecipeRowMapper implements RowMapper<Recipe> {

		@Override
		public Recipe mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Recipe recipe = new Recipe();
			recipe.setId(rs.getInt("id"));
			recipe.setTitle(rs.getString("title"));
			recipe.setDescription(rs.getString("description"));
			recipe.setOwner(rs.getInt("owner"));
			recipe.setAttributedTo(rs.getString("attributedTo"));
			recipe.setNumberOfServings(rs.getInt("numberOfServings"));
			recipe.setOvenTemp(rs.getString("ovenTemp"));
			recipe.setServingSize(rs.getInt("servingSize"));
			recipe.setServingSizeUnit(rs.getString("servingSizeUnit"));
			recipe.setCookTime(rs.getInt("cookTime"));
			recipe.setCookTimeUnit(rs.getString("cookTimeUnit"));
			recipe.setPrepTime(rs.getInt("prepTime"));
			recipe.setPrepTimeUnit(rs.getString("prepTimeUnit"));
			recipe.setRating(rs.getInt("rating"));
			recipe.setLastPrepared(rs.getTimestamp("lastPrepared"));
			
			return recipe;
		}
		
	}

	@Override
	public Recipe getRecipe(Integer id) {
		Recipe recipe = (Recipe) getJdbcTemplate().queryForObject("SELECT * FROM recipe WHERE id = ?",
				new Object[] {id},
				new RecipeRowMapper());
		
		return recipe;
	}

	@Override
	public Recipe createRecipe(Recipe recipe, Integer userId) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "INSERT INTO recipe (title, description, owner, attributedTo," 
						+ " numberOfServings, ovenTemp, servingSize, servingSizeUnit, "  
						+ "cookTime, cookTimeUnit, prepTime, prepTimeUnit) " 
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, recipe.getTitle());
				ps.setString(2, recipe.getDescription());
				ps.setInt(3, userId);
				ps.setString(4, recipe.getAttributedTo());
				
				if (recipe.getNumberOfServings() != null){
					ps.setInt(5, recipe.getNumberOfServings());
				}
				else 
					ps.setNull(5, java.sql.Types.INTEGER);
				if (recipe.getOvenTemp() != null){
					ps.setString(6, recipe.getOvenTemp());
				}
				else 
					ps.setNull(6, java.sql.Types.INTEGER);
				if (recipe.getServingSize() != null){
					ps.setInt(7, recipe.getServingSize());
				}
				else 
					ps.setNull(7, java.sql.Types.INTEGER);
				ps.setString(8, recipe.getServingSizeUnit());
				if (recipe.getCookTime() != null){
					ps.setInt(9, recipe.getCookTime());
				}
				else 
					ps.setNull(9, java.sql.Types.INTEGER);
				
				ps.setString(10, recipe.getCookTimeUnit());
				if (recipe.getPrepTime() != null){
					ps.setInt(11, recipe.getPrepTime());
				}
				else 
					ps.setNull(11, java.sql.Types.INTEGER);
				ps.setString(12, recipe.getPrepTimeUnit());
				
				return ps;
			}
		}, holder);
		if (holder.getKey() != null) {
			recipe.setId(holder.getKey().intValue());
		}
		
		linkToUser(recipe.getId(), userId);
		
		return recipe;
	}

	private void linkToUser(Integer id, Integer userId) {
		getJdbcTemplate().update("INSERT INTO user_recipe (recipeId, userId) VALUES (?, ?)",
				new Object[] {
						id,
						userId
				});
	}

	@Override
	public Integer updateRecipe(Recipe recipe) {
		Integer result = getJdbcTemplate().update(
				"UPDATE recipe SET title = ?, description = ?, owner = ?, attributedTo = ?, "
				+ "numberOfServings = ?, ovenTemp = ?, servingSize = ?, servingSizeUnit = ?,"
				+ " cookTime = ?, cookTimeUnit = ?, prepTime = ?, prepTimeUnit = ? WHERE id = ?", 
				new Object[] {
					recipe.getTitle(),
					recipe.getDescription(),
					recipe.getOwner(),
					recipe.getAttributedTo(),
					recipe.getNumberOfServings(),
					recipe.getOvenTemp(),
					recipe.getServingSize(),
					recipe.getServingSizeUnit(),
					recipe.getCookTime(),
					recipe.getCookTimeUnit(),
					recipe.getPrepTime(),
					recipe.getPrepTimeUnit(),
					recipe.getId()
				});
		
		return result;
	}

	@Override
	public Integer deleteRecipe(Integer id) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM recipe WHERE id = ?", 
				new Object[] {id});
		
		return result;
	}

	@Override
	public List<Recipe> getCookbookRecipes(Integer cookbookId) {
		List<Recipe> recipes = getJdbcTemplate().query(
				"SELECT * FROM recipes_by_cookbookid WHERE cookbookId = ?", 
				new Object[] {cookbookId}, 
				new RecipeRowMapper());
		
		return recipes;
	}

	@Override
	public List<Recipe> getMealRecipes(Integer mealId) {
			List<Recipe> recipes = getJdbcTemplate().query(
					"SELECT * FROM recipes_by_mealid WHERE mealId = ?", 
					new Object[] {mealId}, 
					new RecipeRowMapper());
			
			return recipes;
	}

	@Override
	public Integer addRecipeToCookbook(Integer recipeId, Integer cookbookId) {
		Integer result = getJdbcTemplate().update(
				"call add_recipe_to_cookbook (?, ?)", 
				new Object[] {
						cookbookId, 
						recipeId
				});
		
		return result;
	}
	
	public Integer deleteCookbookRecipe(Integer recipeId, Integer cookbookId) {
		Integer result = getJdbcTemplate().update(
				"DELETE FROM recipe_cookbook WHERE cookbookId = ? AND recipeId = ?", 
				new Object[] {
						cookbookId, 
						recipeId
				});
		
		return result;
	}

	@Override
	public List<Recipe> findRecipes(String category, String title, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getUserRecipes(Integer userId) {
		List<Recipe> recipes = getJdbcTemplate().query(
				"SELECT * FROM recipes_by_userid WHERE userId = ?", 
				new Object[] {userId}, 
				new RecipeRowMapper());
		
		return recipes;
	}
	
}


