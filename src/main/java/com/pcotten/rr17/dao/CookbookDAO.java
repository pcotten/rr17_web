package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Cookbook;

public interface CookbookDAO {

	public Cookbook getCookbook(Integer id);
	
	public Cookbook createCookbook(Cookbook cookbook, Integer userId);
	
	public Integer updateCookbook(Cookbook cookbook);
	
	public Integer deleteCookbook(Integer id);
	
	public List<Cookbook> getUserCookbooks(Integer userId);
	
	public Integer linkCookbookToUser(Integer cookbookId, Integer userId);

	public Integer linkCategoryToCookbook(Integer cookbookId, Integer categoryId);

	public Integer addRecipeToCookbook(Integer cookbookId, Integer recipeId);

	public Integer deleteCookbookRecipe(Integer cookbookId, Integer recipeId);
}
