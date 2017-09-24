package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;
import com.pcotten.rr17.model.Cookbook;
import com.pcotten.rr17.model.Recipe;

public interface CookbookService {

	public Cookbook createCookbook(Cookbook cookbook, Integer userId) throws SQLException;
	
	public boolean updateCookbook(Cookbook cookbook) throws SQLException;
	
	public boolean deleteCookbook(Integer id) throws SQLException;

	public Cookbook getCookbook(Integer id);

	public List<Recipe> getCookbookRecipes(Integer id) throws SQLException;

	public List<Category> getCookbookCategories(Integer id) throws SQLException;

	boolean cookbookExists(Integer userId, Cookbook cookbook) throws SQLException;

	public boolean addRecipeToCookbook(Integer cookbookId, Integer recipeId);
	
	public boolean removeRecipeFromCookbook(Integer cookbookId, Integer recipeId) throws SQLException;
	
	// replaced by addRecipeToCookbook
//	public Recipe createCookbookRecipe(Integer cookbookId, Recipe recipe) throws SQLException;

	public Integer getCookbookOwner(Integer cookbookId);
	
	public List<Cookbook> getCookbooks(Integer userId);

	

	
	
}
