package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;

public interface CategoryService {

	public Category getCategory(Integer id);
	
	public Category createCategory(Category category) throws SQLException;
	
	public Integer updateCategory(Category category) throws SQLException;
	
	public Integer deleteCategory(Integer id) throws SQLException;

	public List<Category> getRecipeCategories(Integer recipeId);

	public List<Category> getIngredientCategories(Integer ingredientId);
	
	public List<Category> getCookbookCategories(Integer cookbookId);
	
	public Integer linkCategoryToRecipe(Integer categoryId, Integer recipeId);
	
	public Integer linkCategoryToIngredient(Integer categoryId, Integer ingredientId);
	
	public Integer linkCategoryToCookbook(Integer categoryId, Integer cookbookId);
	
}
