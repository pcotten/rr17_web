package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Recipe;

public interface RecipeService {

	public Recipe createRecipe(Recipe recipe, Integer userId) throws SQLException;
	
	public boolean updateRecipe(Recipe recipe) throws SQLException;
	
	public boolean deleteRecipe(Integer id) throws SQLException;

	public Recipe getRecipeById(Integer id);
	
	public boolean recipeExists(Recipe recipe) throws SQLException;

	public List<Recipe> getRecipes(String category, String title, String username) throws SQLException;
	
}
