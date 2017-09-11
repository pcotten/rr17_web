package com.pcotten.rr17.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.pcotten.rr17.dao.RecipeDAO;
import com.pcotten.rr17.model.Recipe;
import com.pcotten.rr17.model.User;
@Component
public class RecipeDAOImpl extends JdbcDaoSupport implements RecipeDAO {
	
	@Inject
	DataSource dataSource;
	
	@PostConstruct
	private void initDatasource() {
		setDataSource(dataSource);
	}
	
	private static class RecipeRowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			// TODO Auto-generated method stubpstmt.setString(1, user.getUsername());
			Recipe recipe = new Recipe();
			recipe.setId(rs.getInt("id"));
			recipe.setTitle(rs.getString("title"));
			recipe.setDescription(rs.getString("description"));
			recipe.setOwner(rs.getInt("owner"));
			recipe.setAttributedTo(rs.getString("attributedTo"));
			recipe.setNumberOfServings(rs.getInt("numberOfServings"));
			recipe.setOvenTemp(rs.getInt("ovenTemp"));
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recipe createRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteRecipe(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getCookbookRecipes(Integer cookbookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getMealRecipes(Integer mealId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


