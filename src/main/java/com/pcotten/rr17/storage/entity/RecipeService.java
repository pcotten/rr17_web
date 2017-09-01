package com.pcotten.rr17.storage.entity;

import java.sql.SQLException;

import com.pcotten.rr17.model.Recipe;

public interface RecipeService {

	public Recipe insertNewRecipe(Recipe recipe, Integer userId) throws SQLException;
	
	public int updateRecipe(Recipe recipe) throws SQLException;
	
	public int deleteRecipe(Integer id) throws SQLException;

	public Recipe getRecipeById(Integer id);
	
}
