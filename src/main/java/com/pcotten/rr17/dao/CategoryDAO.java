package com.pcotten.rr17.dao;

import java.util.List;

import com.pcotten.rr17.model.Category;

public interface CategoryDAO {

	public Category getCategory(Integer id);
	
	public Category createCategory(Category category);
	
	public Integer updateCategory(Category category);
	
	public Integer deleteCategory(Integer id);
	
	public List<Category> getRecipeCategories(Integer recipeId);
	
	public List<Category> getMealCategories(Integer mealId);
	
	public List<Category> getIngredientCategories(Integer ingredientId);
	
	public List<Category> getCookbookCategories(Integer cookbookId);

	public int addCategoryToRecipe(Integer categoryId, Integer recipeId);

	public int addCategoryToIngredient(Integer categoryId, Integer ingredientId);

	public int addCategoryToCookbook(Integer categoryId, Integer cookbookId);

	public Integer removeCategoryFromRecipe(Integer categoryId, Integer recipeId);

	public Integer removeCategoryFromIngredient(Integer categoryId, Integer ingredientId);

	public Integer removeCategoryFromCookbook(Integer categoryId, Integer cookbookId);
}
