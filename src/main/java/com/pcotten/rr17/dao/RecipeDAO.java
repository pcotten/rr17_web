package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Recipe;

public interface RecipeDAO {
	
	public Recipe getRecipe(Integer id);
	
	public Recipe createRecipe(Recipe recipe);
	
	public Integer updateRecipe(Recipe recipe);
	
	public Integer deleteRecipe(Integer id);
	
	public List<Recipe> getCookbookRecipes(Integer cookbookId);
	
	public List<Recipe> getMealRecipes(Integer mealId);

	public Integer addRecipeToCookbook(Integer id, Integer cookbookId);
	
	public List<Recipe> findRecipes(String category, String title, String username);
	
}
