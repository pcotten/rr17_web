package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pcotten.rr17.model.Ingredient;
import com.pcotten.rr17.model.Recipe;

public interface RecipeService {

	public Recipe getRecipe(Integer id);
	
	public Recipe createRecipe(Recipe recipe, Integer userId) throws SQLException;
	
	public boolean updateRecipe(Recipe recipe) throws SQLException;
	
	public boolean deleteRecipe(Integer id) throws SQLException;
	
	public Map<Integer, List<Ingredient>> getRecipeIngredients(Integer recipeId);
	
	public boolean createRecipeIngredient(Integer recipeId, Ingredient ingredient) throws SQLException;
	
	public boolean updateRecipeIngredient(Integer recipeId, Ingredient ingredient);
	
	public boolean deleteRecipeIngredient(Integer recipeId, Integer ingredientId);
	
	public boolean recipeExists(Recipe recipe, Integer userId) throws SQLException;

	public List<Recipe> getRecipes(String category, String title, String username) throws SQLException;

	public List<Recipe> getMealRecipes(Integer mealId);
	
	public List<Recipe> getCookbookRecipes(Integer cookbookId);
	
	public List<Recipe> getUserRecipes(Integer userId);
}
