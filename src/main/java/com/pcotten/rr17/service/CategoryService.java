package com.pcotten.rr17.service;

import java.sql.SQLException;
import java.util.List;

import com.pcotten.rr17.model.Category;

public interface CategoryService {

	public Category getCategory(Integer id);
	
	public Category createCategory(Category category) throws SQLException;
	
	public boolean updateCategory(Category category) throws SQLException;
	
	public boolean deleteCategory(Integer id) throws SQLException;

	public List<Category> getRecipeCategories(Integer recipeId);

	public List<Category> getIngredientCategories(Integer ingredientId);
	
	public List<Category> getCookbookCategories(Integer cookbookId);
	
	public boolean addCategoryToRecipe(Integer categoryId, Integer recipeId);
	
	public boolean addCategoryToIngredient(Integer categoryId, Integer ingredientId);
	
	public boolean addCategoryToCookbook(Integer categoryId, Integer cookbookId);

	public boolean removeCategoryFromRecipe(Integer imageId, Integer recipeId);
	
	public boolean removeCategoryFromIngredient(Integer categoryId, Integer ingredientId);
	
	public boolean removeCategoryFromCookbook(Integer categoryId, Integer cookbookId);
}
